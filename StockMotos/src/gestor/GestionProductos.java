package gestor;


import modelo.Producto;
import java.util.ArrayList;

public class GestionProductos {
    private ArrayList<Producto> productos;

    public GestionProductos() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("¡Producto agregado al inventario con éxito!");
    }

    public void mostrarProductos() {
        if (productos.isEmpty()) {
            System.out.println("El inventario de productos está vacío.");
        } else {
            System.out.println("\n--- INVENTARIO DE PRODUCTOS ---");
            for (Producto p : productos) {
                System.out.println("Código: " + p.getCodigo() + " | Nombre: " + p.getNombre() + 
                                   " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock());
            }
        }
    }

    public Producto buscarProducto(String criterio) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(criterio) || p.getNombre().equalsIgnoreCase(criterio)) {
                return p;
            }
        }
        return null;
    }
}