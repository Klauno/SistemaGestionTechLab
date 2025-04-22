package com.techlab.servicio;

import com.techlab.modelo.LineaPedido;
import com.techlab.modelo.Pedido;
import com.techlab.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class GestionProductosPedidos {
    private List<Producto> productos;
    private List<Pedido> pedidos;

    public GestionProductosPedidos() {
        this.productos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    // Agregar producto
    public void agregarProducto(String nombre, double precio, int stock) {
        Producto p = new Producto(nombre, precio, stock);
        productos.add(p);
    }

    // Listar productos
    public List<Producto> listarProductos() {
        return productos;
    }

    // Buscar producto por ID
    public Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Buscar producto por nombre (retorna el primero que coincida)
    public Producto buscarProductoPorNombre(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // Actualizar producto (precio y stock)
    public boolean actualizarProducto(int id, double nuevoPrecio, int nuevoStock) {
        Producto p = buscarProductoPorId(id);
        if (p != null && nuevoPrecio >= 0 && nuevoStock >= 0) {
            p.setPrecio(nuevoPrecio);
            p.setStock(nuevoStock);
            return true;
        }
        return false;
    }

    // Eliminar producto por ID
    public boolean eliminarProducto(int id) {
        Producto p = buscarProductoPorId(id);
        if (p != null) {
            productos.remove(p);
            return true;
        }
        return false;
    }

    // Crear pedido
    public Pedido crearPedido() {
        return new Pedido();
    }

    // Agregar línea al pedido
    public boolean agregarLineaPedido(Pedido pedido, int idProducto, int cantidad) {
        Producto p = buscarProductoPorId(idProducto);
        if (p != null && cantidad > 0 && cantidad <= p.getStock()) {
            LineaPedido linea = new LineaPedido(p, cantidad);
            pedido.agregarLinea(linea);
            return true;
        }
        return false;
    }

    // Confirmar pedido: descontar stock y guardar pedido
    public boolean confirmarPedido(Pedido pedido) {
        if (pedido.getLineas().isEmpty()) {
            return false;
        }
        // Verificar stock suficiente antes de descontar
        for (LineaPedido linea : pedido.getLineas()) {
            if (linea.getCantidad() > linea.getProducto().getStock()) {
                return false;
            }
        }
        // Descontar stock
        for (LineaPedido linea : pedido.getLineas()) {
            linea.getProducto().disminuirStock(linea.getCantidad());
        }
        pedidos.add(pedido);
        return true;
    }

    // Listar pedidos
    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}
