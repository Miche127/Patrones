/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesComportamiento;

import controlador.ControladorProducto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michel Mendez
 */

public class Observer {

    public interface Obser {
        void actualizar();
    }

    public interface Observable {
        void agregarObservador(Obser o);
        void eliminarObservador(Obser o);
        void notificarObservadores();
    }
}
