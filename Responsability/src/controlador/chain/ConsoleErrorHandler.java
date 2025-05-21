/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.chain;

/**
 *
 * @author Betinsky
 */
public class ConsoleErrorHandler extends ErrorHandler {
    @Override
    public void handleError(String mensaje, Exception e) {
        System.out.println("[CONSOLE] " + mensaje + (e != null ? " - " + e.toString() : ""));
        if (siguiente != null) {
            siguiente.handleError(mensaje, e);
        }
    }
}