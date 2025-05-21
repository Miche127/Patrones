/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interprete;

import modelo.ModeloProducto;

/**
 *
 * @author Garaudy
 */
public class PrecioMenor implements Expresion {
    private double valor;

    public PrecioMenor(double valor) {
        this.valor = valor;
    }

    public boolean interpretar(ModeloProducto producto) {
        return producto.getPrecioProducto() < valor;
    }
}
