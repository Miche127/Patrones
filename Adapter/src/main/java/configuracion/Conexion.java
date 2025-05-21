/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

import adaptador.DBAdapter;

/**
 *
 * @author Samuel
 */
public class Conexion {
    
    DBAdapter adapter;
    Connection conectar = null;
    
    public Conexion(DBAdapter adapter) {
        this.adapter = adapter;
    }

    public Connection estableceConexion() {
        conectar = adapter.getConnection();
        return conectar;
    }

    public void cerrarConexion() {
        try {
            if (conectar != null && !conectar.isClosed()) {
                conectar.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.toString());
        }
    }
}