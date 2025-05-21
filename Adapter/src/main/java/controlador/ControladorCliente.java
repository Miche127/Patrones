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
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Samuel
 */
public class ControladorCliente {

    private Conexion conexion;

    public ControladorCliente(DBAdapter adapter) {
        this.conexion = new Conexion(adapter);
    }

    public void MostrarClientes(JTable tablaClientes) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombres");
        modelo.addColumn("Ap. Paterno");
        modelo.addColumn("Ap. Materno");
        tablaClientes.setModel(modelo);

        String consulta = "SELECT idcliente, nombres, appaterno, apmaterno FROM cliente";

        try (Connection conn = conexion.estableceConexion(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(consulta)) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idcliente"),
                    rs.getString("nombres"),
                    rs.getString("appaterno"),
                    rs.getString("apmaterno")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar clientes: " + e.toString());
        }
    }

    public void AgregarCliente(JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        String consulta = "INSERT INTO cliente (nombres, appaterno, apmaterno) VALUES (?, ?, ?)";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setString(1, nombres.getText());
            ps.setString(2, appaterno.getText());
            ps.setString(3, apmaterno.getText());
            ps.execute();

            JOptionPane.showMessageDialog(null, "Cliente agregado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar cliente: " + e.toString());
        }
    }

    public void Seleccionar(JTable totalcliente, JTextField id, JTextField nombre, JTextField appaterno, JTextField apmaterno) {

        int fila = totalcliente.getSelectedRow();
        try {

            if (fila >= 0) {

                id.setText(totalcliente.getValueAt(fila, 0).toString());
                nombre.setText(totalcliente.getValueAt(fila, 0).toString());
                appaterno.setText(totalcliente.getValueAt(fila, 0).toString());
                apmaterno.setText(totalcliente.getValueAt(fila, 0).toString());

            }
        } catch (Exception e) {

            JOptionPane.showConfirmDialog(null, "Error al seleccionar:" + e.toString());

        }

    }

    public void ModificarCliente(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        String consulta = "UPDATE cliente SET nombres=?, appaterno=?, apmaterno=? WHERE idcliente=?";

        try (Connection conn = conexion.estableceConexion(); 
             PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setString(1, nombres.getText());
            ps.setString(2, appaterno.getText());
            ps.setString(3, apmaterno.getText());
            ps.setInt(4, Integer.parseInt(id.getText()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente modificado correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + e.toString());
        }
    }

    public void limpiarCamposClientes(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {

        id.setText("");
        nombres.setText("");
        appaterno.setText("");
        apmaterno.setText("");

    }

    public void EliminarClientes(JTextField id) {

        String consulta = "delete from cliente where cliente.idcliente=?;";

        try (
                Connection conn = conexion.estableceConexion(); 
                PreparedStatement ps = conn.prepareStatement(consulta)) {

            int idCliente = Integer.parseInt(id.getText());
            ps.setInt(1, idCliente);
            ps.execute();

            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No se eliminó correctamente" + e.toString());

        }
    }
}
