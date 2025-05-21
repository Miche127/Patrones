/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import controlador.ControladorCliente;

public class FactoryCliente implements FactoryControlador {
    @Override
    public ControladorCliente crearControlador() {
        return new ControladorCliente.Builder()
                .habilitarLogs(true)
                .mostrarMensajesError(true)
                .build();
    }
}

