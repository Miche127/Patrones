/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Michel Mendez
 */

// Clase Memento para guardar el estado de un cliente
public class ClienteMemento {
    private final int idCliente;
    private final String nombres;
    private final String apPaterno;
    private final String apMaterno;

    public ClienteMemento(int id, String nombres, String apPaterno, String apMaterno) {
        this.idCliente = id;
        this.nombres = nombres;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }
    
    
}
