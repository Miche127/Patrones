/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import adaptador.DBAdapter;
import configuracion.Conexion;
import java.sql.*;

/**
 *
 * @author Samuel
 */
public class ControladorProducto {

    private Conexion conexion;

    public ControladorProducto(DBAdapter adapter) {
        this.conexion = new Conexion(adapter);
    }

    public void MostrarProductos(JTable tablaProductos) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        tablaProductos.setModel(modelo);

        String consulta = "SELECT idproducto, nombre, precioProducto, stock FROM producto";

        try (Connection conn = conexion.estableceConexion(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(consulta)) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idproducto"),
                    rs.getString("nombre"),
                    rs.getDouble("precioProducto"),
                    rs.getInt("stock")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e.toString());
        }
    }

    public void AgregarProducto(JTextField nombres, JTextField precioProducto, JTextField stock) {
        String consulta = "insert into producto (idproducto, nombre , precioProducto, stock) values (?,?,?);";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setString(1, nombres.getText());
            ps.setDouble(2, Double.parseDouble(precioProducto.getText()));
            ps.setInt(3, Integer.parseInt(stock.getText()));
            ps.execute();

            JOptionPane.showMessageDialog(null, "Producto agregado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar producto: " + e.toString());
        }

    }

    public void Seleccionar(JTable totalProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock) {

        int fila = totalProducto.getSelectedRow();
        try {

            if (fila >= 0) {

                id.setText(totalProducto.getValueAt(fila, 0).toString());
                nombres.setText(totalProducto.getValueAt(fila, 0).toString());
                precioProducto.setText(totalProducto.getValueAt(fila, 0).toString());
                stock.setText(totalProducto.getValueAt(fila, 0).toString());

            }
        } catch (Exception e) {

            JOptionPane.showConfirmDialog(null, "Error al seleccionar:" + e.toString());

        }

    }

    public void ModificarProducto(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock) {

        String consulta = "Update producto SET producto.nombre=?, producto.precioProducto =?, producto.stock = ? where producto.idproducto = ? ;";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setInt(1, Integer.parseInt(id.getText()));
            ps.setString(2, nombres.getText());
            ps.setString(3, precioProducto.getText());
            ps.setInt(4, Integer.parseInt(stock.getText()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto modificado correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al modificar" + e.toString());
        }

    }

    public void limpiarCamposproductos(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock) {

        id.setText("");
        nombres.setText("");
        precioProducto.setText("");
        stock.setText("");

    }

    public void EliminarProductos(JTextField id) {

        String consulta = "delete from producto where producto.idproducto=?;";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

            int idProducto = Integer.parseInt(id.getText());

            ps.setInt(1, idProducto);
            ps.execute();

            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No se eliminó correctamente" + e.toString());

        }
    }

}  