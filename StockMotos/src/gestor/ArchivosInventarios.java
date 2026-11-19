package gestor;

import modelo.Categoria;
import modelo.Producto;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ArchivosInventarios {
    private static final String NOMBRE_ARCHIVO = "inventario.txt";

    // Guardar la lista de productos en el fichero
    public static void guardarInventario(ArrayList<Producto> listaProductos) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (Producto p : listaProductos) {
                // Formato de línea: ID,Código,Nombre,Precio,Stock
                escritor.println(p.getIdProducto() + "," + p.getCodigo() + "," + p.getNombre() + "," + p.getPrecio() + "," + p.getStock());
            }
            System.out.println("[Sistema] Inventario guardado en fichero exitosamente.");
        } catch (Exception e) {
            System.out.println("[Error] No se pudo guardar el archivo: " + e.getMessage());
        }
    }

    // Cargar los productos desde el fichero al iniciar el programa
    public static ArrayList<Producto> cargarInventario() {
        ArrayList<Producto> lista = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists()) {
            return lista; // Si el archivo no existe aún, devuelve la lista vacía
        }

        try (Scanner lector = new Scanner(archivo)) {
            Categoria categoriaBase = new Categoria(1, "Repuestos", "General");
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    int id = Integer.parseInt(partes[0]);
                    String codigo = partes[1];
                    String nombre = partes[2];
                    double precio = Double.parseDouble(partes[3]);
                    int stock = Integer.parseInt(partes[4]);

                    Producto p = new Producto(id, codigo, nombre, "", precio, stock, "sin_foto", categoriaBase);
                    lista.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println("[Error] No se pudo leer el archivo: " + e.getMessage());
        }

        return lista;
    }
}