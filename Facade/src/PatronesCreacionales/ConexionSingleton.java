/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesCreacionales;

//PATRON SINGLETON
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Michel Mendez
 */
public class ConexionSingleton {

    private static ConexionSingleton instancia;
    private Connection conexion;

    private final String usuario = "root";
    private final String contraseña = "patata12"; 
    private final String bd = "alquileres";
    private final String ip = "localhost";
    private final String puerto = "3306";
    private final String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    // Constructor privado: solo se crea una vez
    private ConexionSingleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            conexion = DriverManager.getConnection(cadena, usuario, contraseña);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en conexión: " + e.toString());
        }
    }
    // Método estático para obtener la única instancia
    public static ConexionSingleton getInstancia() {
        if (instancia == null) {
            instancia = new ConexionSingleton();
        }
        return instancia;
    }
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(cadena, usuario, contraseña);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al reconectar: " + e.toString());
        }
        return conexion;
    }

    // Método para cerrar la conexión (opcional)
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + e.toString());
        }
    }
}