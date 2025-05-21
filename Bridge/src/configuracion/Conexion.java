package configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion implements ConexionImplementor {
    Connection conectar = null;
    
    String usuario = "root";
    String contraseña = "patata12";
    String bd = "alquileres";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;
    
    @Override
    public Connection estableceConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null, "No se conectó correctamente a la BD: " + e.toString());
        }
        return conectar;
    }
    
    @Override
    public void cerrarConexion() {
        try {
            if(conectar != null && !conectar.isClosed()){
                conectar.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.toString());
        }
    }
}
