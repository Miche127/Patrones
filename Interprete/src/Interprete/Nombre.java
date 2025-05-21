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
public class Nombre implements Expresion {
    private String texto;

    public Nombre(String texto) {
        this.texto = texto.toLowerCase();
    }

    public boolean interpretar(ModeloProducto producto) {
        return producto.getNombreProducto().toLowerCase().contains(texto);
    }
}

