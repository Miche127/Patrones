/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import controlador.ControladorReportes;

public class FactoryReporte implements FactoryControlador {
        
    public ControladorReportes crearControlador() {
        return new ControladorReportes.Builder()
                .habilitarLogs(true)
                .mostrarMensajesError(true)
                .build();
    }
        
}