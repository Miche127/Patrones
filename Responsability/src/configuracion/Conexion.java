package configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion implements ConexionImplementor {
    // Atributo para almacenar la única instancia
    private static Conexion instancia;
    
    private Connection conectar = null;
    
    // Parámetros de conexión (puedes extraerlos luego a un archivo de configuración si lo deseas)
    private final String usuario = "root";
    private final String contraseña = "patata12";
    private final String bd = "alquileres";
    private final String ip = "localhost";
    private final String puerto = "3306";
    private final String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;
    
    // Constructor privado para evitar instanciación externa
    public Conexion() {
    }
    
    // Método público y estático para obtener la única instancia de Conexion
    public static Conexion getInstancia() {
        synchronized (Conexion.class) {
            if (instancia == null) {
                instancia = new Conexion();
            }
        }
        return instancia;
    }
    
    @Override
    public Connection estableceConexion() {
        try {
            // Si la conexión es nula o está cerrada, se crea una nueva
            if(conectar == null || conectar.isClosed()){
                Class.forName("com.mysql.jdbc.Driver");
                conectar = DriverManager.getConnection(cadena, usuario, contraseña);
            }
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
