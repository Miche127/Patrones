/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptador;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Michel Mendez
 */

public class MySQLAdapter implements DBAdapter {
    
    private final String usuario = "root";
    private final String contraseña = "25488271";
    private final String bd = "alquileres";
    private final String ip = "localhost";
    private final String puerto = "3306";
    
    private final String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    @Override
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(cadena, usuario, contraseña);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión a BD: " + e.toString());
            return null;
        }
    }
}
