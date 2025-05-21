/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Factory.IControlador;
import configuracion.Conexion;
import modelo.ModeloCliente;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControladorCliente implements IControlador {

    @Override
    public void inicializar() {
        System.out.println("Controlador de Clientes Inicializado");
    }

    public void Mostrarclientes(JTable tablaTotalClientes) {
        Conexion objetoConexion = new Conexion();
        ModeloCliente objetocliente = new ModeloCliente();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellido Paterno");
        modelo.addColumn("Apellido Materno");

        tablaTotalClientes.setModel(modelo);

        String sql = "SELECT idcliente, nombres, appaterno, apmaterno FROM cliente";

        try {
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                objetocliente.setIdCliente(rs.getInt("idcliente"));
                objetocliente.setNombres(rs.getString("nombres"));
                objetocliente.setApPaterno(rs.getString("appaterno"));
                objetocliente.setApMaterno(rs.getString("apmaterno"));

                modelo.addRow(new Object[]{
                        objetocliente.getIdCliente(),
                        objetocliente.getNombres(),
                        objetocliente.getApPaterno(),
                        objetocliente.getApMaterno()
                });
            }
            tablaTotalClientes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar clientes: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void AgregarCliente(JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        Conexion objetoConexion = new Conexion();
        ModeloCliente objetoCliente = new ModeloCliente();

        String consulta = "INSERT INTO cliente (nombres, appaterno, apmaterno) VALUES (?, ?, ?)";

        try {
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Cliente agregado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar cliente: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void Seleccionar(JTable totalcliente, JTextField id, JTextField nombre, JTextField appaterno, JTextField apmaterno) {
        int fila = totalcliente.getSelectedRow();
        try {
            if (fila >= 0) {
                id.setText(totalcliente.getValueAt(fila, 0).toString());
                nombre.setText(totalcliente.getValueAt(fila, 1).toString());
                appaterno.setText(totalcliente.getValueAt(fila, 2).toString());
                apmaterno.setText(totalcliente.getValueAt(fila, 3).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar cliente: " + e.toString());
        }
    }

    public void ModificarCliente(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        Conexion objetoConexion = new Conexion();
        ModeloCliente objetoCliente = new ModeloCliente();

        String consulta = "UPDATE cliente SET nombres = ?, appaterno = ?, apmaterno = ? WHERE idcliente = ?";

        try {
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());
            cs.setInt(4, objetoCliente.getIdCliente());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Cliente modificado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void EliminarCliente(JTextField id) {
        Conexion objetoConexion = new Conexion();
        ModeloCliente objetoCliente = new ModeloCliente();

        String consulta = "DELETE FROM cliente WHERE idcliente = ?";

        try {
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, objetoCliente.getIdCliente());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void limpiarCampos(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        id.setText("");
        nombres.setText("");
        appaterno.setText("");
        apmaterno.setText("");
    }
}
