package modelo;


import modelo.Usuario;



public class Cliente extends Usuario {
    private String direccion;

    public Cliente(int idUsuario, String nombre, String correo, String contrasena, String telefono, String direccion) {
        super(idUsuario, nombre, correo, contrasena, telefono);
        this.direccion = direccion;
    }

    public String getDireccion() { return direccion; }

    @Override
    public String getRol() {
        return "Cliente";
    }
}