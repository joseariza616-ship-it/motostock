package modelo;

public class Categoria {
    private int idCategoria;
    private String nombre;
    private String descripcion;

    // Constructor completo
    public Categoria(int idCategoria, String nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters
    public int getIdCategoria() { 
        return idCategoria; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public String getDescripcion() { 
        return descripcion; 
    }

    // Setters (útiles por si necesitas modificar la categoría luego)
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}