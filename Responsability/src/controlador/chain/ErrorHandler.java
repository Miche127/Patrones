/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.chain;

/**
 *
 * @author Betinsky
 */
public abstract class ErrorHandler {
    protected ErrorHandler siguiente;

    public void setSiguiente(ErrorHandler siguiente) {
        this.siguiente = siguiente;
    }

    // Método abstracto para procesar el error o validación
    public abstract void handleError(String mensaje, Exception e);
}