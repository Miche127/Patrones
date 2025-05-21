/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import configuracion.Conexion;

/**
 *
 * @author Michel Mendez
 */
public class ConexionFactory {
    
    public static Conexion crearConexion() {
        return new Conexion();
    }
    
}
