package com.techlab.modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contadorPedidos = 1;
    private int idPedido;
    private List<LineaPedido> lineas;

    public Pedido() {
        this.idPedido = contadorPedidos++;
        this.lineas = new ArrayList<>();
    }

    public int getIdPedido() { return idPedido; }
    public List<LineaPedido> getLineas() { return lineas; }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getCostoLinea();
        }
        return total;
    }

    public void mostrarPedido() {
        System.out.println("Pedido ID: " + idPedido);
        for (LineaPedido linea : lineas) {
            System.out.println("  " + linea.toString());
        }
        System.out.printf("Costo total: %.2f\n", calcularTotal());
    }
}
