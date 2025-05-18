package com.techlab.servicio;

import com.techlab.modelo.LineaPedido;
import com.techlab.modelo.Pedido;
import com.techlab.modelo.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionProductosPedidos {
    private List<Producto> productos;
    private List<Pedido> pedidos;

    public GestionProductosPedidos() {
        this.productos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public void agregarProducto(String nombre, double precio, int stock) {
        if (buscarProductoPorNombre(nombre) != null) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre.");
        }
        Producto p = new Producto(nombre, precio, stock);
        productos.add(p);
    }

    public List<Producto> listarProductos() {
        return productos;
    }

    public Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Producto buscarProductoPorNombre(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // Búsqueda flexible por ID o nombre
    public Producto buscarProductoPorIdONombre(String entrada) {
        try {
            int id = Integer.parseInt(entrada);
            return buscarProductoPorId(id);
        } catch (NumberFormatException e) {
            return buscarProductoPorNombre(entrada);
        }
    }

    public boolean actualizarProducto(int id, double nuevoPrecio, int nuevoStock) {
        Producto p = buscarProductoPorId(id);
        if (p != null) {
            if (nuevoPrecio >= 0) p.setPrecio(nuevoPrecio);
            if (nuevoStock >= 0) p.setStock(nuevoStock);
            return true;
        }
        return false;
    }

    public boolean eliminarProducto(int id) {
        Producto p = buscarProductoPorId(id);
        if (p != null) {
            productos.remove(p);
            return true;
        }
        return false;
    }

    public Pedido crearPedido() {
        return new Pedido();
    }

    /**
     * Si el producto ya está en el pedido, suma la cantidad.
     * Si no, agrega una nueva línea.
     * Así se evita tener varias líneas del mismo producto en el pedido.
     */
    public boolean agregarLineaPedido(Pedido pedido, int idProducto, int cantidad) {
        Producto p = buscarProductoPorId(idProducto);
        if (p != null && cantidad > 0 && cantidad <= p.getStock()) {
            // Buscar si ya existe una línea para este producto
            LineaPedido existente = null;
            for (LineaPedido linea : pedido.getLineas()) {
                if (linea.getProducto().getId() == idProducto) {
                    existente = linea;
                    break;
                }
            }
            if (existente != null) {
                int nuevaCantidad = existente.getCantidad() + cantidad;
                if (nuevaCantidad > p.getStock()) {
                    return false; // No hay suficiente stock
                }
                pedido.getLineas().remove(existente);
                pedido.agregarLinea(new LineaPedido(p, nuevaCantidad));
            } else {
                pedido.agregarLinea(new LineaPedido(p, cantidad));
            }
            return true;
        }
        return false;
    }

    /**
     * Al confirmar el pedido:
     * - Suma la cantidad total pedida de cada producto (por si se agregó varias veces el mismo producto).
     * - Verifica que no supere el stock disponible.
     * - Si todo está bien, descuenta el stock y guarda el pedido.
     */
    public boolean confirmarPedido(Pedido pedido) {
        if (pedido.getLineas().isEmpty()) return false;

        // Sumar cantidades por producto
        Map<Producto, Integer> cantidadesPorProducto = new HashMap<>();
        for (LineaPedido linea : pedido.getLineas()) {
            cantidadesPorProducto.merge(linea.getProducto(), linea.getCantidad(), Integer::sum);
        }

        // Verificar stock suficiente para cada producto
        for (Map.Entry<Producto, Integer> entry : cantidadesPorProducto.entrySet()) {
            Producto producto = entry.getKey();
            int cantidadTotal = entry.getValue();
            if (cantidadTotal > producto.getStock()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
            }
        }

        // Descontar stock
        for (LineaPedido linea : pedido.getLineas()) {
            linea.getProducto().disminuirStock(linea.getCantidad());
        }
        pedidos.add(pedido);
        return true;
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}
