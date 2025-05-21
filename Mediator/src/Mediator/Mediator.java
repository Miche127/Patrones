/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mediator;

/**
 *
 * @author Betinsky
 */
public interface Mediator {
    void registroProducto(Object producto);
    void registroCliente(Object cliente);
    void notificar(Object sender, String evento);
}