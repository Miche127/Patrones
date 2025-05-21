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
public class Or implements Expresion {
    private Expresion expr1;
    private Expresion expr2;

    public Or(Expresion expr1, Expresion expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public boolean interpretar(ModeloProducto producto) {
        return expr1.interpretar(producto) || expr2.interpretar(producto);
    }
}
