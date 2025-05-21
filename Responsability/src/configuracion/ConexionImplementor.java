package configuracion;

import java.sql.Connection;

public interface ConexionImplementor {
    Connection estableceConexion();
    void cerrarConexion();
}