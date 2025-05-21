/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import factory.*;
import controlador.*;

/**
 *
 * @author ferna
 */
public class FormClientes {

    private ControladorCliente controlador;

    public FormClientes() {
        FactoryControlador factory = new FactoryCliente();
        this.controlador = (ControladorCliente) factory.crearControlador();
    }
}
