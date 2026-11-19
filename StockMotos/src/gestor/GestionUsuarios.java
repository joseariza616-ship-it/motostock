package gestor;

import modelo.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GestionUsuarios {
    private ArrayList<Usuario> usuarios;
    private static final String NOMBRE_ARCHIVO = "usuarios.txt";

    public GestionUsuarios() {
        this.usuarios = new ArrayList<>();
        cargarDesdeArchivo();
        
        // Precargamos por defecto al mecánico Jesús si no existe
        verificarYAgregarDefecto(new Mecanico(5, "Jesus", "jesus@servimotos.com", "24", "3009998877", "Motor"));
    }

    private void verificarYAgregarDefecto(Usuario usuarioDefecto) {
        boolean existe = false;
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(usuarioDefecto.getCorreo()) || u.getNombre().equalsIgnoreCase(usuarioDefecto.getNombre())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            usuarios.add(usuarioDefecto);
            guardarEnArchivo();
        }
    }

    public void agregarUsuario(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                return;
            }
        }
        usuarios.add(usuario);
        guardarEnArchivo();
        System.out.println("¡Usuario registrado y guardado en fichero con éxito!");
    }

    public void agregarUsuarioSinMensaje(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                return;
            }
        }
        usuarios.add(usuario);
        guardarEnArchivo();
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario autenticar(String credencial, String contrasena) {
        for (Usuario u : usuarios) {
            boolean coincideUsuario = u.getCorreo().equalsIgnoreCase(credencial) || u.getNombre().equalsIgnoreCase(credencial);
            if (coincideUsuario && u.iniciarSesion(u.getCorreo(), contrasena)) {
                return u;
            }
        }
        return null;
    }

    // Método para mostrar usuarios en modo consola (evita errores en el Main antiguo)
    public void mostrarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("\n--- LISTA DE USUARIOS ---");
            for (Usuario u : usuarios) {
                System.out.println("ID: " + u.getIdUsuario());
                System.out.println("Nombre: " + u.getNombre());
                System.out.println("Rol: " + u.getRol());
                System.out.println("Correo: " + u.getCorreo());
                System.out.println("Teléfono: " + u.getTelefono());
                System.out.println("----------------------------------------\n");
            }
        }
    }

    // Método para modificar usuario de forma segura
    public boolean modificarUsuario(int id, String nuevoNombre, String nuevoCorreo, String nuevoTelefono, String rolActual) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getIdUsuario() == id) {
                Usuario actualizado = null;
                if (rolActual.equalsIgnoreCase("Administrador")) {
                    actualizado = new Administrador(id, nuevoNombre, nuevoCorreo, "24", nuevoTelefono);
                } else if (rolActual.equalsIgnoreCase("Cliente")) {
                    actualizado = new Cliente(id, nuevoNombre, nuevoCorreo, "24", nuevoTelefono, "Sin dirección");
                } else if (rolActual.equalsIgnoreCase("Empleado")) {
                    actualizado = new Empleado(id, nuevoNombre, nuevoCorreo, "24", nuevoTelefono, "General");
                } else if (rolActual.equalsIgnoreCase("Mecanico") || rolActual.equalsIgnoreCase("Mecánico")) {
                    actualizado = new Mecanico(id, nuevoNombre, nuevoCorreo, "24", nuevoTelefono, "Motor");
                }

                if (actualizado != null) {
                    usuarios.set(i, actualizado);
                    guardarEnArchivo();
                    return true;
                }
            }
        }
        return false;
    }

    // Método para eliminar usuario por correo
    public boolean eliminarUsuarioPorCorreo(String correo) {
        Usuario aEliminar = null;
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                aEliminar = u;
                break;
            }
        }
        if (aEliminar != null) {
            usuarios.remove(aEliminar);
            guardarEnArchivo();
            return true;
        }
        return false;
    }

    // --- PERSISTENCIA AUTOMÁTICA EN FICHERO ---
    private void guardarEnArchivo() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (Usuario u : usuarios) {
                String pass = "24";
                escritor.println(u.getIdUsuario() + "," + u.getNombre() + "," + u.getCorreo() + "," + pass + "," + u.getTelefono() + "," + u.getRol());
            }
        } catch (Exception e) {
            System.out.println("[Error al guardar usuarios]: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) return;

        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",");
                if (partes.length >= 6) {
                    try {
                        int id = Integer.parseInt(partes[0]);
                        String nombre = partes[1];
                        String correo = partes[2];
                        String pass = partes[3];
                        String telefono = partes[4];
                        String rol = partes[5];

                        Usuario u = null;
                        if (rol.equalsIgnoreCase("Administrador")) {
                            u = new Administrador(id, nombre, correo, pass, telefono);
                        } else if (rol.equalsIgnoreCase("Cliente")) {
                            u = new Cliente(id, nombre, correo, pass, telefono, "Sin dirección");
                        } else if (rol.equalsIgnoreCase("Empleado")) {
                            u = new Empleado(id, nombre, correo, pass, telefono, "General");
                        } else if (rol.equalsIgnoreCase("Mecanico") || rol.equalsIgnoreCase("Mecánico")) {
                            u = new Mecanico(id, nombre, correo, pass, telefono, "Motor");
                        }

                        if (u != null) {
                            boolean existe = false;
                            for (Usuario existente : usuarios) {
                                if (existente.getCorreo().equalsIgnoreCase(u.getCorreo())) {
                                    existe = true;
                                    break;
                                }
                            }
                            if (!existe) {
                                usuarios.add(u);
                            }
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("[Aviso] Línea de usuario ignorada: " + linea);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[Error al cargar usuarios]: " + e.getMessage());
        }
    }
}