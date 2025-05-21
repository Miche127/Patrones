/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import PatronesComportamiento.Prototype.Clonable;
/**
 *
 * @author Samuel
 */
public class ModeloCliente implements Clonable<ModeloCliente>{

    int idCliente;
    String nombres;
    String apPaterno;
    String apMaterno;
    
    public ModeloCliente() {
        
    }
    
    public ModeloCliente(int idCliente, String nombre, String appaterno, String apmaterno) {
        this.idCliente = idCliente;
        this.nombres = nombre;
        this.apPaterno = appaterno;
        this.apMaterno = apmaterno;
    }

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
    
    @Override
    public ModeloCliente clonar() {
        return new ModeloCliente(idCliente, nombres, apPaterno, apMaterno);
    }
}
