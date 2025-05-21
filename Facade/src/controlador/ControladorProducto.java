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

    // === PATRÓN STATE ===
    private EstadoProducto estadoActual;

    private interface EstadoProducto {
        void ejecutar();
    }

    private void cambiarEstado(EstadoProducto nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.estadoActual.ejecutar();
    }

    // === MÉTODO CON STATE + FACADE ===
    public void mostrarProductos(JTable tablaTotalProductos) {
        cambiarEstado(() -> {
            configuracion.Conexion conexion = new configuracion.Conexion();
            ModeloProducto producto = new ModeloProducto();
            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("id");
            modelo.addColumn("nombresProd");
            modelo.addColumn("Precio");
            modelo.addColumn("Stock");

            tablaTotalProductos.setModel(modelo);

            String sql = "SELECT idproducto, nombre, precioProducto, stock FROM producto";

            try {
                Statement st = conexion.estableceConexion().createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    producto.setIdProducto(rs.getInt("idproducto"));
                    producto.setNombreProducto(rs.getString("nombre"));
                    producto.setPrecioProducto(rs.getDouble("precioProducto"));
                    producto.setStockProducto(rs.getInt("stock"));

                    modelo.addRow(new Object[]{
                        producto.getIdProducto(),
                        producto.getNombreProducto(),
                        producto.getPrecioProducto(),
                        producto.getStockProducto()
                    });
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e.toString());
            } finally {
                conexion.cerrarConexion();
            }
        });
    }

    // === MÉTODOS FACADE ===
    public void agregarProducto(JTextField nombre, JTextField precio, JTextField stock) {
        configuracion.Conexion conexion = new configuracion.Conexion();
        ModeloProducto producto = new ModeloProducto();

        String consulta = "INSERT INTO producto (nombre, precioProducto, stock) VALUES (?, ?, ?);";

        try {
            producto.setNombreProducto(nombre.getText());
            producto.setPrecioProducto(Double.parseDouble(precio.getText()));
            producto.setStockProducto(Integer.parseInt(stock.getText()));

            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, producto.getNombreProducto());
            cs.setDouble(2, producto.getPrecioProducto());
            cs.setInt(3, producto.getStockProducto());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.toString());
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void seleccionarProducto(JTable tabla, JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            id.setText(tabla.getValueAt(fila, 0).toString());
            nombre.setText(tabla.getValueAt(fila, 1).toString());
            precio.setText(tabla.getValueAt(fila, 2).toString());
            stock.setText(tabla.getValueAt(fila, 3).toString());
        }
    }

    public void modificarProducto(JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
        configuracion.Conexion conexion = new configuracion.Conexion();
        ModeloProducto producto = new ModeloProducto();

        String consulta = "UPDATE producto SET nombre = ?, precioProducto = ?, stock = ? WHERE idproducto = ?;";

        try {
            producto.setIdProducto(Integer.parseInt(id.getText()));
            producto.setNombreProducto(nombre.getText());
            producto.setPrecioProducto(Double.parseDouble(precio.getText()));
            producto.setStockProducto(Integer.parseInt(stock.getText()));

            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, producto.getNombreProducto());
            cs.setDouble(2, producto.getPrecioProducto());
            cs.setInt(3, producto.getStockProducto());
            cs.setInt(4, producto.getIdProducto());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Se modificó correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + e.toString());
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void eliminarProducto(JTextField id) {
        configuracion.Conexion conexion = new configuracion.Conexion();
        ModeloProducto producto = new ModeloProducto();

        String consulta = "DELETE FROM producto WHERE idproducto = ?;";

        try {
            producto.setIdProducto(Integer.parseInt(id.getText()));

            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, producto.getIdProducto());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se eliminó correctamente: " + e.toString());
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }
}
