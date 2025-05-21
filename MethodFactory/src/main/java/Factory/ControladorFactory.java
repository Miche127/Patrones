/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import controlador.ControladorCliente;
import controlador.ControladorProducto;
import controlador.ControlAlquiler;
import controlador.ControladorReportes;

/**
 *
 * @author Michel Mendez
 */

public class ControladorFactory {
    
    public static IControlador crearControlador(String tipo) {
        switch (tipo) {
            case "cliente":
                return new ControladorCliente();
            case "producto":
                return new ControladorProducto();
            case "alquiler":
                return new ControlAlquiler();
            case "reporte":
                return new ControladorReportes();
            default:
                throw new IllegalArgumentException("Tipo de controlador no reconocido: " + tipo);
        }
    }
}