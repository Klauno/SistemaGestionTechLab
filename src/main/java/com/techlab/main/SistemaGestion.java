package com.techlab.main;

import com.techlab.modelo.Pedido;
import com.techlab.modelo.Producto;
import com.techlab.servicio.GestionProductosPedidos;

import java.util.List;
import java.util.Scanner;

public class SistemaGestion {
    private static Scanner scanner = new Scanner(System.in);
    private static GestionProductosPedidos gestion = new GestionProductosPedidos();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Elija una opción: ");
            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> listarProductos();
                case 3 -> buscarActualizarProducto();
                case 4 -> eliminarProducto();
                case 5 -> crearPedido();
                case 6 -> listarPedidos();
                case 7 -> {
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    salir = true;
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=================================== SISTEMA DE GESTIÓN - TECHLAB ==================================");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
    }

    private static void agregarProducto() {
        System.out.println("\n--- Agregar Producto ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        double precio = leerDouble("Precio: ");
        int stock = leerEntero("Cantidad en stock: ");
        try {
            gestion.agregarProducto(nombre, precio, stock);
            System.out.println("Producto agregado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarProductos() {
        System.out.println("\n--- Lista de Productos ---");
        List<Producto> productos = gestion.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    private static void buscarActualizarProducto() {
        System.out.println("\n--- Buscar/Actualizar Producto ---");
        listarProductos();
        int id = leerEntero("Ingrese ID del producto a buscar: ");
        Producto producto = gestion.buscarProductoPorId(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.println("Producto encontrado: " + producto);

        String respuesta;
        boolean entradaValida;
        do {
            System.out.print("¿Desea actualizar precio y stock? (s/n): ");
            respuesta = scanner.nextLine().trim().toLowerCase();
            entradaValida = respuesta.equals("s") || respuesta.equals("n");
            if (!entradaValida) {
                System.out.println("Entrada inválida. Por favor, ingrese 's' para sí o 'n' para no.");
            }
        } while (!entradaValida);

        if (respuesta.equals("s")) {
            double nuevoPrecio = leerDouble("Nuevo precio: ");
            int nuevoStock = leerEntero("Nuevo stock: ");
            boolean actualizado = gestion.actualizarProducto(id, nuevoPrecio, nuevoStock);
            if (actualizado) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("Error al actualizar producto. Verifique los valores.");
            }
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n--- Eliminar Producto ---");
        listarProductos();
        int id = leerEntero("Ingrese ID del producto a eliminar: ");
        Producto producto = gestion.buscarProductoPorId(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.println("Producto encontrado: " + producto);

        String confirmar;
        boolean entradaValida;
        do {
            System.out.print("¿Confirma eliminación? (s/n): ");
            confirmar = scanner.nextLine().trim().toLowerCase();
            entradaValida = confirmar.equals("s") || confirmar.equals("n");
            if (!entradaValida) {
                System.out.println("Entrada inválida. Por favor, ingrese 's' para sí o 'n' para no.");
            }
        } while (!entradaValida);

        if (confirmar.equals("s")) {
            boolean eliminado = gestion.eliminarProducto(id);
            if (eliminado) {
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Error al eliminar producto.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private static void crearPedido() {
        System.out.println("\n--- Crear Pedido ---");
        List<Producto> productos = gestion.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para pedir.");
            return;
        }

        Pedido pedido = gestion.crearPedido();
        boolean agregoAlgo = false;

        while (true) {
            listarProductos();
            System.out.println("Ingrese ID del producto a agregar al pedido (o 0 para cancelar y salir): ");
            int idProducto = leerEntero("> ");
            if (idProducto == 0) {
                // Permite cancelar la carga del pedido en cualquier momento
                break;
            }
            Producto producto = gestion.buscarProductoPorId(idProducto);
            if (producto == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }
            System.out.println("Producto seleccionado: " + producto);
            System.out.println("Ingrese cantidad deseada (o 0 para cancelar y salir): ");
            int cantidad = leerEntero("> ");
            if (cantidad == 0) {
                // Permite cancelar la carga del pedido en cualquier momento
                break;
            }
            if (cantidad <= 0) {
                System.out.println("Cantidad debe ser mayor a 0.");
                continue;
            }
            if (cantidad > producto.getStock()) {
                System.out.println("No hay suficiente stock. Stock disponible: " + producto.getStock());
                continue;
            }
            boolean agregado = gestion.agregarLineaPedido(pedido, idProducto, cantidad);
            if (agregado) {
                System.out.println("Producto agregado al pedido.");
                agregoAlgo = true;
            } else {
                System.out.println("Error al agregar producto al pedido.");
            }

            // Preguntar si desea agregar otro producto
            if (!confirmarAccion("¿Desea agregar otro producto al pedido?")) {
                break;
            }
        }

        if (!agregoAlgo || pedido.getLineas().isEmpty()) {
            System.out.println("Pedido vacío o cancelado, no se creó.");
            return;
        }

        System.out.printf("Costo total del pedido: %.2f\n", pedido.calcularTotal());
        String confirmar;
        boolean entradaValidaConfirmar;
        do {
            System.out.print("¿Confirma el pedido? (s/n): ");
            confirmar = scanner.nextLine().trim().toLowerCase();
            entradaValidaConfirmar = confirmar.equals("s") || confirmar.equals("n");
            if (!entradaValidaConfirmar) {
                System.out.println("Entrada inválida. Por favor, ingrese 's' para sí o 'n' para no.");
            }
        } while (!entradaValidaConfirmar);

        if (confirmar.equals("s")) {
            try {
                boolean confirmado = gestion.confirmarPedido(pedido);
                if (confirmado) {
                    System.out.println("Pedido confirmado y guardado.");
                } else {
                    System.out.println("No se pudo confirmar el pedido. Verifique stock.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error al confirmar pedido: " + e.getMessage());
            }
        } else {
            System.out.println("Pedido cancelado.");
        }
    }

    private static void listarPedidos() {
        System.out.println("\n--- Lista de Pedidos ---");
        List<Pedido> pedidos = gestion.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos realizados.");
            return;
        }
        for (Pedido p : pedidos) {
            p.mostrarPedido();
            System.out.println("----------------------------");
        }
    }

    // Métodos auxiliares para lectura segura

    private static int leerEntero(String mensaje) {
        int valor = -1;
        while (true) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            }
        }
        return valor;
    }

    private static double leerDouble(String mensaje) {
        double valor = -1;
        while (true) {
            try {
                System.out.print(mensaje);
                valor = Double.parseDouble(scanner.nextLine());
                if (valor < 0) {
                    System.out.println("El valor no puede ser negativo.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número decimal válido.");
            }
        }
        return valor;
    }

    private static boolean confirmarAccion(String mensaje) {
        String respuesta;
        do {
            System.out.print(mensaje + " (s/n): ");
            respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) return true;
            if (respuesta.equals("n")) return false;
            System.out.println("Entrada inválida. Por favor, ingrese 's' o 'n'.");
        } while (true);
    }
}
