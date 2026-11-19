package gestor;

import modelo.Categoria;
import modelo.Producto;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventario {
    private ArrayList<Producto> listaProductos;
    private static final String NOMBRE_ARCHIVO = "inventario.txt";

    public Inventario() {
        this.listaProductos = new ArrayList<>();
        cargarDesdeArchivo(); // Se ejecuta automáticamente al arrancar
    }

    // Devuelve true si se agregó, o false si ya existía un producto con ese código
    public boolean agregarProducto(Producto producto) {
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            return false;
        }
        listaProductos.add(producto);
        guardarEnArchivo(); // Se guarda automáticamente
        return true;
    }

    // Permite que la interfaz gráfica lea la lista de productos
    public ArrayList<Producto> getProductos() {
        return listaProductos;
    }

    public void mostrarInventario() {
        if (listaProductos.isEmpty()) {
            System.out.println("El inventario esta vacio.");
        } else {
            System.out.println("\n--- LISTA DE PRODUCTOS EN STOCK ---");
            for (Producto p : listaProductos) {
                System.out.println("ID: " + p.getIdProducto());
                System.out.println("Código: " + p.getCodigo());
                System.out.println("Nombre: " + p.getNombre());
                // Mostramos la categoría asociada a la clase Categoria
                String nombreCat = (p.getCategoria() != null) ? p.getCategoria().getNombre() : "Sin categoría";
                System.out.println("Categoría: " + nombreCat);
                System.out.println("Precio: $" + p.getPrecio());
                System.out.println("Stock: " + p.getStock());
                System.out.println("----------------------------------------\n");
            }
        }
    }

    public Producto buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    public Producto buscarProducto(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return null;
        }
        String texto = criterio.trim();
        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(texto) || p.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    // --- MÉTODO PARA MODIFICAR NOMBRE O PRECIO POR CÓDIGO ---
    public boolean modificarProductoPorCodigo(String codigo, String nuevoNombre, double nuevoPrecio) {
        Producto p = buscarPorCodigo(codigo);
        if (p != null) {
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                p.setNombre(nuevoNombre);
            }
            if (nuevoPrecio >= 0) {
                p.setPrecio(nuevoPrecio);
            }
            // Guarda los cambios automáticamente en el archivo inventario.txt
            guardarEnArchivo();
            return true;
        }
        return false;
    }

    // --- NUEVO MÉTODO PARA ELIMINAR PRODUCTO POR CÓDIGO ---
    public boolean eliminarProductoPorCodigo(String codigo) {
        Producto p = buscarPorCodigo(codigo);
        if (p != null) {
            listaProductos.remove(p);
            guardarEnArchivo(); // Actualiza el archivo inventario.txt automáticamente
            return true;
        }
        return false;
    }

    public void actualizarCambiosStock() {
        guardarEnArchivo(); // Actualiza el archivo cuando cambie el stock tras una venta
    }

    // --- MÉTODOS DE PERSISTENCIA AUTOMÁTICA EN FICHERO (CON CATEGORÍA) ---
    private void guardarEnArchivo() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (Producto p : listaProductos) {
                // Obtenemos los datos de la categoría o asignamos valores por defecto
                String nomCat = (p.getCategoria() != null) ? p.getCategoria().getNombre() : "Repuestos";
                String descCat = (p.getCategoria() != null) ? p.getCategoria().getDescripcion() : "General";
                
                // Formato de guardado: ID,Codigo,Nombre,Precio,Stock,NombreCategoria,DescCategoria
                escritor.println(p.getIdProducto() + "," + p.getCodigo() + "," + p.getNombre() + "," + 
                                 p.getPrecio() + "," + p.getStock() + "," + nomCat + "," + descCat);
            }
        } catch (Exception e) {
            System.out.println("[Error al guardar inventario]: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) return;

        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",");
                // Soportamos tanto el formato viejo (5 partes) como el nuevo con categoría (7 partes)
                if (partes.length < 5) continue;

                try {
                    int id = Integer.parseInt(partes[0]);
                    String codigo = partes[1];
                    String nombre = partes[2];
                    double precio = Double.parseDouble(partes[3]);
                    int stock = Integer.parseInt(partes[4]);

                    // Extraemos la categoría guardada o usamos una por defecto
                    String nomCat = (partes.length >= 6) ? partes[5] : "Repuestos";
                    String descCat = (partes.length >= 7) ? partes[6] : "General";
                    Categoria categoriaAsociada = new Categoria(id, nomCat, descCat);

                    // Si el código ya se cargó, se ignora para evitar duplicados
                    if (buscarPorCodigo(codigo) == null) {
                        Producto p = new Producto(id, codigo, nombre, "", precio, stock, "sin_foto", categoriaAsociada);
                        listaProductos.add(p);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Aviso] Línea ignorada en el inventario: " + linea);
                }
            }
        } catch (Exception e) {
            System.out.println("[Error al cargar inventario]: " + e.getMessage());
        }
    }
}