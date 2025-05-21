/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesComportamiento;

import controlador.ControladorProducto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Michel Mendez
 */
public class ProductoObservable extends ControladorProducto implements Observer.Observable {

    private final List<Observer.Obser> observadores = new ArrayList<>();

    @Override
    public void agregarObservador(Observer.Obser o) {
        observadores.add(o);
    }

    @Override
    public void eliminarObservador(Observer.Obser o) {
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores() {
        for (Observer.Obser o : observadores) {
            o.actualizar();
        }
    }

    @Override
    public void AgregarProducto(JTextField nombres, JTextField precioProducto, JTextField stock, JComboBox<String> cmbCategoria) {
        super.AgregarProducto(nombres, precioProducto, stock, cmbCategoria);
        notificarObservadores();
    }

    @Override
    public void ModificarProducto(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock, JComboBox<String> cmbCategoria) {
        super.ModificarProducto(id, nombres, precioProducto, stock, cmbCategoria);
        notificarObservadores();
    }

    @Override
    public void EliminarProductos(JTextField id) {
        super.EliminarProductos(id);
        notificarObservadores();
    }
}
