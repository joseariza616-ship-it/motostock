package modelo;



public abstract class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;

    public Usuario(int idUsuario, String nombre, String correo, String contrasena, String telefono) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        return this.correo.equals(correo) && this.contrasena.equals(contrasena);
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }

    public abstract String getRol();
}