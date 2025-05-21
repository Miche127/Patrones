/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package controlador;

import Mediator.Mediator;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorProducto {

    private final boolean habilitarLogs;
    private final boolean mostrarMensajesError;

    // 🔹 Mediator agregado
    private final Mediator mediator;

    // Constructor privado usando Builder
    private ControladorProducto(Builder builder) {
        this.habilitarLogs = builder.habilitarLogs;
        this.mostrarMensajesError = builder.mostrarMensajesError;
        this.mediator = builder.mediator;
    }

    public static class Builder {
        private boolean habilitarLogs = false;
        private boolean mostrarMensajesError = true;
        private Mediator mediator;

        public Builder habilitarLogs(boolean valor) {
            this.habilitarLogs = valor;
            return this;
        }

        public Builder mostrarMensajesError(boolean valor) {
            this.mostrarMensajesError = valor;
            return this;
        }

        // 🔹 Método para asignar el Mediator
        public Builder mediator(Mediator mediator) {
            this.mediator = mediator;
            return this;
        }

        public ControladorProducto build() {
            return new ControladorProducto(this);
        }
    }

    // ✅ Método para mostrar productos con Mediator
    public void mostrarProductos(JTable tablaTotalProductos) {
        configuracion.Conexion objetoConexion = configuracion.Conexion.getInstancia();
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
            mediator.notificar(this, "ProductosMostrados"); // 🔹 Notificación Mediator

        } catch (Exception e) {
            manejarError("Error al mostrar productos", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para agregar productos con Mediator
    public void agregarProducto(JTextField nombres, JTextField precioProducto, JTextField stock) {
        configuracion.Conexion objetoConexion = configuracion.Conexion.getInstancia();
        String consulta = "INSERT INTO producto (nombre, precioProducto, stock) VALUES (?, ?, ?)";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, nombres.getText());
            cs.setDouble(2, Double.parseDouble(precioProducto.getText()));
            cs.setInt(3, Integer.parseInt(stock.getText()));
            cs.execute();

            JOptionPane.showMessageDialog(null, "Producto agregado correctamente");
            mediator.notificar(this, "ProductoAgregado"); // 🔹 Notificación Mediator

        } catch (Exception e) {
            manejarError("Error al agregar producto", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para modificar productos con Mediator
    public void modificarProducto(JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
        configuracion.Conexion objetoConexion = configuracion.Conexion.getInstancia();
        String consulta = "UPDATE producto SET nombre=?, precioProducto=?, stock=? WHERE idproducto=?";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, nombre.getText());
            cs.setDouble(2, Double.parseDouble(precio.getText()));
            cs.setInt(3, Integer.parseInt(stock.getText()));
            cs.setInt(4, Integer.parseInt(id.getText()));
            cs.execute();

            JOptionPane.showMessageDialog(null, "Producto modificado correctamente");
            mediator.notificar(this, "ProductoModificado"); // 🔹 Notificación Mediator

        } catch (Exception e) {
            manejarError("Error al modificar producto", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para eliminar productos con Mediator
    public void eliminarProducto(JTextField id) {
        configuracion.Conexion objetoConexion = configuracion.Conexion.getInstancia();
        String consulta = "DELETE FROM producto WHERE idproducto=?";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(id.getText()));
            cs.execute();

            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
            mediator.notificar(this, "ProductoEliminado"); // 🔹 Notificación Mediator

        } catch (Exception e) {
            manejarError("Error al eliminar producto", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para seleccionar productos de una tabla
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

    // ✅ Método para limpiar los campos de producto
    public void limpiarCamposProductos(JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
        id.setText("");
        nombre.setText("");
        precio.setText("");
        stock.setText("");
    }

    // ✅ Método para manejar errores
    private void manejarError(String mensaje, Exception e) {
        if (habilitarLogs) {
            System.out.println("LOG: " + mensaje + " - " + e.toString());
        }
        if (mostrarMensajesError) {
            JOptionPane.showMessageDialog(null, mensaje + (e != null ? ": " + e.toString() : ""));
        }
    }
}
