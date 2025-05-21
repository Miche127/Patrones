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
public class StockMayor implements Expresion {
    private int valor;

    public StockMayor(int valor) {
        this.valor = valor;
    }

    public boolean interpretar(ModeloProducto producto) {
        return producto.getStockProducto() > valor;
    }
}
