/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.Stack;
import modelo.ClienteMemento;

/**
 *
 * @author Michel Mendez
 */
public class ClienteAdministrador {

    private Stack<ClienteMemento> historial = new Stack<>();

    public void guardarMemento(ClienteMemento memento) {
        historial.push(memento);
    }

    public ClienteMemento restaurarMemento() {
        if (!historial.isEmpty()) {
            return historial.pop();
        } else {
            return null;
        }
    }
}
