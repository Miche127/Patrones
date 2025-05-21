package configuracion;

import java.sql.Connection;

public class ConexionAbstraction {
    protected ConexionImplementor implementor;
    
    public ConexionAbstraction(ConexionImplementor implementor) {
        this.implementor = implementor;
    }
    
    public Connection estableceConexion() {
        return implementor.estableceConexion();
    }
    
    public void cerrarConexion() {
        implementor.cerrarConexion();
    }
}
