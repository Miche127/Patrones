/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observer;

/**
 *
 * @author Michel Mendez
 */
public interface Observable {
    void agregarObservador(Observer o);
    void eliminarObservador(Observer o);
    void notificarObservadores();
}
