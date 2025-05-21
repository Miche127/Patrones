package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloProducto;
import configuracion.ConexionAbstraction;
import configuracion.Conexion;

public class ControlAlquiler {
    private final boolean habilitarLogs;
    private final boolean habilitarMensajesError;

    // ✅ Constructor privado para forzar el uso del Builder
    private ControlAlquiler(Builder builder) {
        this.habilitarLogs = builder.habilitarLogs;
        this.habilitarMensajesError = builder.habilitarMensajesError;
    }

    public void pasarproductosVentas(JTable tbresumenventa, JTextField txtSidproducto, JTextField txtSnombreproducto, JTextField txtSprecioproducto, JTextField txtcantidadventa, JTextField txtSstockproducto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void calcularTotalPagar(JTable tbresumenventa, JLabel lbliva, JLabel lbltotal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void SeleccionarClienteAlquiler(JTable tbclientes, JTextField txtSidcliente, JTextField txtSnombrecliente, JTextField txtSappaterno, JTextField txtSapmaterno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void BuscarCliente(JTextField txtbuscarcliente, JTable tbclientes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void SeleccionarProductoAlquiler(JTable tbproductos, JTextField txtSidproducto, JTextField txtSnombreproducto, JTextField txtSprecioproducto, JTextField txtSstockproducto, JTextField txtSprecioventa) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void eliminarProductoSeleccionadoResumenVenta(JTable tbresumenventa) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void crearFactura(JTextField txtSidcliente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void realizarVenta(JTable tbresumenventa) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void limpiarLuegoVenta(JTextField txtbuscarcliente, JTable tbclientes, JTextField txtbuscarproductos, JTable tbproductos, JTextField txtSidcliente, JTextField txtSnombrecliente, JTextField txtSappaterno, JTextField txtSapmaterno, JTextField txtSidproducto, JTextField txtSnombreproducto, JTextField txtSprecioproducto, JTextField txtSstockproducto, JTextField txtSprecioventa, JTextField txtcantidadventa, JTable tbresumenventa, JLabel lbliva, JLabel lbltotal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // ✅ Método que ahora usa el Bridge para la conexión a la base de datos
    public void BuscarProducto(JTextField nombreProducto, JTable tablaproducto) {
        // Se utiliza la abstracción de conexión, pasando la implementación concreta (ConexionMySQL)
        ConexionAbstraction objetoConexion = new ConexionAbstraction(new Conexion());

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("PrecioProducto");
        modelo.addColumn("stock");

        tablaproducto.setModel(modelo);

        try {
            String consulta = "SELECT * FROM producto WHERE producto.nombre LIKE CONCAT('%', ? , '%');";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setString(1, nombreProducto.getText());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                // Se crea una instancia base y se clona (usando el patrón Prototype)
                ModeloProducto productoBase = new ModeloProducto(
                    rs.getInt("idproducto"),
                    rs.getString("nombre"),
                    rs.getDouble("precioProducto"),
                    rs.getInt("stock")
                );
                ModeloProducto objetoProducto = productoBase.clone();
                modelo.addRow(new Object[]{
                    objetoProducto.getIdProducto(),
                    objetoProducto.getNombreProducto(),
                    objetoProducto.getPrecioProducto(),
                    objetoProducto.getStockProducto()
                });
            }
            tablaproducto.setModel(modelo);

        } catch (Exception e) {
            if (habilitarMensajesError) {
                JOptionPane.showMessageDialog(null, "Error al mostrar: " + e.toString());
            }
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para depuración con logs si están habilitados
    private void log(String mensaje) {
        if (habilitarLogs) {
            System.out.println("LOG: " + mensaje);
        }
    }

    // ✅ Clase interna Builder
    public static class Builder {
        private boolean habilitarLogs = false;
        private boolean habilitarMensajesError = true;

        public Builder habilitarLogs(boolean valor) {
            this.habilitarLogs = valor;
            return this;
        }

        public Builder habilitarMensajesError(boolean valor) {
            this.habilitarMensajesError = valor;
            return this;
        }

        public ControlAlquiler build() {
            return new ControlAlquiler(this);
        }
    }
}
