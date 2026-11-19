package Main;

import controller.Venta;
import gestor.GestionUsuarios;
import gestor.Inventario;
import modelo.Mecanico;
import modelo.Empleado;
import modelo.Cliente;
import modelo.Administrador;
import modelo.Usuario;
import modelo.Producto;
import modelo.Categoria;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        GestionUsuarios gestionUsuarios = new GestionUsuarios();
        Inventario inventario = new Inventario();
        ArrayList<Venta> historialVentas = new ArrayList<>();

        // Configuración inicial de cuentas (Contraseña por defecto: 24)
        Administrador adminMaster = new Administrador(1, "jhoiner", "jhoiner@servimotos.com", "24", "3001234567");
        gestionUsuarios.agregarUsuarioSinMensaje(adminMaster);

        Administrador sheyla = new Administrador(2, "Sheyla", "sheyla@servimotos.com", "24", "3001112233");
        Administrador jose = new Administrador(3, "Jose", "jose@servimotos.com", "24", "3002223344");
        Administrador darlys = new Administrador(4, "Darlys", "darlys@servimotos.com", "24", "3003334455");
        
        gestionUsuarios.agregarUsuarioSinMensaje(sheyla);
        gestionUsuarios.agregarUsuarioSinMensaje(jose);
        gestionUsuarios.agregarUsuarioSinMensaje(darlys);

        Mecanico mecanicoJesus = new Mecanico(5, "Jesus", "jesus@servimotos.com", "24", "3009998877", "motor");
        gestionUsuarios.agregarUsuarioSinMensaje(mecanicoJesus);

        Categoria catLubricantes = new Categoria(1, "Lubricantes y Quimicos", "Aceites, fluidos, grasas y limpiadores");
        Categoria catRepuestos = new Categoria(2, "Repuestos y Motor", "Filtros, bujias, empaques, carburacion y guayas");
        Categoria catFrenosArrastre = new Categoria(3, "Frenos y Arrastre", "Pastillas, bandas, discos, kits y cadenas");
        Categoria catElectricoLlantas = new Categoria(4, "Electrico y Llantas", "Baterias, luces LED, encendido, llantas y camaras");

        if (inventario.getProductos().isEmpty()) {
            // Lubricantes y Químicos
            inventario.agregarProducto(new Producto(1, "101", "Aceite Motul 5100 10W40", "Lubricante semisintetico 4T", 58000, 12, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(2, "102", "Aceite Motul 7100 10W40", "Lubricante 100% sintetico 4T", 74000, 10, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(3, "103", "Aceite Motul 3000 20W50", "Lubricante mineral 4T", 38000, 24, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(4, "104", "Aceite Mobil Super Moto 20W50", "Lubricante mineral 4T", 35000, 20, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(5, "105", "Aceite Mobil Super 4T 10W30", "Lubricante 4T", 42000, 15, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(6, "106", "Aceite Castrol Power 1 10W40", "Lubricante semisintetico", 55000, 12, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(7, "007", "Aceite Shell Advance AX7 10W40", "Lubricante semisintetico", 46000, 14, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(8, "008", "Aceite Motul 10W30", "Lubricante sintetico", 69000, 8, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(9, "009", "Aceite Yamaha Yamalube 20W50", "Lubricante original Yamaha", 39000, 18, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(10, "010", "Aceite Repsol Moto Sport 10W40", "Lubricante semisintetico", 48000, 10, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(27, "027", "Liquido de Frenos DOT 4 250ml", "Fluido de frenos alto rendimiento", 18000, 16, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(28, "028", "Liquido de Frenos DOT 3 250ml", "Fluido de frenos estandar", 14000, 15, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(72, "072", "Aceite Hidraulico Barras 10W 500ml", "Aceite para suspension telescopica", 26000, 14, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(86, "086", "Limpiador de Carburador Aerosol", "Spray limpiador de cuerpos", 19000, 20, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(87, "087", "Lubricante de Cadena Motul C2 400ml", "Grasa blanca adherente", 56000, 12, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(88, "088", "Limpiador de Cadena Motul C1 400ml", "Desengrasante de cadena", 52000, 10, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(89, "089", "Lubricante de Cadena Simoniz 300ml", "Lubricante spray", 22000, 24, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(90, "090", "Aflojatodo Penetrante WD40 226g", "Multiusos penetrante", 25000, 18, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(91, "091", "Silicona Gris Alta Temperatura Loctite", "Formador de empaques", 24000, 15, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(92, "092", "Trabarroscas Azul Fijador Medio 10ml", "Fijador de tornillos", 28000, 10, "sin_foto", catLubricantes));
            inventario.agregarProducto(new Producto(93, "093", "Grasa de Litio Multiusos Pote 250g", "Grasa automotriz", 16000, 16, "sin_foto", catLubricantes));

            // Repuestos y Motor
            inventario.agregarProducto(new Producto(11, "011", "Filtro de Aceite Generico 150cc", "Filtro estandar", 12000, 30, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(12, "012", "Filtro de Aceite K&N Alto Flujo", "Filtro alto rendimiento", 65000, 5, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(13, "013", "Filtro de Aceite Honda CB190R", "Repuesto Honda", 18000, 15, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(14, "014", "Filtro de Aceite Yamaha FZ16", "Repuesto Yamaha", 16000, 20, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(15, "015", "Filtro de Aceite Bajaj Pulsar NS200", "Repuesto Bajaj", 15000, 25, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(16, "016", "Filtro de Aire Pulsar NS200", "Elemento filtrante aire", 24000, 15, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(17, "017", "Filtro de Aire Yamaha FZ 2.0", "Elemento filtrante aire", 28000, 12, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(18, "018", "Filtro de Aire Honda XR150L", "Elemento filtrante aire", 26000, 14, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(19, "019", "Filtro de Aire AKT NKD 125", "Elemento filtrante aire", 15000, 25, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(20, "020", "Filtro de Aire Suzuki Gixxer 150", "Elemento filtrante aire", 27000, 10, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(41, "041", "Bujia NGK Iridium CR8EIX", "Bujia punta de iridio", 52000, 12, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(42, "042", "Bujia NGK Estandar CR8E", "Bujia convencional", 16000, 35, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(43, "043", "Bujia NGK D8EA", "Bujia moto 125/150cc", 14000, 40, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(44, "044", "Bujia Bosch UR2CC", "Bujia estandar", 15000, 25, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(45, "045", "Capuchon de Bujia NGK Siliconado", "Supresor de ruido", 18000, 20, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(59, "059", "Guaya de Embrague Clutch Universal", "Cable de embrague", 15000, 25, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(60, "060", "Guaya de Acelerador Completa", "Cable de acelerador", 16000, 25, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(61, "061", "Guaya de Velocimetro 125cc", "Cable velocimetro", 14000, 18, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(62, "062", "Manigueta de Clutch Aluminio", "Leva izquierda", 18000, 20, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(63, "063", "Manigueta de Freno Delantero", "Leva derecha", 18000, 20, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(64, "064", "Juego de Manilares Caucho Antideslizante", "Grips manubrio", 16000, 22, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(65, "065", "Juego de Espejos Retrovisores 10mm", "Par de espejos", 28000, 15, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(66, "066", "Discos de Clutch Juego x5 Unidades", "Kit discos friccion", 36000, 14, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(67, "067", "Discos de Clutch Pulsar NS200 Juego", "Kit discos Bajaj", 58000, 10, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(68, "068", "Empaque Tapa Clutch 150cc", "Junta tapa embrague", 12000, 20, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(69, "069", "Empaque Culata Cilindro 150cc", "Junta superior motor", 15000, 18, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(70, "070", "Kit de Empaques Completo Motor 125cc", "Juego total juntas", 28000, 12, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(71, "071", "Retenedores de Telescopica Par 31x43x10.5", "Sellos suspension delantera", 24000, 16, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(73, "073", "Cunas de Direccion Rodamientos Conicos", "Kit direccion", 38000, 12, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(74, "074", "Rodamiento de Rueda 6202 2RS", "Balinera sellada", 12000, 30, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(75, "075", "Rodamiento de Rueda 6301 2RS", "Balinera sellada", 12000, 30, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(76, "076", "Rodamiento de Rueda 6004 2RS", "Balinera porta sprocket", 14000, 25, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(77, "077", "Cauchos de Porta Sprocket Juego x4", "Amortiguadores masa trasera", 18000, 20, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(78, "078", "Tensor de Cadenilla de Distribucion", "Tensor automatico", 34000, 10, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(79, "079", "Cadenilla de Distribucion 94 Eslabones", "Cadena tiempo motor", 32000, 12, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(80, "080", "Carburador PZ27 Completo", "Carburador 125/150cc", 88000, 6, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(81, "081", "Carburador PZ30 con Bomba de Pique", "Carburador racing 150/200cc", 115000, 5, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(82, "082", "Kit de Reparacion Carburador PZ27", "Agujas, empaques y chicleres", 18000, 15, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(83, "083", "Filtro de Gasolina Magnetico Transparente", "Filtro combustible", 6000, 40, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(84, "084", "Manguera de Gasolina Siliconada 1 Metro", "Ducto combustible", 8000, 30, "sin_foto", catRepuestos));
            inventario.agregarProducto(new Producto(85, "085", "Llave de Paso de Gasolina Tanque", "Grifo combustible", 22000, 15, "sin_foto", catRepuestos));

            // Frenos y Arrastre
            inventario.agregarProducto(new Producto(21, "021", "Pastillas Freno Delanteras Sintetizadas", "Pastillas alto rendimiento", 45000, 15, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(22, "022", "Pastillas Freno Delanteras Semimetalicas", "Pastillas uso diario", 22000, 30, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(23, "023", "Pastillas Freno Traseras Pulsar NS200", "Pastillas traseras", 25000, 20, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(24, "024", "Pastillas Freno Traseras Yamaha FZ", "Pastillas traseras", 24000, 18, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(25, "025", "Bandas de Freno Trasero Tambores 125cc", "Zapatas freno campana", 20000, 25, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(26, "026", "Bandas de Freno Trasero AKT NKD 125", "Zapatas freno NKD", 19000, 22, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(29, "029", "Disco de Freno Delantero 260mm", "Rotor delantero", 95000, 6, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(30, "030", "Disco de Freno Trasero 220mm", "Rotor trasero", 75000, 5, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(31, "031", "Kit de Arrastre Cassarella 428H Reforzado", "Piñon, catalina y cadena", 85000, 12, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(32, "032", "Kit de Arrastre DID Dorado 428H", "Kit transmision pesado", 145000, 8, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(33, "033", "Kit de Arrastre Cho Ho Pulsar NS200", "Kit transmision Pulsar", 115000, 10, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(34, "034", "Kit de Arrastre AKT NKD 125", "Kit transmision NKD", 68000, 15, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(35, "035", "Kit de Arrastre Yamaha FZ16", "Kit transmision FZ", 98000, 10, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(36, "036", "Cadena Reforzada 428H 132 Eslabones", "Cadena transmision", 48000, 14, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(37, "037", "Cadena Oring 520 120 Eslabones", "Cadena sellada alto cilindraje", 135000, 6, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(38, "038", "Pinon de Salida 14 Dientes", "Sprocket delantero", 16000, 20, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(39, "039", "Pinon de Salida 15 Dientes", "Sprocket delantero", 16000, 20, "sin_foto", catFrenosArrastre));
            inventario.agregarProducto(new Producto(40, "040", "Catalina Trasera 42 Dientes Acero", "Sprocket trasero", 38000, 10, "sin_foto", catFrenosArrastre));

            // Eléctrico y Llantas
            inventario.agregarProducto(new Producto(46, "046", "Bateria Gel 12V 5Ah YTX5L-BS", "Bateria libre mantenimiento", 95000, 10, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(47, "047", "Bateria Gel 12V 7Ah YTX7L-BS", "Bateria libre mantenimiento", 115000, 8, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(48, "048", "Bateria Gel 12V 9Ah YTX9-BS", "Bateria alto amperaje", 135000, 6, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(49, "049", "Regulador Rectificador de Voltaje 12V", "Estabilizador corriente", 45000, 10, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(50, "050", "Bobina de Encendido Alta Tension", "Bobina de alta", 38000, 12, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(51, "051", "CDI Racing Digital Universal", "Modulo encendido sin corte", 55000, 8, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(52, "052", "Relay de Arranque Marranito 12V", "Solenoide encendido", 22000, 18, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(53, "053", "Bombillo LED H4 Luz Blanca 6000K", "Farola LED alta potencia", 35000, 20, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(54, "054", "Bombillo Halogeno H4 12V 35W", "Bombillo original", 12000, 30, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(55, "055", "Bombillo Stop Trasero LED Doble Contacto", "Luz freno LED", 10000, 35, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(56, "056", "Juego de Direccionales LED Universales", "Par direccionales", 28000, 15, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(57, "057", "Flasher Electronico Direccionales LED", "Rele destellador ajustable", 14000, 20, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(58, "058", "Pito Claxon 12V Disco Negro", "Bocina moto", 20000, 16, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(94, "094", "Llanta Delantera 2.75-18 Sellomatic", "Cubierta sin camara", 135000, 6, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(95, "095", "Llanta Trasera 100/90-18 Sellomatic", "Cubierta sin camara", 165000, 6, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(96, "096", "Llanta Trasera 130/70-17 Pistera", "Cubierta deportiva", 210000, 4, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(97, "097", "Neumatico Camara 2.75/3.00-18", "Tubo interior rin 18", 22000, 20, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(98, "098", "Neumatico Camara 90/90-17", "Tubo interior rin 17", 24000, 18, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(99, "099", "Valvula Recta Sellomatic para Rin", "Valvula tubeless", 6000, 40, "sin_foto", catElectricoLlantas));
            inventario.agregarProducto(new Producto(100, "100", "Kit Despinche Rapido Mechas y Cemento", "Reparacion emergencia", 15000, 25, "sin_foto", catElectricoLlantas));
        }

        Usuario usuarioLogueado = null;

        System.out.println("=========================================");
        System.out.println("   BIENVENIDO AL SISTEMA STOCKMOTOS");
        System.out.println("=========================================");

        while (usuarioLogueado == null) {
            System.out.print("Por favor, ingresa tu correo o usuario: ");
            String inputCredencial = entrada.nextLine().trim();
            System.out.print("Ingresa tu contraseña: ");
            String inputPassword = entrada.nextLine().trim();

            usuarioLogueado = gestionUsuarios.autenticar(inputCredencial, inputPassword);

            if (usuarioLogueado == null) {
                System.out.println("\n[Atención] Los datos ingresados no coinciden. Inténtalo de nuevo.\n");
            } else {
                System.out.println("\n¡Hola, " + usuarioLogueado.getNombre() + "! Qué bueno verte por aquí. Rol asignado: " + usuarioLogueado.getRol() + "\n");
            }
        }

        int opcion = 0;

        if (usuarioLogueado.getRol().equalsIgnoreCase("Administrador")) {
            do {
                System.out.println("\n=== MENÚ ADMINISTRADOR (" + usuarioLogueado.getNombre() + ") ===");
                System.out.println("1. Registrar un nuevo usuario");
                System.out.println("2. Ver lista general de usuarios");
                System.out.println("3. Agregar producto al inventario");
                System.out.println("4. Ver inventario actual");
                System.out.println("5. Buscar producto (código o nombre)");
                System.out.println("6. Modificar producto (nombre o precio)");
                System.out.println("7. Eliminar producto del inventario");
                System.out.println("8. Realizar venta / facturación");
                System.out.println("9. Ver reporte de productos vendidos");
                System.out.println("10. Ver reporte de ganancias totales");
                System.out.println("11. Cerrar sesión y salir");
                System.out.print("¿Qué acción deseas realizar?: ");
                
                try {
                    opcion = Integer.parseInt(entrada.nextLine());
                } catch (NumberFormatException e) {
                    opcion = 0;
                }

                switch (opcion) {
                    case 1:
                        registrarUsuarioMenu(entrada, gestionUsuarios);
                        break;
                    case 2:
                        gestionUsuarios.mostrarUsuarios();
                        break;
                    case 3:
                        agregarProductoMenu(entrada, inventario);
                        break;
                    case 4:
                        inventario.mostrarInventario();
                        break;
                    case 5:
                        buscarProductoMenu(entrada, inventario);
                        break;
                    case 6:
                        modificarProductoMenu(entrada, inventario);
                        break;
                    case 7:
                        eliminarProductoMenu(entrada, inventario);
                        break;
                    case 8:
                        Venta ventaAdmin = new Venta();
                        ventaAdmin.procesarVenta(entrada, inventario, historialVentas);
                        break;
                    case 9:
                        mostrarReporteVentas(entrada, historialVentas);
                        break;
                    case 10:
                        mostrarReporteGanancias(entrada, historialVentas);
                        break;
                    case 11:
                        System.out.println("Cerrando sesión de administrador. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción no válida, selecciona un número del 1 al 11.");
                }
            } while (opcion != 11);

        } else if (usuarioLogueado.getRol().equalsIgnoreCase("Mecanico")) {
            do {
                System.out.println("\n=== MENÚ MECÁNICO (" + usuarioLogueado.getNombre() + " - " + ((Mecanico)usuarioLogueado).getEspecialidad() + ") ===");
                System.out.println("1. Ver inventario de repuestos");
                System.out.println("2. Buscar producto o repuesto");
                System.out.println("3. Registrar mantenimiento, asignar repuestos y cobrar");
                System.out.println("4. Salir del sistema");
                System.out.print("¿Qué acción deseas realizar?: ");

                try {
                    opcion = Integer.parseInt(entrada.nextLine());
                } catch (NumberFormatException e) {
                    opcion = 0;
                }

                switch (opcion) {
                    case 1:
                        inventario.mostrarInventario();
                        break;
                    case 2:
                        buscarProductoMenu(entrada, inventario);
                        break;
                    case 3:
                        registrarMantenimientoMecanico(entrada, inventario, historialVentas, usuarioLogueado);
                        break;
                    case 4:
                        System.out.println("Saliendo del módulo de mecánico...");
                        break;
                    default:
                        System.out.println("Opción no válida, selecciona un número del 1 al 4.");
                }
            } while (opcion != 4);

        } else if (usuarioLogueado.getRol().equalsIgnoreCase("Cliente")) {
            do {
                System.out.println("\n=== MENÚ TIENDA SERVIMOTOS ===");
                System.out.println("1. Ver catálogo de productos disponibles");
                System.out.println("2. Buscar un producto");
                System.out.println("3. Comprar productos");
                System.out.println("4. Salir de la tienda");
                System.out.print("¿Qué acción deseas realizar?: ");

                try {
                    opcion = Integer.parseInt(entrada.nextLine());
                } catch (NumberFormatException e) {
                    opcion = 0;
                }

                switch (opcion) {
                    case 1:
                        inventario.mostrarInventario();
                        break;
                    case 2:
                        buscarProductoMenu(entrada, inventario);
                        break;
                    case 3:
                        Venta ventaCliente = new Venta();
                        ventaCliente.procesarVenta(entrada, inventario, historialVentas);
                        break;
                    case 4:
                        System.out.println("¡Gracias por visitarnos! Vuelve pronto.");
                        break;
                    default:
                        System.out.println("Opción no válida, selecciona un número del 1 al 4.");
                }
            } while (opcion != 4);

        } else {
            do {
                System.out.println("\n=== MENÚ " + usuarioLogueado.getRol().toUpperCase() + " ===");
                System.out.println("1. Ver inventario");
                System.out.println("2. Buscar producto");
                System.out.println("3. Realizar venta");
                System.out.println("4. Salir");
                System.out.print("¿Qué acción deseas realizar?: ");

                try {
                    opcion = Integer.parseInt(entrada.nextLine());
                } catch (NumberFormatException e) {
                    opcion = 0;
                }

                switch (opcion) {
                    case 1:
                        inventario.mostrarInventario();
                        break;
                    case 2:
                        buscarProductoMenu(entrada, inventario);
                        break;
                    case 3:
                        Venta ventaEmpleado = new Venta();
                        ventaEmpleado.procesarVenta(entrada, inventario, historialVentas);
                        break;
                    case 4:
                        System.out.println("Cerrando sesión...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 4);
        }

        entrada.close();
    }

    private static void mostrarReporteVentas(Scanner entrada, ArrayList<Venta> historialVentas) {
        System.out.println("\n--- REPORTE DE VENTAS Y PRODUCTOS SALIENTES ---");
        if (historialVentas.isEmpty()) {
            System.out.println("Aún no se han registrado ventas en el sistema.");
            System.out.println("----------------------------------------\n");
            System.out.println("Presiona Enter para volver al menú...");
            entrada.nextLine();
            return;
        }

        for (Venta v : historialVentas) {
            System.out.println("Factura Nro: " + v.getIdVenta());
            System.out.println("Método de pago: " + v.getMetodoPago());
            System.out.println("Detalle de productos o servicios:");
            
            ArrayList<Producto> prods = v.getProductosVendidos();
            ArrayList<Integer> cants = v.getCantidadesVendidas();
            
            for (int i = 0; i < prods.size(); i++) {
                Producto p = prods.get(i);
                int cantidad = cants.get(i);
                double subtotal = p.getPrecio() * cantidad;
                System.out.println("  - [" + p.getCodigo() + "] " + p.getNombre() + 
                                   " | Cantidad: " + cantidad + " | Subtotal: $" + subtotal);
            }
            System.out.println("Total de esta factura: $" + v.getTotalVenta());
            System.out.println("----------------------------------------\n");
        }
        
        System.out.println("Presiona Enter para volver al menú...");
        entrada.nextLine();
    }

    private static void mostrarReporteGanancias(Scanner entrada, ArrayList<Venta> historialVentas) {
        System.out.println("\n--- REPORTE DE GANANCIAS TOTALES ---");
        if (historialVentas.isEmpty()) {
            System.out.println("Total de ventas realizadas: 0");
            System.out.println("Total de unidades vendidas: 0");
            System.out.println("Ganancias totales recaudadas: $0.0");
            System.out.println("----------------------------------------\n");
            System.out.println("Presiona Enter para volver al menú...");
            entrada.nextLine();
            return;
        }

        double gananciasTotales = 0;
        int totalUnidades = 0;
        double totalEfectivo = 0;
        double totalTarjeta = 0;
        double totalNequi = 0;

        for (Venta v : historialVentas) {
            gananciasTotales += v.getTotalVenta();
            
            for (int cant : v.getCantidadesVendidas()) {
                totalUnidades += cant;
            }

            if (v.getMetodoPago().equalsIgnoreCase("Efectivo")) {
                totalEfectivo += v.getTotalVenta();
            } else if (v.getMetodoPago().equalsIgnoreCase("Tarjeta")) {
                totalTarjeta += v.getTotalVenta();
            } else {
                totalNequi += v.getTotalVenta();
            }
        }

        System.out.println("Total de facturas procesadas: " + historialVentas.size());
        System.out.println("Total de unidades vendidas: " + totalUnidades);
        System.out.println("Recaudado en efectivo: $" + totalEfectivo);
        System.out.println("Recaudado con tarjeta: $" + totalTarjeta);
        System.out.println("Recaudado por Nequi / Transferencia: $" + totalNequi);
        System.out.println("GANANCIA BRUTA TOTAL: $" + gananciasTotales);
        System.out.println("----------------------------------------\n");
        
        System.out.println("Presiona Enter para volver al menú...");
        entrada.nextLine();
    }

    private static void registrarUsuarioMenu(Scanner entrada, GestionUsuarios gu) {
        System.out.println("\n--- REGISTRAR NUEVO USUARIO ---");
        System.out.println("Selecciona el tipo de perfil:");
        System.out.println("1. Cliente");
        System.out.println("2. Mecánico");
        System.out.println("3. Empleado");
        System.out.println("4. Administrador");
        System.out.print("Elige una opción: ");
        int tipo = Integer.parseInt(entrada.nextLine());

        System.out.print("Ingresa el ID de usuario (número): ");
        int idU = Integer.parseInt(entrada.nextLine());
        System.out.print("Nombre completo o de usuario: ");
        String nombre = entrada.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = entrada.nextLine();
        System.out.print("Contraseña de acceso: ");
        String pass = entrada.nextLine();
        System.out.print("Número de teléfono: ");
        String tel = entrada.nextLine();

        if (tipo == 1) {
            System.out.print("Dirección de residencia: ");
            String dir = entrada.nextLine();
            gu.agregarUsuario(new Cliente(idU, nombre, correo, pass, tel, dir));
        } else if (tipo == 2) {
            System.out.print("Especialidad del mecánico (ej. motor, suspensión): ");
            String esp = entrada.nextLine();
            gu.agregarUsuario(new Mecanico(idU, nombre, correo, pass, tel, esp));
        } else if (tipo == 3) {
            System.out.print("Turno de trabajo: ");
            String turno = entrada.nextLine();
            gu.agregarUsuario(new Empleado(idU, nombre, correo, pass, tel, turno));
        } else if (tipo == 4) {
            gu.agregarUsuario(new Administrador(idU, nombre, correo, pass, tel));
        } else {
            System.out.println("Opción de tipo no válida.");
        }
    }

    private static void agregarProductoMenu(Scanner entrada, Inventario inv) {
        System.out.println("\n--- AGREGAR NUEVO PRODUCTO AL INVENTARIO ---");
        
        int idP = inv.getProductos().size() + 1;
        System.out.println("ID asignado de forma automática: " + idP);
        
        String cod;
        while (true) {
            System.out.print("Ingresa el código del producto (ej. 101): ");
            cod = entrada.nextLine().trim();
            if (!cod.isEmpty()) {
                if (inv.buscarPorCodigo(cod) == null) {
                    break;
                } else {
                    System.out.println("Ups, ya existe un producto registrado con ese código. Prueba con otro diferente.");
                }
            } else {
                System.out.println("El código no puede quedar vacío.");
            }
        }

        System.out.print("Nombre del producto: ");
        String nomP = entrada.nextLine();
        
        System.out.print("Breve descripción: ");
        String desc = entrada.nextLine();

        double precio = 0;
        while (true) {
            try {
                System.out.print("Precio de venta ($): ");
                precio = Double.parseDouble(entrada.nextLine());
                if (precio >= 0) break;
                System.out.println("El precio no puede tener valores negativos.");
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido para el precio.");
            }
        }

        int stock = 0;
        while (true) {
            try {
                System.out.print("Cantidad inicial en stock: ");
                stock = Integer.parseInt(entrada.nextLine());
                if (stock >= 0) break;
                System.out.println("El stock inicial no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número entero válido.");
            }
        }

        System.out.println("\nSelecciona la categoría correspondiente:");
        System.out.println("1. Lubricantes y Químicos");
        System.out.println("2. Repuestos y Motor");
        System.out.println("3. Frenos y Arrastre");
        System.out.println("4. Eléctrico y Llantas");
        System.out.print("Opción (1-4): ");
        String opCat = entrada.nextLine().trim();

        Categoria categoriaSeleccionada;
        switch (opCat) {
            case "1":
                categoriaSeleccionada = new Categoria(1, "Lubricantes y Quimicos", "Aceites, fluidos, grasas y limpiadores");
                break;
            case "3":
                categoriaSeleccionada = new Categoria(3, "Frenos y Arrastre", "Pastillas, bandas, discos, kits y cadenas");
                break;
            case "4":
                categoriaSeleccionada = new Categoria(4, "Electrico y Llantas", "Baterias, luces LED, encendido, llantas y camaras");
                break;
            default:
                categoriaSeleccionada = new Categoria(2, "Repuestos y Motor", "Filtros, bujias, empaques, carburacion y guayas");
                break;
        }
        
        inv.agregarProducto(new Producto(idP, cod, nomP, desc, precio, stock, "sin_foto", categoriaSeleccionada));
        System.out.println("¡Listo! Producto guardado con éxito en el inventario.");
    }

    private static void buscarProductoMenu(Scanner entrada, Inventario inv) {
        System.out.print("Ingresa el código o nombre del producto que buscas: ");
        String criterio = entrada.nextLine();
        Producto encontrado = inv.buscarProducto(criterio);
        if (encontrado != null) {
            System.out.println("\n¡Producto encontrado!");
            System.out.println("ID: " + encontrado.getIdProducto());
            System.out.println("Código: " + encontrado.getCodigo());
            System.out.println("Nombre: " + encontrado.getNombre());
            System.out.println("Categoría: " + (encontrado.getCategoria() != null ? encontrado.getCategoria().getNombre() : "Sin categoría"));
            System.out.println("Precio: $" + encontrado.getPrecio());
            System.out.println("Stock disponible: " + encontrado.getStock());
            System.out.println("----------------------------------------\n");
        } else {
            System.out.println("No se encontró ningún producto con ese criterio.");
        }
    }

    private static void modificarProductoMenu(Scanner entrada, Inventario inv) {
        System.out.println("\n--- MODIFICAR PRODUCTO ---");
        System.out.print("Ingresa el código del producto que deseas modificar: ");
        String codigo = entrada.nextLine().trim();

        Producto p = inv.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("No encontramos ningún producto registrado con el código: " + codigo);
            System.out.println("Presiona Enter para continuar...");
            entrada.nextLine();
            return;
        }

        System.out.println("\nProducto seleccionado: " + p.getNombre() + " | Precio actual: $" + p.getPrecio());
        System.out.println("¿Qué te gustaría cambiar?");
        System.out.println("1. Cambiar solo el nombre");
        System.out.println("2. Cambiar solo el precio");
        System.out.println("3. Cambiar ambos (nombre y precio)");
        System.out.print("Elige una opción (1-3): ");
        String opMod = entrada.nextLine().trim();

        String nuevoNombre = p.getNombre();
        double nuevoPrecio = p.getPrecio();

        if (opMod.equals("1") || opMod.equals("3")) {
            System.out.print("Escribe el nuevo nombre: ");
            nuevoNombre = entrada.nextLine().trim();
        }

        if (opMod.equals("2") || opMod.equals("3")) {
            while (true) {
                try {
                    System.out.print("Escribe el nuevo precio ($): ");
                    nuevoPrecio = Double.parseDouble(entrada.nextLine().trim());
                    if (nuevoPrecio >= 0) break;
                    System.out.println("El precio no puede ser negativo.");
                } catch (NumberFormatException e) {
                    System.out.println("Ingresa un valor numérico válido.");
                }
            }
        }

        boolean exito = inv.modificarProductoPorCodigo(codigo, nuevoNombre, nuevoPrecio);
        if (exito) {
            System.out.println("\n¡Datos actualizados con éxito!");
            System.out.println("Nuevo nombre: " + nuevoNombre + " | Nuevo precio: $" + nuevoPrecio);
        } else {
            System.out.println("Hubo un problema al intentar modificar el producto.");
        }
        
        System.out.println("\nPresiona Enter para continuar...");
        entrada.nextLine();
    }

    private static void eliminarProductoMenu(Scanner entrada, Inventario inv) {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        System.out.print("Ingresa el código del producto que deseas eliminar: ");
        String codigo = entrada.nextLine().trim();

        Producto p = inv.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("No se encontró ningún producto con el código: " + codigo);
        } else {
            System.out.println("Vas a eliminar: " + p.getNombre() + " (Código: " + p.getCodigo() + ")");
            System.out.print("¿Estás seguro de que deseas eliminar este producto? (s/n): ");
            String confirmar = entrada.nextLine().trim();
            if (confirmar.equalsIgnoreCase("s")) {
                inv.eliminarProductoPorCodigo(codigo);
                System.out.println("¡El producto ha sido eliminado del inventario correctamente!");
            } else {
                System.out.println("Operación cancelada. El producto no fue eliminado.");
            }
        }
        System.out.println("\nPresiona Enter para continuar...");
        entrada.nextLine();
    }

    private static void registrarMantenimientoMecanico(Scanner entrada, Inventario inv, ArrayList<Venta> historialVentas, Usuario mecanico) {
        System.out.println("\n--- REGISTRAR MANTENIMIENTO Y TALLER ---");
        
        System.out.print("Describe brevemente el servicio realizado (ej. Sincronización y cambio de aceite): ");
        String descServicio = entrada.nextLine().trim();

        double valorServicio = 0;
        while (true) {
            try {
                System.out.print("Ingresa el valor de la mano de obra ($): ");
                valorServicio = Double.parseDouble(entrada.nextLine());
                if (valorServicio >= 0) break;
                System.out.println("El valor no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un monto válido.");
            }
        }

        ArrayList<Producto> prodsUsados = new ArrayList<>();
        ArrayList<Integer> cantsUsadas = new ArrayList<>();
        double totalRepuestos = 0;

        String continuar = "s";
        while (continuar.equalsIgnoreCase("s")) {
            System.out.print("Ingresa el código del repuesto utilizado (o presiona enter si no aplica): ");
            String codigo = entrada.nextLine().trim();
            
            if (!codigo.isEmpty()) {
                Producto p = inv.buscarPorCodigo(codigo);
                if (p != null) {
                    System.out.println("Repuesto encontrado: " + p.getNombre() + " | Stock disponible: " + p.getStock() + " | Precio U.: $" + p.getPrecio());
                    
                    System.out.print("¿Cuántas unidades se usaron?: ");
                    int cantidad = Integer.parseInt(entrada.nextLine());

                    if (cantidad > 0 && p.tieneStockDisponible(cantidad)) {
                        p.actualizarStock(-cantidad);
                        
                        prodsUsados.add(p);
                        cantsUsadas.add(cantidad);
                        totalRepuestos += (p.getPrecio() * cantidad);
                        
                        inv.actualizarCambiosStock();
                        System.out.println("¡Repuesto agregado al servicio y stock descontado!");
                    } else {
                        System.out.println("Cantidad inválida o no hay suficiente stock (" + p.getStock() + " disponibles).");
                    }
                } else {
                    System.out.println("No se encontró ningún producto con ese código.");
                }
            }

            System.out.print("¿Deseas agregar otro repuesto a este servicio? (s/n): ");
            continuar = entrada.nextLine().trim();
        }

        if (valorServicio > 0) {
            Categoria catServicio = new Categoria(99, "Servicios", "Mano de obra y taller");
            Producto pServicio = new Producto(999, "SERV", "Mano de Obra (" + descServicio + ")", descServicio, valorServicio, 1, "sin_foto", catServicio);
            prodsUsados.add(pServicio);
            cantsUsadas.add(1);
        }

        System.out.println("\nSelecciona el método de pago:");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta");
        System.out.println("3. Nequi / Transferencia");
        System.out.print("Elige una opción (1-3): ");
        String opPago = entrada.nextLine().trim();
        String metodoPago = "Efectivo";
        if (opPago.equals("2")) {
            metodoPago = "Tarjeta";
        } else if (opPago.equals("3")) {
            metodoPago = "Nequi";
        }

        double totalGeneral = totalRepuestos + valorServicio;
        int idVentaFicticio = historialVentas.size() + 1;

        Venta nuevaVenta = new Venta(idVentaFicticio, prodsUsados, cantsUsadas, totalGeneral, metodoPago);
        historialVentas.add(nuevaVenta);

        System.out.println("\n========================================");
        System.out.println("      RECIBO DE TALLER Y SERVICIO");
        System.out.println("========================================");
        System.out.println("Mecánico a cargo: " + mecanico.getNombre());
        System.out.println("Servicio: " + descServicio);
        System.out.println("Mano de obra: $" + valorServicio);
        System.out.println("Total repuestos: $" + totalRepuestos);
        System.out.println("TOTAL A PAGAR: $" + totalGeneral);
        System.out.println("Método de pago: " + metodoPago);
        System.out.println("¡Servicio registrado con éxito y guardado en el sistema!");
        System.out.println("========================================\n");
        
        System.out.println("Presiona Enter para continuar...");
        entrada.nextLine();
    }
}