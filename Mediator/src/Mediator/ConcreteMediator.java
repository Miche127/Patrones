/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mediator;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Betinsky
 */
public class ConcreteMediator implements Mediator {
    // Guarda referencias a los componentes
    private  List<Object> productos = new ArrayList<>();
    private  List<Object> clientes = new ArrayList<>();

    @Override
    public void registroProducto(Object producto) {
        productos.add(producto);
    }

    @Override
    public void registroCliente(Object cliente) {
        clientes.add(cliente);
    }

    @Override
    public void notificar(Object sender, String evento) {
        // Lógica para determinar qué componentes deben reaccionar
        if (evento.equals("ProductoAgregado")) {
            System.out.println("Se ha agregado un producto. Actualizar la vista de productos.");
        } else if (evento.equals("ClienteAgregado")) {
            System.out.println("Se ha agregado un cliente. Actualizar la vista de clientes.");
        }
    }
}
