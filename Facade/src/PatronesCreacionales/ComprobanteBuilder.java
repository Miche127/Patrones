/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesCreacionales;

import modelo.Comprobante;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Michel Mendez
 */
public class ComprobanteBuilder {
    private int id;
    private String cliente;
    private List<String> productos = new ArrayList<>();
    private double total;

    public ComprobanteBuilder setId(int id) {
        this.id = id;
        return this;
    }
    public ComprobanteBuilder setCliente(String cliente) {
        this.cliente = cliente;
        return this;
    }
    public ComprobanteBuilder addProducto(String producto) {
        productos.add(producto);
        return this;
    }
    public ComprobanteBuilder setTotal(double total) {
        this.total = total;
        return this;
    }
    public Comprobante build() {
        return new Comprobante(id, cliente, productos, total);
    }
}
