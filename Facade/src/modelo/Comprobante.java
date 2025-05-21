/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;

/**
 *
 * @author Michel Mendez
 */
public class Comprobante {
    private int id;
    private String cliente;
    private List<String> productos;
    private double total;

    public Comprobante(int id, String cliente, List<String> productos, double total) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
        this.total = total;
    }

    public String generarTexto() {
        return "Comprobante ID: " + id + "\nCliente: " + cliente + "\nProductos: " + productos + "\nTotal: S/ " + total;
    }
}
