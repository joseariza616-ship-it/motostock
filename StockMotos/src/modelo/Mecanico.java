package modelo;


import modelo.Usuario;



public class Mecanico extends Usuario {
    private String especialidad;

    public Mecanico(int idUsuario, String nombre, String correo, String contrasena, String telefono, String especialidad) {
        super(idUsuario, nombre, correo, contrasena, telefono);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() { return especialidad; }

    @Override
    public String getRol() {
        return "Mecanico";
    }
}