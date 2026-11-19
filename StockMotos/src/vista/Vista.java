package vista;

import controller.Venta;
import gestor.GestionUsuarios;
import gestor.Inventario;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Administrador;
import modelo.Categoria;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Mecanico;
import modelo.Producto;
import modelo.Usuario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Vista {

    private static final String VERDE_OSCURO = "#14532d";
    private static final String VERDE_HOVER = "#166534";
    private static final String VERDE_CLARO = "#dcfce7";
    private static final String VERDE_ACENTO = "#86efac";
    private static final String FONDO = "#f3f7f4";

    private static final int STOCK_BAJO = 5;
    private static final String ESTILO_CAMPO = "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-border-color: #cbd5e1; -fx-padding: 8px;";
    private static final String ESTILO_TARJETA = "-fx-background-color: white; -fx-background-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 8, 0, 0, 2);";
    private static final String ESTILO_BOTON_PRINCIPAL = "-fx-background-color: " + VERDE_OSCURO + "; -fx-text-fill: white; -fx-background-radius: 6px; -fx-padding: 9px 18px; -fx-cursor: hand;";
    private static final String ESTILO_BOTON_ROJO = "-fx-background-color: #dc2626; -fx-text-fill: white; -fx-background-radius: 6px; -fx-padding: 9px 18px; -fx-cursor: hand;";

    private Inventario inventario;
    private GestionUsuarios gestionUsuarios;
    private Usuario usuarioActual;
    private Runnable alCerrarSesion;
    private BorderPane root;
    private static ArrayList<Venta> historialVentas = new ArrayList<>();
    private Categoria categoriaBase = new Categoria(1, "Repuestos", "Partes y refacciones para motos");

    public Vista(Inventario inventario, GestionUsuarios gestionUsuarios, Usuario usuarioActual, Runnable alCerrarSesion) {
        this.inventario = inventario;
        this.gestionUsuarios = gestionUsuarios;
        this.usuarioActual = usuarioActual;
        this.alCerrarSesion = alCerrarSesion;
    }

    public void mostrarVentanaPrincipal(Stage stage) {
        String rol = usuarioActual.getRol();
        boolean esAdmin = rol.equalsIgnoreCase("Administrador");
        boolean esCliente = rol.equalsIgnoreCase("Cliente");

        stage.setTitle("StockMotos - " + usuarioActual.getNombre() + " (" + rol + ")");

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + FONDO + ";");

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: " + VERDE_OSCURO + ";");
        sidebar.setPrefWidth(220);

        Label logoLabel = crearLabelEstilizada("StockMotos", "18px", Color.WHITE, true);
        Label subLogoLabel = crearLabelEstilizada("Gestión Comercial", "11px", Color.web(VERDE_ACENTO), false);
        VBox logoBox = new VBox(2, logoLabel, subLogoLabel);
        logoBox.setPadding(new Insets(0, 0, 20, 0));
        sidebar.getChildren().add(logoBox);

        if (esAdmin) {
            Button btnPanel = crearBotonMenu("📊 Panel General");
            Button btnProductos = crearBotonMenu("📦 Productos / Inventario");
            Button btnUsuariosReg = crearBotonMenu("👥 Registrar Usuario");
            Button btnVerUsuarios = crearBotonMenu("📋 Ver y Gestionar Usuarios");
            Button btnVentas = crearBotonMenu("🛒 Realizar Venta");
            Button btnReportes = crearBotonMenu("📈 Reportes");

            btnPanel.setOnAction(e -> root.setCenter(crearPanelGeneral(stage)));
            btnProductos.setOnAction(e -> root.setCenter(crearPanelProductos(
                    "Productos / Inventario",
                    "Consulta el stock, agrega, modifica o elimina productos", true)));
            btnUsuariosReg.setOnAction(e -> root.setCenter(crearPanelUsuarios()));
            btnVerUsuarios.setOnAction(e -> root.setCenter(crearPanelListaUsuarios()));
            btnVentas.setOnAction(e -> root.setCenter(crearPanelRealizarVenta()));
            btnReportes.setOnAction(e -> root.setCenter(crearPanelSelectorReportes(stage)));

            sidebar.getChildren().addAll(btnPanel, btnProductos, btnUsuariosReg, btnVerUsuarios, btnVentas, btnReportes);
            root.setCenter(crearPanelGeneral(stage));

        } else if (esCliente) {
            Button btnCatalogo = crearBotonMenu("📦 Catálogo de productos");
            Button btnComprar = crearBotonMenu("🛒 Comprar productos");

            btnCatalogo.setOnAction(e -> root.setCenter(crearPanelProductos(
                    "Catálogo de productos", "Busca por código o nombre", false)));
            btnComprar.setOnAction(e -> root.setCenter(crearPanelRealizarVenta()));

            sidebar.getChildren().addAll(btnCatalogo, btnComprar);
            root.setCenter(crearPanelProductos("Catálogo de productos", "Busca por código o nombre", false));

        } else {
            if (rol.equalsIgnoreCase("Mecanico") || rol.equalsIgnoreCase("Mecánico")) {
                Button btnInventario = crearBotonMenu("📦 Ver inventario");
                Button btnMantenimiento = crearBotonMenu("🛠️ Registrar Mantenimiento");

                btnInventario.setOnAction(e -> root.setCenter(crearPanelProductos(
                        "Inventario de Repuestos", "Consulta el stock disponible para taller", false)));
                btnMantenimiento.setOnAction(e -> root.setCenter(crearPanelMantenimiento()));

                sidebar.getChildren().addAll(btnInventario, btnMantenimiento);
                root.setCenter(crearPanelMantenimiento());
            } else {
                Button btnInventario = crearBotonMenu("📦 Ver inventario");
                Button btnVentas = crearBotonMenu("🛒 Realizar Venta");

                btnInventario.setOnAction(e -> root.setCenter(crearPanelProductos(
                        "Inventario", "Consulta el stock (en rojo: stock bajo)", false)));
                btnVentas.setOnAction(e -> root.setCenter(crearPanelRealizarVenta()));

                sidebar.getChildren().addAll(btnInventario, btnVentas);
                root.setCenter(crearPanelProductos("Inventario", "Consulta el stock (en rojo: stock bajo)", false));
            }
        }

        Region espacio = new Region();
        VBox.setVgrow(espacio, Priority.ALWAYS);

        Label lblNombre = crearLabelEstilizada(usuarioActual.getNombre(), "13px", Color.WHITE, true);
        Label lblRol = crearLabelEstilizada(rol, "11px", Color.web(VERDE_ACENTO), false);
        VBox infoUsuario = new VBox(2, lblNombre, lblRol);
        infoUsuario.setPadding(new Insets(0, 0, 5, 15));

        Button btnSalir = crearBotonMenu("🚪 Cerrar sesión");
        btnSalir.setOnAction(e -> {
            if (alCerrarSesion != null) {
                alCerrarSesion.run();
            }
        });

        sidebar.getChildren().addAll(espacio, infoUsuario, btnSalir);
        root.setLeft(sidebar);

        Scene scene = new Scene(root, 1100, 680);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearPanelGeneral(Stage ownerStage) {
        VBox contentArea = new VBox(20);
        contentArea.setPadding(new Insets(25));

        Label titulo = crearLabelEstilizada("Resumen general", "22px", Color.web(VERDE_OSCURO), true);
        Label subtitulo = crearLabelEstilizada("Una vista rápida del estado del negocio y stock actual", "13px", Color.web("#64748b"), false);
        VBox titleBox = new VBox(3, titulo, subtitulo);

        ArrayList<Producto> productosBajos = new ArrayList<>();
        for (Producto p : inventario.getProductos()) {
            if (p.getStock() <= STOCK_BAJO) {
                productosBajos.add(p);
            }
        }

        HBox cardsBox = new HBox(20);
        
        double totalHoy = 0;
        for (Venta v : historialVentas) totalHoy += v.getTotalVenta();
        VBox cardIngresos = crearTarjetaMetrica("Ingresos totales", "$" + String.format("%,.2f", totalHoy), "Ventas registradas");

        double ticketPromedio = historialVentas.isEmpty() ? 0 : totalHoy / historialVentas.size();
        VBox cardTicket = crearTarjetaMetrica("Ticket promedio", "$" + String.format("%,.2f", ticketPromedio), "Promedio por venta");

        VBox cardAgotarse = new VBox(8);
        cardAgotarse.setPadding(new Insets(15));
        cardAgotarse.setPrefWidth(240);
        cardAgotarse.setStyle(ESTILO_TARJETA + "; -fx-cursor: hand; -fx-border-color: #fca5a5; -fx-border-radius: 8px;");

        Label lblTitAgotarse = crearLabelEstilizada("Productos por agotarse", "12px", Color.web("#dc2626"), true);
        Label lblValAgotarse = crearLabelEstilizada(productosBajos.size() + " items", "20px", Color.web("#dc2626"), true);
        Label lblDescAgotarse = crearLabelEstilizada("Stock crítico (<= " + STOCK_BAJO + ") - Haz clic aquí", "11px", Color.web("#94a3b8"), false);
        cardAgotarse.getChildren().addAll(lblTitAgotarse, lblValAgotarse, lblDescAgotarse);

        cardAgotarse.setOnMouseClicked(e -> mostrarVentanaProductosAgotarse(ownerStage, productosBajos));

        cardsBox.getChildren().addAll(cardIngresos, cardTicket, cardAgotarse);
        contentArea.getChildren().addAll(titleBox, cardsBox);
        return contentArea;
    }

    private void mostrarVentanaProductosAgotarse(Stage ownerStage, ArrayList<Producto> listaBajos) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.WINDOW_MODAL);
        modalStage.initOwner(ownerStage);
        modalStage.setTitle("Detalle de Productos por Agotarse");

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: " + FONDO + ";");

        Label lblTituloModal = crearLabelEstilizada("⚠️ Productos con Stock Crítico", "18px", Color.web("#dc2626"), true);
        Label lblSubModal = crearLabelEstilizada("Lista completa de los ítems que se están agotando:", "13px", Color.web("#64748b"), false);

        ObservableList<Producto> obsBajos = FXCollections.observableArrayList(listaBajos);
        TableView<Producto> tablaModal = new TableView<>(obsBajos);
        tablaModal.setPlaceholder(new Label("¡Excelente! No hay productos por agotarse."));
        tablaModal.setPrefHeight(300);

        TableColumn<Producto, String> colCod = new TableColumn<>("Código");
        colCod.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colCod.setPrefWidth(90);

        TableColumn<Producto, String> colNom = new TableColumn<>("Nombre del Producto");
        colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colNom.setPrefWidth(260);

        TableColumn<Producto, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().getPrecio())));
        colPrecio.setPrefWidth(110);

        TableColumn<Producto, Number> colStock = new TableColumn<>("Cantidad (Stock)");
        colStock.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getStock()));
        colStock.setPrefWidth(120);

        tablaModal.getColumns().addAll(colCod, colNom, colPrecio, colStock);

        Button btnCerrar = new Button("Cerrar Ventana");
        btnCerrar.setStyle(ESTILO_BOTON_PRINCIPAL);
        btnCerrar.setOnAction(e -> modalStage.close());

        HBox bottomBox = new HBox(btnCerrar);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        rootModal.getChildren().addAll(lblTituloModal, lblSubModal, tablaModal, bottomBox);

        Scene sceneModal = new Scene(rootModal, 620, 420);
        modalStage.setScene(sceneModal);
        modalStage.showAndWait();
    }

    private VBox crearPanelSelectorReportes(Stage stage) {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(25));

        Label titulo = crearLabelEstilizada("Centro de Reportes", "22px", Color.web(VERDE_OSCURO), true);
        Label subtitulo = crearLabelEstilizada("Selecciona el reporte que deseas consultar y gestiona su descarga", "13px", Color.web("#64748b"), false);
        VBox titleBox = new VBox(3, titulo, subtitulo);

        ComboBox<String> cmbTipoReporte = new ComboBox<>(FXCollections.observableArrayList(
            "1. Reporte de Ganancias (General y Desglose por Método de Pago)",
            "2. Reporte de Stock de Salidas (Productos Vendidos)",
            "3. Reporte Detallado de Cada Venta (Descarga en TXT)"
        ));
        cmbTipoReporte.setValue("1. Reporte de Ganancias (General y Desglose por Método de Pago)");
        cmbTipoReporte.setPrefWidth(480);
        cmbTipoReporte.setStyle(ESTILO_CAMPO);

        VBox contenedorContenidoReporte = new VBox(15);
        VBox.setVgrow(contenedorContenidoReporte, Priority.ALWAYS);

        Button btnDescargarPDF = new Button("📥 Descargar Reporte en PDF");
        btnDescargarPDF.setStyle(ESTILO_BOTON_PRINCIPAL);

        btnDescargarPDF.setOnAction(e -> {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                boolean proceed = job.showPrintDialog(stage);
                if (proceed) {
                    boolean success = job.printPage(contenedorContenidoReporte);
                    if (success) {
                        job.endJob();
                    }
                }
            }
        });

        Button btnDescargarTXT = new Button("📄 Descargar Reporte Detallado en TXT");
        btnDescargarTXT.setStyle("-fx-background-color: #0284c7; -fx-text-fill: white; -fx-background-radius: 6px; -fx-padding: 9px 18px; -fx-cursor: hand;");

        btnDescargarTXT.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte Detallado por Factura");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de texto (*.txt)", "*.txt"));
            fileChooser.setInitialFileName("Reporte_Detallado_Facturas.txt");
            File archivo = fileChooser.showSaveDialog(stage);

            if (archivo != null) {
                try (FileWriter writer = new FileWriter(archivo)) {
                    writer.write("==================================================\n");
                    writer.write("       REPORTE DETALLADO POR FACTURA - STOCKMOTOS \n");
                    writer.write("==================================================\n\n");

                    if (historialVentas.isEmpty()) {
                        writer.write("No hay facturas registradas en el sistema.\n");
                    } else {
                        for (Venta v : historialVentas) {
                            writer.write("Factura Nro: " + v.getIdVenta() + "\n");
                            writer.write("Método de Pago: " + v.getMetodoPago() + "\n");
                            writer.write("--------------------------------------------------\n");
                            writer.write(String.format("%-25s | %-8s | %-12s | %-12s\n", "Producto", "Cant.", "P. Unitario", "Subtotal"));
                            writer.write("--------------------------------------------------\n");

                            ArrayList<Producto> prods = v.getProductosVendidos();
                            ArrayList<Integer> cants = v.getCantidadesVendidas();

                            for (int i = 0; i < prods.size(); i++) {
                                Producto p = prods.get(i);
                                int cant = cants.get(i);
                                double subtotal = p.getPrecio() * cant;
                                String nombreCorto = p.getNombre().length() > 25 ? p.getNombre().substring(0, 22) + "..." : p.getNombre();
                                writer.write(String.format("%-25s | %-8d | $%,-11.2f | $%,-11.2f\n", nombreCorto, cant, p.getPrecio(), subtotal));
                            }
                            writer.write("--------------------------------------------------\n");
                            writer.write(String.format("TOTAL FACTURA: $%,.2f\n\n", v.getTotalVenta()));
                        }
                    }
                    writer.write("==================================================\n");
                    writer.write("Fin del reporte.\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cmbTipoReporte.setOnAction(e -> {
            String seleccionado = cmbTipoReporte.getValue();
            contenedorContenidoReporte.getChildren().clear();

            if (seleccionado.startsWith("1")) {
                contenedorContenidoReporte.getChildren().addAll(crearVistaReporteGanancias(), btnDescargarPDF);
            } else if (seleccionado.startsWith("2")) {
                contenedorContenidoReporte.getChildren().addAll(crearVistaReporteStockSalidas(), btnDescargarPDF);
            } else {
                contenedorContenidoReporte.getChildren().addAll(crearVistaReporteDetalladoFacturasVista(), btnDescargarTXT);
            }
        });

        contenedorContenidoReporte.getChildren().addAll(crearVistaReporteGanancias(), btnDescargarPDF);

        panel.getChildren().addAll(titleBox, cmbTipoReporte, contenedorContenidoReporte);
        return panel;
    }

    private VBox crearVistaReporteGanancias() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(10, 0, 0, 0));

        double totalEfectivo = 0;
        double totalTarjeta = 0;
        double totalNequi = 0;
        double gananciaTotal = 0;
        int totalUnidades = 0;

        for (Venta v : historialVentas) {
            double t = v.getTotalVenta();
            gananciaTotal += t;
            for (int cant : v.getCantidadesVendidas()) {
                totalUnidades += cant;
            }

            String m = v.getMetodoPago().toLowerCase();
            if (m.contains("efectivo")) {
                totalEfectivo += t;
            } else if (m.contains("tarjeta")) {
                totalTarjeta += t;
            } else if (m.contains("nequi") || m.contains("transferencia")) {
                totalNequi += t;
            }
        }

        VBox tarjeta = new VBox(14);
        tarjeta.setPadding(new Insets(20));
        tarjeta.setMaxWidth(550);
        tarjeta.setStyle(ESTILO_TARJETA);

        tarjeta.getChildren().addAll(
            crearLabelEstilizada("Resumen Financiero de Ganancias", "16px", Color.web(VERDE_OSCURO), true),
            new Label("• Total de Facturas Procesadas: " + historialVentas.size()),
            new Label("• Total de Unidades Salientes: " + totalUnidades),
            new Label("• Ganancias en Efectivo: $" + String.format("%,.2f", totalEfectivo)),
            new Label("• Ganancias en Tarjeta: $" + String.format("%,.2f", totalTarjeta)),
            new Label("• Ganancias en Nequi / Transferencia: $" + String.format("%,.2f", totalNequi)),
            crearLabelEstilizada("GANANCIA BRUTA TOTAL: $" + String.format("%,.2f", gananciaTotal), "15px", Color.web(VERDE_OSCURO), true)
        );

        box.getChildren().add(tarjeta);
        return box;
    }

    private VBox crearVistaReporteStockSalidas() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 0, 0, 0));

        ObservableList<DetalleSalida> detalles = FXCollections.observableArrayList();
        for (Venta v : historialVentas) {
            ArrayList<Producto> prods = v.getProductosVendidos();
            ArrayList<Integer> cants = v.getCantidadesVendidas();
            for (int i = 0; i < prods.size(); i++) {
                detalles.add(new DetalleSalida(v.getIdVenta(), prods.get(i), cants.get(i), v.getMetodoPago()));
            }
        }

        TableView<DetalleSalida> tabla = new TableView<>(detalles);
        tabla.setPlaceholder(new Label("No hay salidas de stock registradas."));
        tabla.setPrefHeight(350);

        TableColumn<DetalleSalida, Number> colFac = new TableColumn<>("Factura #");
        colFac.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().idFactura));
        colFac.setPrefWidth(90);

        TableColumn<DetalleSalida, String> colCod = new TableColumn<>("Código");
        colCod.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().producto.getCodigo()));
        colCod.setPrefWidth(90);

        TableColumn<DetalleSalida, String> colNom = new TableColumn<>("Nombre del Producto");
        colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().producto.getNombre()));
        colNom.setPrefWidth(280);

        TableColumn<DetalleSalida, Number> colCant = new TableColumn<>("Cantidad Saliente");
        colCant.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().cantidad));
        colCant.setPrefWidth(120);

        TableColumn<DetalleSalida, String> colPrecio = new TableColumn<>("Precio Unitario");
        colPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().producto.getPrecio())));
        colPrecio.setPrefWidth(130);

        TableColumn<DetalleSalida, String> colPago = new TableColumn<>("Método de Pago");
        colPago.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().metodoPago));
        colPago.setPrefWidth(130);

        tabla.getColumns().addAll(colFac, colCod, colNom, colCant, colPrecio, colPago);
        VBox.setVgrow(tabla, Priority.ALWAYS);

        box.getChildren().add(tabla);
        return box;
    }

    private VBox crearVistaReporteDetalladoFacturasVista() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 0, 0, 0));

        VBox tarjetaInfo = new VBox(8);
        tarjetaInfo.setPadding(new Insets(15));
        tarjetaInfo.setMaxWidth(600);
        tarjetaInfo.setStyle(ESTILO_TARJETA);

        tarjetaInfo.getChildren().addAll(
            crearLabelEstilizada("Reporte Detallado de Cada Venta", "15px", Color.web(VERDE_OSCURO), true),
            new Label("• Total de Facturas Registradas: " + historialVentas.size()),
            new Label("• Formato de salida: Archivo de texto (.txt) estructurado por factura con desglose de ítems, cantidades y subtotales.")
        );

        box.getChildren().add(tarjetaInfo);
        return box;
    }

    public static class DetalleSalida {
        public int idFactura;
        public Producto producto;
        public int cantidad;
        public String metodoPago;

        public DetalleSalida(int idFactura, Producto producto, int cantidad, String metodoPago) {
            this.idFactura = idFactura;
            this.producto = producto;
            this.cantidad = cantidad;
            this.metodoPago = metodoPago;
        }
    }

    public static class ItemCarrito {
        private Producto producto;
        private int cantidad;

        public ItemCarrito(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public Producto getProducto() { return producto; }
        public String getCodigo() { return producto.getCodigo(); }
        public String getNombre() { return producto.getNombre(); }
        public double getPrecioUnitario() { return producto.getPrecio(); }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public double getSubtotal() { return producto.getPrecio() * cantidad; }
    }

    private VBox crearPanelRealizarVenta() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));

        Label titulo = crearLabelEstilizada("Punto de Venta / Carrito", "22px", Color.web(VERDE_OSCURO), true);
        Label subtitulo = crearLabelEstilizada("Busca el producto, selecciónalo con clic, añade la cantidad y gestiona tu factura", "13px", Color.web("#64748b"), false);
        VBox titleBox = new VBox(3, titulo, subtitulo);

        TextField txtBuscarProd = crearCampo("Buscar producto por nombre o código para seleccionar...");
        ObservableList<Producto> listaDisponibles = FXCollections.observableArrayList(inventario.getProductos());
        FilteredList<Producto> filtradosDisponibles = new FilteredList<>(listaDisponibles, p -> true);

        txtBuscarProd.textProperty().addListener((obs, anterior, actual) -> {
            String filtro = actual.trim().toLowerCase();
            filtradosDisponibles.setPredicate(p -> filtro.isEmpty()
                    || p.getCodigo().toLowerCase().contains(filtro)
                    || p.getNombre().toLowerCase().contains(filtro));
        });

        TableView<Producto> tablaDisponibles = new TableView<>(filtradosDisponibles);
        tablaDisponibles.setPrefHeight(160);
        tablaDisponibles.setPlaceholder(new Label("No hay productos encontrados."));

        TableColumn<Producto, String> colCatCod = new TableColumn<>("Código");
        colCatCod.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colCatCod.setPrefWidth(90);

        TableColumn<Producto, String> colCatNom = new TableColumn<>("Nombre del Producto");
        colCatNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colCatNom.setPrefWidth(320);

        TableColumn<Producto, String> colCatPrecio = new TableColumn<>("Precio");
        colCatPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().getPrecio())));
        colCatPrecio.setPrefWidth(120);

        TableColumn<Producto, Number> colCatStock = new TableColumn<>("Stock");
        colCatStock.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getStock()));
        colCatStock.setPrefWidth(80);

        tablaDisponibles.getColumns().addAll(colCatCod, colCatNom, colCatPrecio, colCatStock);

        TextField txtCantidadAdd = crearCampo("Cantidad");
        txtCantidadAdd.setText("1");
        txtCantidadAdd.setPrefWidth(110);

        Button btnAgregarAlCarrito = new Button("➕ Agregar al Carrito");
        btnAgregarAlCarrito.setStyle(ESTILO_BOTON_PRINCIPAL);

        HBox controlBox = new HBox(12, new Label("Cantidad:"), txtCantidadAdd, btnAgregarAlCarrito);
        controlBox.setAlignment(Pos.CENTER_LEFT);

        ObservableList<ItemCarrito> carritoItems = FXCollections.observableArrayList();
        TableView<ItemCarrito> tablaCarrito = new TableView<>(carritoItems);
        tablaCarrito.setPrefHeight(160);
        tablaCarrito.setPlaceholder(new Label("El carrito está vacío. Selecciona productos arriba."));

        TableColumn<ItemCarrito, String> colCarCod = new TableColumn<>("Código");
        colCarCod.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colCarCod.setPrefWidth(90);

        TableColumn<ItemCarrito, String> colCarNom = new TableColumn<>("Producto");
        colCarNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colCarNom.setPrefWidth(280);

        TableColumn<ItemCarrito, String> colCarPrecio = new TableColumn<>("Precio U.");
        colCarPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().getPrecioUnitario())));
        colCarPrecio.setPrefWidth(110);

        TableColumn<ItemCarrito, Number> colCarCant = new TableColumn<>("Cant.");
        colCarCant.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getCantidad()));
        colCarCant.setPrefWidth(70);

        TableColumn<ItemCarrito, String> colCarSub = new TableColumn<>("Subtotal");
        colCarSub.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().getSubtotal())));
        colCarSub.setPrefWidth(110);

        TableColumn<ItemCarrito, Void> colCarAccion = new TableColumn<>("Quitar");
        colCarAccion.setPrefWidth(70);
        colCarAccion.setCellFactory(param -> new TableCell<>() {
            private final Button btnX = new Button("❌");
            {
                btnX.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-cursor: hand; -fx-background-radius: 4px;");
                btnX.setOnAction(event -> {
                    ItemCarrito item = getTableView().getItems().get(getIndex());
                    carritoItems.remove(item);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnX);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        tablaCarrito.getColumns().addAll(colCarCod, colCarNom, colCarPrecio, colCarCant, colCarSub, colCarAccion);

        Label lblTotalGeneral = crearLabelEstilizada("TOTAL A PAGAR: $0.00", "16px", Color.web(VERDE_OSCURO), true);
        Label lblMensajeVenta = new Label("");

        btnAgregarAlCarrito.setOnAction(e -> {
            Producto seleccionado = tablaDisponibles.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarMensaje(lblMensajeVenta, "Por favor, selecciona un producto de la tabla superior con un clic.", false);
                return;
            }

            int cant;
            try {
                cant = Integer.parseInt(txtCantidadAdd.getText().trim());
            } catch (NumberFormatException ex) {
                mostrarMensaje(lblMensajeVenta, "Ingresa un número válido para la cantidad.", false);
                return;
            }

            if (cant <= 0 || !seleccionado.tieneStockDisponible(cant)) {
                mostrarMensaje(lblMensajeVenta, "Stock insuficiente o cantidad no válida.", false);
                return;
            }

            boolean encontradoEnCarrito = false;
            for (ItemCarrito item : carritoItems) {
                if (item.getProducto().getCodigo().equalsIgnoreCase(seleccionado.getCodigo())) {
                    int nuevaCant = item.getCantidad() + cant;
                    if (!seleccionado.tieneStockDisponible(nuevaCant)) {
                        mostrarMensaje(lblMensajeVenta, "Supera el stock total disponible.", false);
                        return;
                    }
                    item.setCantidad(nuevaCant);
                    encontradoEnCarrito = true;
                    break;
                }
            }

            if (!encontradoEnCarrito) {
                carritoItems.add(new ItemCarrito(seleccionado, cant));
            }

            tablaCarrito.refresh();
            txtCantidadAdd.setText("1");
            
            double sumaTotal = 0;
            for (ItemCarrito it : carritoItems) sumaTotal += it.getSubtotal();
            lblTotalGeneral.setText("TOTAL A PAGAR: $" + String.format("%,.2f", sumaTotal));
            mostrarMensaje(lblMensajeVenta, "Producto agregado al carrito correctamente.", true);
        });

        ComboBox<String> cmbMetodoPago = new ComboBox<>(FXCollections.observableArrayList("Efectivo", "Tarjeta", "Nequi"));
        cmbMetodoPago.setValue("Efectivo");
        cmbMetodoPago.setPrefWidth(160);

        Button btnFinalizar = new Button("Finalizar Compra");
        btnFinalizar.setStyle(ESTILO_BOTON_PRINCIPAL);

        Button btnCancelar = new Button("Cancelar Compra");
        btnCancelar.setStyle(ESTILO_BOTON_ROJO);

        btnFinalizar.setOnAction(e -> {
            if (carritoItems.isEmpty()) {
                mostrarMensaje(lblMensajeVenta, "El carrito está vacío.", false);
                return;
            }

            ArrayList<Producto> prodsVenta = new ArrayList<>();
            ArrayList<Integer> cantsVenta = new ArrayList<>();
            double totalVenta = 0;

            for (ItemCarrito item : carritoItems) {
                Producto p = item.getProducto();
                int cant = item.getCantidad();
                p.actualizarStock(-cant);
                prodsVenta.add(p);
                cantsVenta.add(cant);
                totalVenta += item.getSubtotal();
            }

            inventario.actualizarCambiosStock();
            tablaDisponibles.refresh();

            int idVenta = historialVentas.size() + 1;
            String metodo = cmbMetodoPago.getValue();

            Venta nuevaVenta = new Venta(idVenta, prodsVenta, cantsVenta, totalVenta, metodo);
            historialVentas.add(nuevaVenta);

            carritoItems.clear();
            lblTotalGeneral.setText("TOTAL A PAGAR: $0.00");
            mostrarMensaje(lblMensajeVenta, "¡Compra Finalizada con Éxito! Factura #" + idVenta, true);
        });

        btnCancelar.setOnAction(e -> {
            carritoItems.clear();
            lblTotalGeneral.setText("TOTAL A PAGAR: $0.00");
            mostrarMensaje(lblMensajeVenta, "Compra cancelada.", false);
        });

        HBox bottomActions = new HBox(15, new Label("Pago:"), cmbMetodoPago, btnFinalizar, btnCancelar);
        bottomActions.setAlignment(Pos.CENTER_LEFT);

        panel.getChildren().addAll(
            titleBox,
            new Label("1. Buscar y seleccionar producto:"),
            txtBuscarProd,
            tablaDisponibles,
            controlBox,
            new Label("2. Carrito de compras actual:"),
            tablaCarrito,
            lblTotalGeneral,
            bottomActions,
            lblMensajeVenta
        );

        return panel;
    }

    // --- NUEVO PANEL DE MANTENIMIENTO INTERACTIVO CON BÚSQUEDA Y CARRITO DE REPUESTOS ---
    private VBox crearPanelMantenimiento() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));

        Label titulo = crearLabelEstilizada("Registro de Mantenimiento y Taller", "22px", Color.web(VERDE_OSCURO), true);
        Label subtitulo = crearLabelEstilizada("Busca repuestos, agrégalos al servicio, añade mano de obra y cobra la reparación", "13px", Color.web("#64748b"), false);
        VBox titleBox = new VBox(3, titulo, subtitulo);

        TextField txtServicio = crearCampo("Descripción del servicio (ej. Sincronización y cambio de aceite)");
        TextField txtManoObra = crearCampo("Valor de la mano de obra ($)");

        // Buscador y tabla de productos disponibles
        TextField txtBuscarProd = crearCampo("Buscar repuesto por nombre o código para seleccionar...");
        ObservableList<Producto> listaDisponibles = FXCollections.observableArrayList(inventario.getProductos());
        FilteredList<Producto> filtradosDisponibles = new FilteredList<>(listaDisponibles, p -> true);

        txtBuscarProd.textProperty().addListener((obs, anterior, actual) -> {
            String filtro = actual.trim().toLowerCase();
            filtradosDisponibles.setPredicate(p -> filtro.isEmpty()
                    || p.getCodigo().toLowerCase().contains(filtro)
                    || p.getNombre().toLowerCase().contains(filtro));
        });

        TableView<Producto> tablaDisponibles = new TableView<>(filtradosDisponibles);
        tablaDisponibles.setPrefHeight(130);
        tablaDisponibles.setPlaceholder(new Label("No hay repuestos encontrados."));

        TableColumn<Producto, String> colCatCod = new TableColumn<>("Código");
        colCatCod.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colCatCod.setPrefWidth(80);

        TableColumn<Producto, String> colCatNom = new TableColumn<>("Nombre del Repuesto");
        colCatNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colCatNom.setPrefWidth(280);

        TableColumn<Producto, String> colCatPrecio = new TableColumn<>("Precio U.");
        colCatPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().getPrecio())));
        colCatPrecio.setPrefWidth(110);

        TableColumn<Producto, Number> colCatStock = new TableColumn<>("Stock");
        colCatStock.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getStock()));
        colCatStock.setPrefWidth(70);

        tablaDisponibles.getColumns().addAll(colCatCod, colCatNom, colCatPrecio, colCatStock);

        TextField txtCantidadAdd = crearCampo("Cantidad");
        txtCantidadAdd.setText("1");
        txtCantidadAdd.setPrefWidth(100);

        Button btnAgregarRepuesto = new Button("➕ Agregar Repuesto");
        btnAgregarRepuesto.setStyle(ESTILO_BOTON_PRINCIPAL);

        HBox controlBox = new HBox(12, new Label("Cantidad:"), txtCantidadAdd, btnAgregarRepuesto);
        controlBox.setAlignment(Pos.CENTER_LEFT);

        // Tabla de repuestos agregados al servicio (Usando ItemCarrito)
        ObservableList<ItemCarrito> repuestosItems = FXCollections.observableArrayList();
        TableView<ItemCarrito> tablaRepuestos = new TableView<>(repuestosItems);
        tablaRepuestos.setPrefHeight(130);
        tablaRepuestos.setPlaceholder(new Label("No se han agregado repuestos aún."));

        TableColumn<ItemCarrito, String> colCarCod = new TableColumn<>("Código");
        colCarCod.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colCarCod.setPrefWidth(80);

        TableColumn<ItemCarrito, String> colCarNom = new TableColumn<>("Repuesto");
        colCarNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colCarNom.setPrefWidth(260);

        TableColumn<ItemCarrito, String> colCarPrecio = new TableColumn<>("Precio U.");
        colCarPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().getPrecioUnitario())));
        colCarPrecio.setPrefWidth(110);

        TableColumn<ItemCarrito, Number> colCarCant = new TableColumn<>("Cant.");
        colCarCant.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getCantidad()));
        colCarCant.setPrefWidth(60);

        TableColumn<ItemCarrito, String> colCarSub = new TableColumn<>("Subtotal");
        colCarSub.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().getSubtotal())));
        colCarSub.setPrefWidth(100);

        TableColumn<ItemCarrito, Void> colCarAccion = new TableColumn<>("Quitar");
        colCarAccion.setPrefWidth(60);
        colCarAccion.setCellFactory(param -> new TableCell<>() {
            private final Button btnX = new Button("❌");
            {
                btnX.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-cursor: hand; -fx-background-radius: 4px;");
                btnX.setOnAction(event -> {
                    ItemCarrito item = getTableView().getItems().get(getIndex());
                    repuestosItems.remove(item);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnX);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        tablaRepuestos.getColumns().addAll(colCarCod, colCarNom, colCarPrecio, colCarCant, colCarSub, colCarAccion);

        Label lblTotalTaller = crearLabelEstilizada("TOTAL SERVICIO: $0.00", "15px", Color.web(VERDE_OSCURO), true);
        ComboBox<String> cmbPago = new ComboBox<>(FXCollections.observableArrayList("Efectivo", "Tarjeta", "Nequi"));
        cmbPago.setValue("Efectivo");
        cmbPago.setPrefWidth(160);

        Button btnCobrar = new Button("Cobrar y Registrar Servicio");
        btnCobrar.setStyle(ESTILO_BOTON_PRINCIPAL);

        Button btnCancelarTaller = new Button("Cancelar");
        btnCancelarTaller.setStyle(ESTILO_BOTON_ROJO);

        Label lblResultado = new Label("");
        lblResultado.setWrapText(true);

        // Lógica para añadir repuesto al servicio
        btnAgregarRepuesto.setOnAction(e -> {
            Producto seleccionado = tablaDisponibles.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarMensaje(lblResultado, "Por favor, selecciona un repuesto de la tabla superior con un clic.", false);
                return;
            }

            int cant;
            try {
                cant = Integer.parseInt(txtCantidadAdd.getText().trim());
            } catch (NumberFormatException ex) {
                mostrarMensaje(lblResultado, "Ingresa un número válido para la cantidad.", false);
                return;
            }

            if (cant <= 0 || !seleccionado.tieneStockDisponible(cant)) {
                mostrarMensaje(lblResultado, "Stock insuficiente o cantidad no válida para " + seleccionado.getNombre(), false);
                return;
            }

            boolean encontrado = false;
            for (ItemCarrito item : repuestosItems) {
                if (item.getProducto().getCodigo().equalsIgnoreCase(seleccionado.getCodigo())) {
                    int nuevaCant = item.getCantidad() + cant;
                    if (!seleccionado.tieneStockDisponible(nuevaCant)) {
                        mostrarMensaje(lblResultado, "Supera el stock total disponible.", false);
                        return;
                    }
                    item.setCantidad(nuevaCant);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                repuestosItems.add(new ItemCarrito(seleccionado, cant));
            }

            tablaRepuestos.refresh();
            txtCantidadAdd.setText("1");

            // Calcular total preliminar (repuestos + mano de obra)
            double sumaRepuestos = 0;
            for (ItemCarrito it : repuestosItems) sumaRepuestos += it.getSubtotal();
            double manoObraVal = 0;
            try {
                manoObraVal = Double.parseDouble(txtManoObra.getText().trim().replace(',', '.'));
            } catch (Exception ignored) {}
            
            lblTotalTaller.setText("TOTAL SERVICIO: $" + String.format("%,.2f", sumaRepuestos + manoObraVal));
            mostrarMensaje(lblResultado, "Repuesto agregado correctamente al servicio.", true);
        });

        // Actualizar total al escribir mano de obra
        txtManoObra.textProperty().addListener((obs, viejo, nuevo) -> {
            double sumaRepuestos = 0;
            for (ItemCarrito it : repuestosItems) sumaRepuestos += it.getSubtotal();
            double manoObraVal = 0;
            try {
                manoObraVal = Double.parseDouble(nuevo.trim().replace(',', '.'));
            } catch (Exception ignored) {}
            lblTotalTaller.setText("TOTAL SERVICIO: $" + String.format("%,.2f", sumaRepuestos + manoObraVal));
        });

        // Lógica para cobrar y registrar
        btnCobrar.setOnAction(e -> {
            String desc = txtServicio.getText().trim();
            if (desc.isEmpty()) {
                mostrarMensaje(lblResultado, "Ingresa una descripción del servicio.", false);
                return;
            }

            double valorManoObra = 0;
            try {
                String textoMano = txtManoObra.getText().trim().replace(',', '.');
                if (!textoMano.isEmpty()) {
                    valorManoObra = Double.parseDouble(textoMano);
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje(lblResultado, "Valor de mano de obra inválido.", false);
                return;
            }

            ArrayList<Producto> prodsUsados = new ArrayList<>();
            ArrayList<Integer> cantsUsadas = new ArrayList<>();
            double totalRepuestos = 0;

            for (ItemCarrito item : repuestosItems) {
                Producto p = item.getProducto();
                int cant = item.getCantidad();
                p.actualizarStock(-cant);
                prodsUsados.add(p);
                cantsUsadas.add(cant);
                totalRepuestos += item.getSubtotal();
            }

            if (!repuestosItems.isEmpty()) {
                inventario.actualizarCambiosStock();
                tablaDisponibles.refresh();
            }

            if (valorManoObra > 0) {
                Categoria catServicio = new Categoria(99, "Servicios", "Taller");
                Producto pServicio = new Producto(999, "SERV", "Mano de Obra (" + desc + ")", desc, valorManoObra, 1, "sin_foto", catServicio);
                prodsUsados.add(pServicio);
                cantsUsadas.add(1);
            }

            double totalGeneral = totalRepuestos + valorManoObra;
            int idVenta = historialVentas.size() + 1;
            String metodo = cmbPago.getValue();

            Venta nuevaVenta = new Venta(idVenta, prodsUsados, cantsUsadas, totalGeneral, metodo);
            historialVentas.add(nuevaVenta);

            txtServicio.clear();
            txtManoObra.clear();
            repuestosItems.clear();
            txtCantidadAdd.setText("1");
            lblTotalTaller.setText("TOTAL SERVICIO: $0.00");

            mostrarMensaje(lblResultado, "¡Servicio cobrado con éxito! Factura #" + idVenta + " - Total: $" + String.format("%,.2f", totalGeneral), true);
        });

        btnCancelarTaller.setOnAction(e -> {
            txtServicio.clear();
            txtManoObra.clear();
            repuestosItems.clear();
            lblTotalTaller.setText("TOTAL SERVICIO: $0.00");
            mostrarMensaje(lblResultado, "Servicio cancelado.", false);
        });

        HBox bottomActions = new HBox(12, new Label("Pago:"), cmbPago, btnCobrar, btnCancelarTaller);
        bottomActions.setAlignment(Pos.CENTER_LEFT);

        panel.getChildren().addAll(
            titleBox,
            txtServicio,
            txtManoObra,
            new Label("1. Buscar y seleccionar repuesto:"),
            txtBuscarProd,
            tablaDisponibles,
            controlBox,
            new Label("2. Repuestos asignados al taller:"),
            tablaRepuestos,
            lblTotalTaller,
            bottomActions,
            lblResultado
        );

        return panel;
    }

    private VBox crearPanelProductos(String tituloTexto, String subtituloTexto, boolean esAdmin) {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(25));

        Label titulo = crearLabelEstilizada(tituloTexto, "22px", Color.web(VERDE_OSCURO), true);
        Label subtitulo = crearLabelEstilizada(subtituloTexto, "13px", Color.web("#64748b"), false);
        VBox titleBox = new VBox(3, titulo, subtitulo);

        TextField txtBuscar = crearCampo("Buscar por código o nombre...");
        txtBuscar.setMaxWidth(320);

        ObservableList<Producto> datos = FXCollections.observableArrayList(inventario.getProductos());
        FilteredList<Producto> filtrados = new FilteredList<>(datos, p -> true);

        txtBuscar.textProperty().addListener((obs, anterior, actual) -> {
            String filtro = actual.trim().toLowerCase();
            filtrados.setPredicate(p -> filtro.isEmpty()
                    || p.getCodigo().toLowerCase().contains(filtro)
                    || p.getNombre().toLowerCase().contains(filtro));
        });

        TableView<Producto> tabla = new TableView<>(filtrados);
        tabla.setPlaceholder(new Label("No hay productos registrados."));

        TableColumn<Producto, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getIdProducto()));
        colId.setPrefWidth(60);

        TableColumn<Producto, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colCodigo.setPrefWidth(90);

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colNombre.setPrefWidth(300);

        TableColumn<Producto, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.2f", c.getValue().getPrecio())));
        colPrecio.setPrefWidth(130);

        TableColumn<Producto, Number> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getStock()));
        colStock.setPrefWidth(80);

        tabla.getColumns().addAll(colId, colCodigo, colNombre, colPrecio, colStock);

        tabla.setRowFactory(tv -> new TableRow<Producto>() {
            @Override
            protected void updateItem(Producto p, boolean empty) {
                super.updateItem(p, empty);
                if (!empty && p != null && p.getStock() <= STOCK_BAJO) {
                    setStyle("-fx-background-color: #fee2e2;");
                } else {
                    setStyle("");
                }
            }
        });
        VBox.setVgrow(tabla, Priority.ALWAYS);

        panel.getChildren().addAll(titleBox, txtBuscar, tabla);

        if (esAdmin) {
            VBox formulariosBox = new VBox(15);
            formulariosBox.getChildren().addAll(
                crearFormularioProducto(datos, tabla),
                crearFormularioModificarEliminar(datos, tabla)
            );
            panel.getChildren().add(formulariosBox);
        }

        return panel;
    }

    private VBox crearFormularioProducto(ObservableList<Producto> datos, TableView<Producto> tabla) {
        VBox formCard = new VBox(10);
        formCard.setPadding(new Insets(15));
        formCard.setStyle(ESTILO_TARJETA);

        Label lblForm = crearLabelEstilizada("Registrar nuevo producto", "14px", Color.web(VERDE_OSCURO), true);

        TextField txtCodigo = crearCampo("Código (ej. 101)");
        txtCodigo.setPrefWidth(150);
        TextField txtNombre = crearCampo("Nombre del producto");
        HBox.setHgrow(txtNombre, Priority.ALWAYS);
        TextField txtPrecio = crearCampo("Precio (ej. 25000)");
        txtPrecio.setPrefWidth(130);
        TextField txtStock = crearCampo("Stock inicial");
        txtStock.setPrefWidth(100);

        Button btnAgregar = new Button("Agregar");
        btnAgregar.setStyle(ESTILO_BOTON_PRINCIPAL);

        Label lblMensaje = new Label("");

        btnAgregar.setOnAction(e -> {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String textoPrecio = txtPrecio.getText().trim().replace(',', '.');
            String textoStock = txtStock.getText().trim();

            if (codigo.isEmpty() || inventario.buscarPorCodigo(codigo) != null) {
                mostrarMensaje(lblMensaje, "Código vacío o ya existente.", false);
                return;
            }
            if (nombre.isEmpty()) {
                mostrarMensaje(lblMensaje, "El nombre no puede estar vacío.", false);
                return;
            }

            double precio = Double.parseDouble(textoPrecio);
            int stock = Integer.parseInt(textoStock);

            Producto nuevo = new Producto(siguienteId(), codigo, nombre, "", precio, stock, "sin_foto", categoriaBase);
            inventario.agregarProducto(nuevo);
            datos.setAll(inventario.getProductos());

            txtCodigo.clear();
            txtNombre.clear();
            txtPrecio.clear();
            txtStock.clear();
            mostrarMensaje(lblMensaje, "Producto agregado correctamente.", true);
        });

        HBox fila = new HBox(10, txtCodigo, txtNombre, txtPrecio, txtStock, btnAgregar);
        fila.setAlignment(Pos.CENTER_LEFT);
        formCard.getChildren().addAll(lblForm, fila, lblMensaje);
        return formCard;
    }

    private VBox crearFormularioModificarEliminar(ObservableList<Producto> datos, TableView<Producto> tabla) {
        VBox formCard = new VBox(10);
        formCard.setPadding(new Insets(15));
        formCard.setStyle(ESTILO_TARJETA);

        Label lblForm = crearLabelEstilizada("Modificar o Eliminar producto seleccionado", "14px", Color.web(VERDE_OSCURO), true);

        TextField txtModNombre = crearCampo("Nuevo Nombre");
        HBox.setHgrow(txtModNombre, Priority.ALWAYS);
        TextField txtModPrecio = crearCampo("Nuevo Precio");
        txtModPrecio.setPrefWidth(130);
        TextField txtModStock = crearCampo("Nuevo Stock");
        txtModStock.setPrefWidth(130);

        Button btnModificar = new Button("Modificar");
        btnModificar.setStyle(ESTILO_BOTON_PRINCIPAL);

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle(ESTILO_BOTON_ROJO);

        Label lblMensajeMod = new Label("");

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, viejo, seleccionado) -> {
            if (seleccionado != null) {
                txtModNombre.setText(seleccionado.getNombre());
                txtModPrecio.setText(String.valueOf(seleccionado.getPrecio()));
                txtModStock.setText(String.valueOf(seleccionado.getStock()));
            }
        });

        btnModificar.setOnAction(e -> {
            Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarMensaje(lblMensajeMod, "Selecciona un producto de la tabla.", false);
                return;
            }

            String nuevoNombre = txtModNombre.getText().trim();
            double nuevoPrecio = Double.parseDouble(txtModPrecio.getText().trim().replace(',', '.'));
            int nuevoStock = Integer.parseInt(txtModStock.getText().trim());

            inventario.modificarProductoPorCodigo(seleccionado.getCodigo(), nuevoNombre, nuevoPrecio);
            int diferenciaStock = nuevoStock - seleccionado.getStock();
            if (diferenciaStock != 0) {
                seleccionado.actualizarStock(diferenciaStock);
                inventario.actualizarCambiosStock();
            }

            datos.setAll(inventario.getProductos());
            tabla.refresh();
            mostrarMensaje(lblMensajeMod, "Producto modificado con éxito.", true);
        });

        btnEliminar.setOnAction(e -> {
            Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarMensaje(lblMensajeMod, "Selecciona un producto para eliminar.", false);
                return;
            }

            inventario.eliminarProductoPorCodigo(seleccionado.getCodigo());
            datos.setAll(inventario.getProductos());
            txtModNombre.clear();
            txtModPrecio.clear();
            txtModStock.clear();
            mostrarMensaje(lblMensajeMod, "Producto eliminado correctamente.", true);
        });

        HBox filaAcciones = new HBox(10, txtModNombre, txtModPrecio, txtModStock, btnModificar, btnEliminar);
        filaAcciones.setAlignment(Pos.CENTER_LEFT);
        formCard.getChildren().addAll(lblForm, filaAcciones, lblMensajeMod);
        return formCard;
    }

    private VBox crearPanelListaUsuarios() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(25));

        Label titulo = crearLabelEstilizada("Gestión de Usuarios", "22px", Color.web(VERDE_OSCURO), true);
        Label subtitulo = crearLabelEstilizada("Selecciona un usuario de la tabla para modificar sus datos o eliminarlo", "13px", Color.web("#64748b"), false);
        VBox titleBox = new VBox(3, titulo, subtitulo);

        ObservableList<Usuario> listaUsrs = FXCollections.observableArrayList(gestionUsuarios.getUsuarios());

        TableView<Usuario> tablaUsrs = new TableView<>(listaUsrs);
        tablaUsrs.setPlaceholder(new Label("No hay usuarios registrados."));

        TableColumn<Usuario, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getIdUsuario()));
        colId.setPrefWidth(60);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colNombre.setPrefWidth(160);

        TableColumn<Usuario, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        colCorreo.setPrefWidth(210);

        TableColumn<Usuario, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRol()));
        colRol.setPrefWidth(120);

        TableColumn<Usuario, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colTelefono.setPrefWidth(130);

        tablaUsrs.getColumns().addAll(colId, colNombre, colCorreo, colRol, colTelefono);
        VBox.setVgrow(tablaUsrs, Priority.ALWAYS);

        VBox formCard = new VBox(10);
        formCard.setPadding(new Insets(15));
        formCard.setStyle(ESTILO_TARJETA);

        Label lblFormUsr = crearLabelEstilizada("Modificar o Eliminar usuario seleccionado", "14px", Color.web(VERDE_OSCURO), true);

        TextField txtUsrNombre = crearCampo("Nuevo Nombre");
        HBox.setHgrow(txtUsrNombre, Priority.ALWAYS);
        TextField txtUsrCorreo = crearCampo("Nuevo Correo");
        TextField txtUsrPass = crearCampo("Nueva Contraseña");
        TextField txtUsrTelefono = crearCampo("Nuevo Teléfono");

        Button btnModUsr = new Button("Modificar Usuario");
        btnModUsr.setStyle(ESTILO_BOTON_PRINCIPAL);

        Button btnDelUsr = new Button("Eliminar Usuario");
        btnDelUsr.setStyle(ESTILO_BOTON_ROJO);

        Label lblMsgUsr = new Label("");

        tablaUsrs.getSelectionModel().selectedItemProperty().addListener((obs, viejo, seleccionado) -> {
            if (seleccionado != null) {
                txtUsrNombre.setText(seleccionado.getNombre());
                txtUsrCorreo.setText(seleccionado.getCorreo());
                txtUsrPass.setText("24");
                txtUsrTelefono.setText(seleccionado.getTelefono());
            }
        });

        btnModUsr.setOnAction(e -> {
            Usuario seleccionado = tablaUsrs.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarMensaje(lblMsgUsr, "Selecciona un usuario de la tabla.", false);
                return;
            }

            String nuevoNombre = txtUsrNombre.getText().trim();
            String nuevoCorreo = txtUsrCorreo.getText().trim();
            String nuevoTel = txtUsrTelefono.getText().trim();

            if (nuevoNombre.isEmpty() || nuevoCorreo.isEmpty()) {
                mostrarMensaje(lblMsgUsr, "Nombre y correo no pueden estar vacíos.", false);
                return;
            }

            gestionUsuarios.modificarUsuario(seleccionado.getIdUsuario(), nuevoNombre, nuevoCorreo, nuevoTel, seleccionado.getRol());
            listaUsrs.setAll(gestionUsuarios.getUsuarios());
            tablaUsrs.refresh();
            mostrarMensaje(lblMsgUsr, "Usuario modificado correctamente.", true);
        });

        btnDelUsr.setOnAction(e -> {
            Usuario seleccionado = tablaUsrs.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarMensaje(lblMsgUsr, "Selecciona un usuario para eliminar.", false);
                return;
            }

            if (seleccionado.getNombre().equalsIgnoreCase("jhoiner")) {
                mostrarMensaje(lblMsgUsr, "No se puede eliminar al Administrador principal.", false);
                return;
            }

            gestionUsuarios.eliminarUsuarioPorCorreo(seleccionado.getCorreo());
            listaUsrs.setAll(gestionUsuarios.getUsuarios());
            txtUsrNombre.clear();
            txtUsrCorreo.clear();
            txtUsrPass.clear();
            txtUsrTelefono.clear();
            mostrarMensaje(lblMsgUsr, "Usuario eliminado correctamente.", true);
        });

        HBox filaUsrAcciones = new HBox(10, txtUsrNombre, txtUsrCorreo, txtUsrPass, txtUsrTelefono, btnModUsr, btnDelUsr);
        filaUsrAcciones.setAlignment(Pos.CENTER_LEFT);
        formCard.getChildren().addAll(lblFormUsr, filaUsrAcciones, lblMsgUsr);

        panel.getChildren().addAll(titleBox, tablaUsrs, formCard);
        return panel;
    }

    private VBox crearPanelUsuarios() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(25));

        Label titulo = crearLabelEstilizada("Registrar Usuario", "22px", Color.web(VERDE_OSCURO), true);
        Label subtitulo = crearLabelEstilizada("Registra clientes, mecánicos, empleados y administradores", "13px", Color.web("#64748b"), false);
        VBox titleBox = new VBox(3, titulo, subtitulo);

        VBox formCard = new VBox(14);
        formCard.setPadding(new Insets(20));
        formCard.setMaxWidth(700);
        formCard.setStyle(ESTILO_TARJETA);

        Label lblForm = crearLabelEstilizada("Nuevo usuario", "15px", Color.web(VERDE_OSCURO), true);

        ComboBox<String> cmbTipo = new ComboBox<>(FXCollections.observableArrayList(
                "Cliente", "Mecánico", "Empleado", "Administrador"));
        cmbTipo.setValue("Cliente");
        cmbTipo.setMaxWidth(Double.MAX_VALUE);

        TextField txtId = crearCampo("ID de usuario (número)");
        TextField txtNombre = crearCampo("Nombre de usuario");
        TextField txtCorreo = crearCampo("Correo");
        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Contraseña");
        txtPass.setStyle(ESTILO_CAMPO);
        TextField txtTelefono = crearCampo("Teléfono");
        TextField txtExtra = crearCampo("Dirección / Especialidad");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(c1, c2);
        grid.add(cmbTipo, 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(txtNombre, 0, 1);
        grid.add(txtCorreo, 1, 1);
        grid.add(txtPass, 0, 2);
        grid.add(txtTelefono, 1, 2);
        grid.add(txtExtra, 0, 3, 2, 1);

        Button btnRegistrar = new Button("Registrar usuario");
        btnRegistrar.setStyle(ESTILO_BOTON_PRINCIPAL);

        Label lblMensaje = new Label("");

        btnRegistrar.setOnAction(e -> {
            int tipo = cmbTipo.getSelectionModel().getSelectedIndex();
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String pass = txtPass.getText();
            String telefono = txtTelefono.getText().trim();
            String extra = txtExtra.getText().trim();

            int id = Integer.parseInt(txtId.getText().trim());

            Usuario nuevo;
            if (tipo == 0) {
                nuevo = new Cliente(id, nombre, correo, pass, telefono, extra);
            } else if (tipo == 1) {
                nuevo = new Mecanico(id, nombre, correo, pass, telefono, extra.isEmpty() ? "Motor" : extra);
            } else if (tipo == 2) {
                nuevo = new Empleado(id, nombre, correo, pass, telefono, extra);
            } else {
                nuevo = new Administrador(id, nombre, correo, pass, telefono);
            }

            gestionUsuarios.agregarUsuario(nuevo);
            mostrarMensaje(lblMensaje, cmbTipo.getValue() + " registrado con éxito.", true);
        });

        formCard.getChildren().addAll(lblForm, grid, btnRegistrar, lblMensaje);
        panel.getChildren().addAll(titleBox, formCard);
        return panel;
    }

    private int siguienteId() {
        int max = 0;
        for (Producto p : inventario.getProductos()) {
            if (p.getIdProducto() > max) {
                max = p.getIdProducto();
            }
        }
        return max + 1;
    }

    private void mostrarMensaje(Label lbl, String texto, boolean exito) {
        lbl.setText(texto);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + (exito ? "#16a34a" : "#dc2626") + ";");
    }

    private TextField crearCampo(String prompt) {
        TextField campo = new TextField();
        campo.setPromptText(prompt);
        campo.setStyle(ESTILO_CAMPO);
        return campo;
    }

    private Label crearLabelEstilizada(String texto, String fontSize, Color color, boolean negrita) {
        Label lbl = new Label(texto);
        String weight = negrita ? "bold" : "normal";
        lbl.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: " + weight + ";");
        lbl.setTextFill(color);
        return lbl;
    }

    private Button crearBotonMenu(String texto) {
        final String estiloNormal = "-fx-background-color: transparent; -fx-text-fill: " + VERDE_CLARO + "; -fx-font-size: 13px; -fx-cursor: hand;";
        final String estiloHover = "-fx-background-color: " + VERDE_HOVER + "; -fx-text-fill: white; -fx-font-size: 13px; -fx-cursor: hand;";

        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);
        btn.setPadding(new Insets(10, 15, 10, 15));
        btn.setStyle(estiloNormal);

        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloNormal));
        return btn;
    }

    private VBox crearTarjetaMetrica(String titulo, String valor, String descripcion) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefWidth(240);
        card.setStyle(ESTILO_TARJETA);

        Label lblTitulo = crearLabelEstilizada(titulo, "12px", Color.web("#64748b"), false);
        Label lblValor = crearLabelEstilizada(valor, "20px", Color.web(VERDE_OSCURO), true);
        Label lblDesc = crearLabelEstilizada(descripcion, "11px", Color.web("#94a3b8"), false);

        card.getChildren().addAll(lblTitulo, lblValor, lblDesc);
        return card;
    }
}