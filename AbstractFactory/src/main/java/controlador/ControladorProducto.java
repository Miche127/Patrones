/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package controlador;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloProducto;

public class ControladorProducto {
    
    private final boolean habilitarLogs;
    private final boolean mostrarMensajesError;

    // ✅ Constructor privado para usar Builder
    private ControladorProducto(Builder builder) {
        this.habilitarLogs = builder.habilitarLogs;
        this.mostrarMensajesError = builder.mostrarMensajesError;
    }

    public void ModificarProducto(JTextField txtidproducto, JTextField txtnombreproducto, JTextField txtprecioproducto, JTextField txtstockproducto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void MostrarProductos(JTable tbproductos) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void limpiarCamposproductos(JTextField txtidproducto, JTextField txtnombreproducto, JTextField txtprecioproducto, JTextField txtstockproducto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void EliminarProductos(JTextField txtidproducto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void Seleccionar(JTable tbproductos, JTextField txtidproducto, JTextField txtnombreproducto, JTextField txtprecioproducto, JTextField txtstockproducto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // ✅ Clase interna Builder
    public static class Builder {
        private boolean habilitarLogs = false;
        private boolean mostrarMensajesError = true;

        public Builder habilitarLogs(boolean valor) {
            this.habilitarLogs = valor;
            return this;
        }

        public Builder mostrarMensajesError(boolean valor) {
            this.mostrarMensajesError = valor;
            return this;
        }

        public ControladorProducto build() {
            return new ControladorProducto(this);
        }
    }

    // ✅ Método mejorado para mostrar productos
    public void mostrarProductos(JTable tablaTotalProductos) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        
        tablaTotalProductos.setModel(modelo);

        String sql = "SELECT idproducto, nombre, precioProducto, stock FROM producto;";

        try {
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idproducto"),
                    rs.getString("nombre"),
                    rs.getDouble("precioProducto"),
                    rs.getInt("stock")
                });
            }

            tablaTotalProductos.setModel(modelo);

        } catch (Exception e) {
            manejarError("Error al mostrar productos", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método mejorado para agregar productos
    public void agregarProducto(JTextField nombres, JTextField precioProducto, JTextField stock) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        String consulta = "INSERT INTO producto (nombre, precioProducto, stock) VALUES (?, ?, ?)";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, nombres.getText());
            cs.setDouble(2, Double.parseDouble(precioProducto.getText()));
            cs.setInt(3, Integer.parseInt(stock.getText()));
            cs.execute();

            JOptionPane.showMessageDialog(null, "Producto agregado correctamente");
        } catch (Exception e) {
            manejarError("Error al agregar producto", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para seleccionar un producto de la tabla
    public void seleccionarProducto(JTable tablaProductos, JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
        int fila = tablaProductos.getSelectedRow();

        if (fila >= 0) {
            id.setText(tablaProductos.getValueAt(fila, 0).toString());
            nombre.setText(tablaProductos.getValueAt(fila, 1).toString());
            precio.setText(tablaProductos.getValueAt(fila, 2).toString());
            stock.setText(tablaProductos.getValueAt(fila, 3).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto válido.");
        }
    }

    // ✅ Método para modificar un producto
    public void modificarProducto(JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        String consulta = "UPDATE producto SET nombre=?, precioProducto=?, stock=? WHERE idproducto=?";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, nombre.getText());
            cs.setDouble(2, Double.parseDouble(precio.getText()));
            cs.setInt(3, Integer.parseInt(stock.getText()));
            cs.setInt(4, Integer.parseInt(id.getText()));
            cs.execute();

            JOptionPane.showMessageDialog(null, "Producto modificado correctamente");
        } catch (Exception e) {
            manejarError("Error al modificar producto", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para eliminar un producto
    public void eliminarProducto(JTextField id) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        String consulta = "DELETE FROM producto WHERE idproducto=?";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(id.getText()));
            cs.execute();

            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
        } catch (Exception e) {
            manejarError("Error al eliminar producto", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para limpiar los campos
    public void limpiarCamposProductos(JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
        id.setText("");
        nombre.setText("");
        precio.setText("");
        stock.setText("");
    }

    // ✅ Método mejorado para manejo de errores
    private void manejarError(String mensaje, Exception e) {
        if (habilitarLogs) {
            System.out.println("LOG: " + mensaje + " - " + e.toString());
        }
        if (mostrarMensajesError) {
            JOptionPane.showMessageDialog(null, mensaje + ": " + e.toString());
        }
    }
}
