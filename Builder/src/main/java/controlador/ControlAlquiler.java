/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControlAlquiler {
    private final boolean habilitarLogs;
    private final boolean habilitarMensajesError;

    // ✅ Constructor privado para forzar el uso del Builder
    private ControlAlquiler(Builder builder) {
        this.habilitarLogs = builder.habilitarLogs;
        this.habilitarMensajesError = builder.habilitarMensajesError;
    }

    public void pasarproductosVentas(JTable tbresumenventa, JTextField txtSidproducto, JTextField txtSnombreproducto, JTextField txtSprecioproducto, JTextField txtcantidadventa, JTextField txtSstockproducto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void calcularTotalPagar(JTable tbresumenventa, JLabel lbliva, JLabel lbltotal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void SeleccionarClienteAlquiler(JTable tbclientes, JTextField txtSidcliente, JTextField txtSnombrecliente, JTextField txtSappaterno, JTextField txtSapmaterno) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void BuscarCliente(JTextField txtbuscarcliente, JTable tbclientes) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void SeleccionarProductoAlquiler(JTable tbproductos, JTextField txtSidproducto, JTextField txtSnombreproducto, JTextField txtSprecioproducto, JTextField txtSstockproducto, JTextField txtSprecioventa) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void eliminarProductoSeleccionadoResumenVenta(JTable tbresumenventa) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void crearFactura(JTextField txtSidcliente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void realizarVenta(JTable tbresumenventa) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void limpiarLuegoVenta(JTextField txtbuscarcliente, JTable tbclientes, JTextField txtbuscarproductos, JTable tbproductos, JTextField txtSidcliente, JTextField txtSnombrecliente, JTextField txtSappaterno, JTextField txtSapmaterno, JTextField txtSidproducto, JTextField txtSnombreproducto, JTextField txtSprecioproducto, JTextField txtSstockproducto, JTextField txtSprecioventa, JTextField txtcantidadventa, JTable tbresumenventa, JLabel lbliva, JLabel lbltotal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    // ✅ Ejemplo de un método mejorado con Builder
    public void BuscarProducto(JTextField nombreProducto, JTable tablaproducto) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();
        
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
                objetoProducto.setIdProducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                objetoProducto.setStockProducto(rs.getInt("stock"));
                modelo.addRow(new Object[]{objetoProducto.getIdProducto(), objetoProducto.getNombreProducto(), objetoProducto.getPrecioProducto(), objetoProducto.getStockProducto()});
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
}
