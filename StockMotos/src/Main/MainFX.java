package Main;

import gestor.GestionUsuarios;
import gestor.Inventario;
import javafx.application.Application;
import javafx.stage.Stage;
import modelo.Administrador;
import modelo.Categoria;
import modelo.Producto;
import modelo.Usuario;
import vista.Vista;
import vista.VistaLogin;

public class MainFX extends Application {

    private GestionUsuarios gestionUsuarios;
    private Inventario inventario;

    @Override
    public void start(Stage primaryStage) {
        gestionUsuarios = new GestionUsuarios();
        inventario = new Inventario();

        // Administradores por defecto (contraseña: 24)
        gestionUsuarios.agregarUsuarioSinMensaje(new Administrador(1, "jhoiner", "jhoiner@servimotos.com", "24", "3001234567"));
        gestionUsuarios.agregarUsuarioSinMensaje(new Administrador(2, "Sheyla", "sheyla@servimotos.com", "24", "3001112233"));
        gestionUsuarios.agregarUsuarioSinMensaje(new Administrador(3, "Jose", "jose@servimotos.com", "24", "3002223344"));
        gestionUsuarios.agregarUsuarioSinMensaje(new Administrador(4, "Darlys", "darlys@servimotos.com", "24", "3003334455"));

        // Categoría base y productos precargados
        // (Inventario ignora automáticamente los que ya existan por código)
        Categoria catRepuestos = new Categoria(1, "Repuestos", "Partes y refacciones para motos");
        inventario.agregarProducto(new Producto(1, "001", "Bujia NGK Boxer", "", 15000, 10, "sin_foto", catRepuestos));
        inventario.agregarProducto(new Producto(2, "002", "Bujia NGK AKT NKD", "", 18000, 8, "sin_foto", catRepuestos));
        inventario.agregarProducto(new Producto(3, "003", "Bujia NGK Pulsar", "", 25000, 12, "sin_foto", catRepuestos));

        mostrarLogin();
    }

    private void mostrarLogin() {
        Stage ventanaLogin = new Stage();
        VistaLogin login = new VistaLogin(gestionUsuarios);
        login.mostrar(ventanaLogin, usuario -> {
            abrirPanel(usuario);   // primero se abre el panel...
            ventanaLogin.close();  // ...y luego se cierra el login
        });
    }

    private void abrirPanel(Usuario usuario) {
        Stage ventanaPanel = new Stage();
        Vista vista = new Vista(inventario, gestionUsuarios, usuario, () -> {
            mostrarLogin();        // al cerrar sesión vuelve el login
            ventanaPanel.close();
        });
        vista.mostrarVentanaPrincipal(ventanaPanel);
    }

    public static void main(String[] args) {
        launch(args);
    }
}