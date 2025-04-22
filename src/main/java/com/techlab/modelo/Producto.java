package com.techlab.modelo;

public class Producto {
    private static int contadorId = 1;
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setPrecio(double precio) {
        if(precio >= 0) {
            this.precio = precio;
        }
    }

    public void setStock(int stock) {
        if(stock >= 0) {
            this.stock = stock;
        }
    }

    public void disminuirStock(int cantidad) {
        if(cantidad <= stock) {
            stock -= cantidad;
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Precio: %.2f | Stock: %d", id, nombre, precio, stock);
    }
}
