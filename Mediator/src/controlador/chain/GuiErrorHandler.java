/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.chain;

import javax.swing.JOptionPane;

/**
 *
 * @author Betinsky
 */
public class GuiErrorHandler extends ErrorHandler {
    @Override
    public void handleError(String mensaje, Exception e) {
        JOptionPane.showMessageDialog(null, "[GUI] " + mensaje + (e != null ? " - " + e.toString() : ""));
        if (siguiente != null) {
            siguiente.handleError(mensaje, e);
        }
    }
}
