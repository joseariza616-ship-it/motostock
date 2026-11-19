package modelo;


import modelo.Usuario;



public class Empleado extends Usuario {
    private String turno;

    public Empleado(int idUsuario, String nombre, String correo, String contrasena, String telefono, String turno) {
        super(idUsuario, nombre, correo, contrasena, telefono);
        this.turno = turno;
    }

    public String getTurno() { return turno; }

    @Override
    public String getRol() {
        return "Empleado";
    }
}