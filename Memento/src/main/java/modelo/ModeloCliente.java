/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Michel Mendez
 */
// Clase ModeloCliente con Memento
public class ModeloCliente {

    int idCliente;
    String nombres;
    String apPaterno;
    String apMaterno;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public ClienteMemento saveToMemento() {
        return new ClienteMemento(idCliente, nombres, apPaterno, apMaterno);
    }

    public void restoreFromMemento(ClienteMemento memento) {
        this.idCliente = memento.getIdCliente();
        this.nombres = memento.getNombres();
        this.apPaterno = memento.getApPaterno();
        this.apMaterno = memento.getApMaterno();
    }
}
