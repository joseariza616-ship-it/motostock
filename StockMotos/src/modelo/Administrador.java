package modelo;


import modelo.Usuario;



public class Administrador extends Usuario {

    public Administrador(int idUsuario, String nombre, String correo, String contrasena, String telefono) {
        super(idUsuario, nombre, correo, contrasena, telefono);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }
}