package modelo;

import modelo.Categoria;

public class Producto {
    private int idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String foto;
    private Categoria categoria;

    public Producto(int idProducto, String codigo, String nombre, String descripcion, double precio, int stock, String foto, Categoria categoria) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.foto = foto;
        this.categoria = categoria;
    }

    public int getIdProducto() { return idProducto; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public String getFoto() { return foto; }
    public Categoria getCategoria() { return categoria; }

    // Métodos de modificación (Setters)
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public void setPrecio(double precio) { 
        this.precio = precio; 
    }

    public boolean tieneStockDisponible(int cantidad) {
        return this.stock >= cantidad;
    }

    public void actualizarStock(int cantidad) {
        this.stock += cantidad;
    }
}