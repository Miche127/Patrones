/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesComportamiento;

import java.util.List;

/**
 *
 * @author Michel Mendez
 */
public class IteratorProductos implements Iterador {

    private List<String> productos;
    private int posicion = 0;

    public IteratorProductos(List<String> productos) {
        this.productos = productos;
    }

    public boolean tieneSiguiente() {
        return posicion < productos.size();
    }

    public Object siguiente() {
        return productos.get(posicion++);
    }
}
