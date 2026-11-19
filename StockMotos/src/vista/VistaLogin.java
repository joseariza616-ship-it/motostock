package vista;

import gestor.GestionUsuarios;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Usuario;

public class VistaLogin {

    private GestionUsuarios gestionUsuarios;

    public VistaLogin(GestionUsuarios gestionUsuarios) {
        this.gestionUsuarios = gestionUsuarios;
    }

    // alIngresar se ejecuta solo cuando el usuario se autentica correctamente
    public void mostrar(Stage stage, Consumer<Usuario> alIngresar) {
        stage.setTitle("StockMotos - Iniciar sesión");

        Label lblLogo = new Label("StockMotos");
        lblLogo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label lblSub = new Label("Inicia sesión para continuar");
        lblSub.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");
        VBox encabezado = new VBox(4, lblLogo, lblSub);
        encabezado.setAlignment(Pos.CENTER);

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Correo o usuario");
        txtUsuario.setStyle(estiloCampo());

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.setStyle(estiloCampo());

        Button btnIngresar = new Button("Ingresar");
        btnIngresar.setMaxWidth(Double.MAX_VALUE);
        btnIngresar.setStyle("-fx-background-color: #0f172a; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-background-radius: 6px; -fx-padding: 10px; -fx-cursor: hand;");

        Label lblMensaje = new Label("");
        lblMensaje.setWrapText(true);
        lblMensaje.setStyle("-fx-font-size: 12px; -fx-text-fill: #dc2626;");

        VBox tarjeta = new VBox(14, encabezado, txtUsuario, txtPassword, btnIngresar, lblMensaje);
        tarjeta.setPadding(new Insets(30));
        tarjeta.setMaxWidth(340);
        tarjeta.setMaxHeight(Region.USE_PREF_SIZE);
        tarjeta.setStyle("-fx-background-color: white; -fx-background-radius: 12px; "
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.10), 14, 0, 0, 3);");

        StackPane fondo = new StackPane(tarjeta);
        fondo.setStyle("-fx-background-color: #f4f6f9;");

        Runnable intentarIngreso = () -> {
            String credencial = txtUsuario.getText().trim();
            String password = txtPassword.getText().trim();

            if (credencial.isEmpty() || password.isEmpty()) {
                lblMensaje.setText("Ingresa tu correo o usuario y tu contraseña.");
                return;
            }

            Usuario usuario = gestionUsuarios.autenticar(credencial, password);

            if (usuario == null) {
                lblMensaje.setText("Credenciales incorrectas. Intenta de nuevo.");
                txtPassword.clear();
                txtPassword.requestFocus();
                return;
            }

            // Cualquier rol puede entrar: el panel cambia según el rol
            alIngresar.accept(usuario);
        };

        btnIngresar.setOnAction(e -> intentarIngreso.run());
        txtPassword.setOnAction(e -> intentarIngreso.run());
        txtUsuario.setOnAction(e -> txtPassword.requestFocus());

        Scene scene = new Scene(fondo, 480, 460);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private String estiloCampo() {
        return "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-border-color: #cbd5e1; -fx-padding: 10px;";
    }
}