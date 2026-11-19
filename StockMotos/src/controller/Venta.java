package controller;

import modelo.Producto;
import gestor.Inventario;
import java.util.ArrayList;
import java.util.Scanner;

public class Venta {
    private static int contadorVentas = 1;
    private int idVenta;
    private ArrayList<Producto> productosVendidos;
    private ArrayList<Integer> cantidadesVendidas;
    private double totalVenta;
    private String metodoPago;

    // Constructor vacío por defecto (para caja y ventas normales)
    public Venta() {
        this.idVenta = contadorVentas++;
        this.productosVendidos = new ArrayList<>();
        this.cantidadesVendidas = new ArrayList<>();
        this.totalVenta = 0.0;
        this.metodoPago = "";
    }

    // Constructor sobrecargado (para registrar el mantenimiento y taller del mecánico)
    public Venta(int idVenta, ArrayList<Producto> productosVendidos, ArrayList<Integer> cantidadesVendidas, double totalVenta, String metodoPago) {
        this.idVenta = idVenta;
        this.productosVendidos = productosVendidos;
        this.cantidadesVendidas = cantidadesVendidas;
        this.totalVenta = totalVenta;
        this.metodoPago = metodoPago;
    }

    public int getIdVenta() { return idVenta; }
    public ArrayList<Producto> getProductosVendidos() { return productosVendidos; }
    public ArrayList<Integer> getCantidadesVendidas() { return cantidadesVendidas; }
    public double getTotalVenta() { return totalVenta; }
    public String getMetodoPago() { return metodoPago; }

    public void procesarVenta(Scanner entrada, Inventario inventario, ArrayList<Venta> historialVentas) {
        boolean continuarComprando = true;

        System.out.println("\n=== NUEVA VENTA / FACTURACIÓN (Factura #" + idVenta + ") ===");

        while (continuarComprando) {
            Producto p = null;
            while (p == null) {
                System.out.print("Ingrese el código (ej. 001) o nombre del producto a vender: ");
                String criterio = entrada.nextLine().trim();

                if (criterio.isEmpty()) {
                    System.out.println("Error: Debe ingresar un código o nombre válido.");
                    continue;
                }

                p = inventario.buscarProducto(criterio);
                if (p == null) {
                    System.out.println("Producto no encontrado. Intente de nuevo.");
                }
            }

            System.out.println("Encontrado: " + p.getNombre() + " | Precio: $" + p.getPrecio() + " | Stock disponible: " + p.getStock());

            if (p.getStock() > 0) {
                int cantidad = 0;
                while (true) {
                    System.out.print("Ingrese la cantidad a llevar: ");
                    String lineCant = entrada.nextLine().trim();
                    try {
                        cantidad = Integer.parseInt(lineCant);
                        if (cantidad > 0 && p.tieneStockDisponible(cantidad)) {
                            break;
                        } else {
                            System.out.println("Error: Cantidad inválida o supera el stock disponible (" + p.getStock() + ").");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número entero válido.");
                    }
                }

                productosVendidos.add(p);
                cantidadesVendidas.add(cantidad);
                totalVenta += (p.getPrecio() * cantidad);

                System.out.println("¡Producto agregado a la venta actual! Subtotal parcial: $" + (p.getPrecio() * cantidad));
            } else {
                System.out.println("Lo sentimos, este producto está agotado.");
            }

            String opcion = "";
            while (true) {
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1. Seguir eligiendo otro producto");
                System.out.println("2. Finalizar compra y pagar");
                System.out.println("3. Cancelar compra");
                System.out.print("Elija una opción (1-3): ");

                opcion = entrada.nextLine().trim();

                if (opcion.equals("1") || opcion.equals("2") || opcion.equals("3")) {
                    break;
                } else {
                    System.out.println("Opción no válida. Por favor digite 1, 2 o 3.");
                }
            }

            if (opcion.equals("2")) {
                if (productosVendidos.isEmpty()) {
                    System.out.println("No hay productos en la venta. Operación cancelada.");
                    contadorVentas--;
                    pausar(entrada);
                    return;
                }

                if (realizarPago(entrada)) {
                    for (int i = 0; i < productosVendidos.size(); i++) {
                        Producto prod = productosVendidos.get(i);
                        int cant = cantidadesVendidas.get(i);
                        prod.actualizarStock(-cant);
                    }
                    
                    // Actualiza automáticamente el fichero de inventario con el stock reducido
                    inventario.actualizarCambiosStock();

                    historialVentas.add(this);
                    System.out.println("\n¡Venta #" + idVenta + " procesada y guardada en el historial!");
                } else {
                    contadorVentas--;
                    System.out.println("Pago cancelado o fallido. La venta no se completó.");
                }
                pausar(entrada);
                continuarComprando = false;

            } else if (opcion.equals("3")) {
                contadorVentas--;
                System.out.println("Compra cancelada por el usuario.");
                pausar(entrada);
                continuarComprando = false;
            }
        }
    }

    private boolean realizarPago(Scanner entrada) {
        System.out.println("\n----------------------------------------");
        System.out.println("TOTAL A PAGAR: $" + totalVenta);
        System.out.println("Seleccione el método de pago:");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta de Crédito / Débito");
        System.out.println("3. Nequi / Transferencia");

        String metodo = "";
        while (true) {
            System.out.print("Elija una opción (1-3): ");
            metodo = entrada.nextLine().trim();
            if (metodo.equals("1") || metodo.equals("2") || metodo.equals("3")) {
                break;
            }
            System.out.println("Método de pago no válido. Intente de nuevo.");
        }

        switch (metodo) {
            case "1":
                this.metodoPago = "Efectivo";
                while (true) {
                    System.out.print("Ingrese el dinero entregado por el cliente: $");
                    String lineDinero = entrada.nextLine().trim();
                    try {
                        double dinero = Double.parseDouble(lineDinero);
                        if (dinero >= totalVenta) {
                            System.out.println("Cambio a entregar: $" + (dinero - totalVenta));
                            break;
                        } else {
                            System.out.println("Dinero insuficiente. Intente de nuevo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Monto inválido. Ingrese un valor numérico.");
                    }
                }
                break;
            case "2":
                this.metodoPago = "Tarjeta";
                System.out.println("Procesando pago con tarjeta... ¡Aprobado!");
                break;
            case "3":
                this.metodoPago = "Nequi / Transferencia";
                System.out.println("Verificando transferencia... ¡Confirmada!");
                break;
        }

        System.out.println("¡Pago exitoso mediante " + this.metodoPago + "!");
        return true;
    }

    private void pausar(Scanner entrada) {
        System.out.println("\nPresione Enter para continuar...");
        entrada.nextLine();
    }
}