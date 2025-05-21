/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import controlador.ControladorProducto;

public class FactoryProducto implements FactoryControlador {
    @Override
    public ControladorProducto crearControlador() {
        return new ControladorProducto.Builder()
                .habilitarLogs(true)
                .mostrarMensajesError(true)
                .build();
    }
    
}