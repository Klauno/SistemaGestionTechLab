package com.techlab.modelo;

public class LineaPedido {
    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public double getCostoLinea() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return String.format("%s | Cantidad: %d | Subtotal: %.2f",
                producto.toString(), cantidad, getCostoLinea());
    }
}
