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
import modelo.ModeloCliente;

public class ControladorCliente {

    // === PATRÓN STATE ===
    private EstadoCliente estadoActual;

    private interface EstadoCliente {
        void ejecutar();
    }

    private void cambiarEstado(EstadoCliente nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.estadoActual.ejecutar();
    }

    // === MÉTODO CON STATE + FACADE ===
    public void mostrarClientes(JTable tablaTotalClientes) {
        cambiarEstado(() -> {
            configuracion.Conexion conexion = new configuracion.Conexion();
            ModeloCliente cliente = new ModeloCliente();
            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("id");
            modelo.addColumn("nombres");
            modelo.addColumn("appaterno");
            modelo.addColumn("apmaterno");

            tablaTotalClientes.setModel(modelo);

            String sql = "SELECT idcliente, nombres, appaterno, apmaterno FROM cliente";

            try {
                Statement st = conexion.estableceConexion().createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    cliente.setIdCliente(rs.getInt("idcliente"));
                    cliente.setNombres(rs.getString("nombres"));
                    cliente.setApPaterno(rs.getString("appaterno"));
                    cliente.setApMaterno(rs.getString("apmaterno"));

                    modelo.addRow(new Object[]{
                        cliente.getIdCliente(),
                        cliente.getNombres(),
                        cliente.getApPaterno(),
                        cliente.getApMaterno()
                    });
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al mostrar clientes: " + e.toString());
            } finally {
                conexion.cerrarConexion();
            }
        });
    }

    // === MÉTODOS FACADE ===
    public void agregarCliente(JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        configuracion.Conexion conexion = new configuracion.Conexion();
        ModeloCliente cliente = new ModeloCliente();
        String consulta = "INSERT INTO cliente (nombres, appaterno, apmaterno) VALUES (?, ?, ?);";

        try {
            cliente.setNombres(nombres.getText());
            cliente.setApPaterno(appaterno.getText());
            cliente.setApMaterno(apmaterno.getText());

            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, cliente.getNombres());
            cs.setString(2, cliente.getApPaterno());
            cs.setString(3, cliente.getApMaterno());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.toString());
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void seleccionarCliente(JTable tabla, JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            id.setText(tabla.getValueAt(fila, 0).toString());
            nombres.setText(tabla.getValueAt(fila, 1).toString());
            appaterno.setText(tabla.getValueAt(fila, 2).toString());
            apmaterno.setText(tabla.getValueAt(fila, 3).toString());
        }
    }

    public void modificarCliente(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        configuracion.Conexion conexion = new configuracion.Conexion();
        ModeloCliente cliente = new ModeloCliente();
        String consulta = "UPDATE cliente SET nombres = ?, appaterno = ?, apmaterno = ? WHERE idcliente = ?;";

        try {
            cliente.setIdCliente(Integer.parseInt(id.getText()));
            cliente.setNombres(nombres.getText());
            cliente.setApPaterno(appaterno.getText());
            cliente.setApMaterno(apmaterno.getText());

            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, cliente.getNombres());
            cs.setString(2, cliente.getApPaterno());
            cs.setString(3, cliente.getApMaterno());
            cs.setInt(4, cliente.getIdCliente());

            cs.execute();
            JOptionPane.showMessageDialog(null, "¡Se modificó correctamente!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + e.toString());
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void eliminarCliente(JTextField id) {
        configuracion.Conexion conexion = new configuracion.Conexion();
        ModeloCliente cliente = new ModeloCliente();
        String consulta = "DELETE FROM cliente WHERE idcliente = ?;";

        try {
            cliente.setIdCliente(Integer.parseInt(id.getText()));

            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, cliente.getIdCliente());

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
