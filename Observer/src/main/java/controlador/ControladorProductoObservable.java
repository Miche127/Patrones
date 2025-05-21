/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import observer.Observer;
import observer.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michel Mendez
 */
public class ControladorProductoObservable extends ControladorProducto implements Observable {

    private final List<Observer> observadores = new ArrayList<>();

    @Override
    public void agregarObservador(Observer o) {
        observadores.add(o);
    }

    @Override
    public void eliminarObservador(Observer o){
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores() {
        for (Observer o : observadores) {
            o.actualizar();
        }
    }

    //Sobrescribimos AgregarProducto para notificar
    @Override
    public void AgregarProducto(javax.swing.JTextField nombres, javax.swing.JTextField precioProducto, javax.swing.JTextField stock) {
        super.AgregarProducto(nombres, precioProducto, stock);
        notificarObservadores();
    }

    @Override
    public void ModificarProducto(javax.swing.JTextField id, javax.swing.JTextField nombres, javax.swing.JTextField precioProducto, javax.swing.JTextField stock) {
        super.ModificarProducto(id, nombres, precioProducto, stock);
        notificarObservadores();
    }

    @Override
    public void EliminarProductos(javax.swing.JTextField id) {
        super.EliminarProductos(id);
        notificarObservadores();
    }
}
