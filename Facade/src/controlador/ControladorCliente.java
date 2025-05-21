/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import PatronesCreacionales.ConexionSingleton;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloCliente;

/**
 *
 * @author Samuel
 */
public class ControladorCliente {

    public void Mostrarclientes(JTable tablaTotalClientes) {

        //Connection conn = configuracion.Conexion.getInstancia().getConexion();
        modelo.ModeloCliente objetocliente = new modelo.ModeloCliente();

        DefaultTableModel modelo = new DefaultTableModel();

        String sql = "";

        modelo.addColumn("id");
        modelo.addColumn("nombres");
        modelo.addColumn("appaterno");
        modelo.addColumn("apmaterno");

        tablaTotalClientes.setModel(modelo);

        sql = "select cliente.idcliente, cliente.nombres, cliente.appaterno,cliente.apmaterno from cliente";

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                objetocliente.setIdCliente(rs.getInt("idcliente"));
                objetocliente.setNombres(rs.getString("nombres"));
                objetocliente.setApPaterno(rs.getString("appaterno"));
                objetocliente.setApMaterno(rs.getString("apmaterno"));

                modelo.addRow(new Object[]{objetocliente.getIdCliente(), objetocliente.getNombres(), objetocliente.getApPaterno(), objetocliente.getApMaterno()});

            }

            tablaTotalClientes.setModel(modelo);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios," + e.toString());

        } finally {

            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();
        }

    }

    public void AgregarCliente(JTextField nombres, JTextField appaterno, JTextField apmaterno) {

        //configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloCliente objetoCliente = new modelo.ModeloCliente();

        String consulta = "insert into cliente (nombres,appaterno,apmaterno) values (?,?,?);";

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();
            
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());

            CallableStatement cs = conn.prepareCall(consulta);
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.toString());

        } finally {
            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();

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

            JOptionPane.showConfirmDialog(null, "Error al seleccionar:" + e.toString());

        }

    }

    public void ModificarCliente(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {

        //configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloCliente objetoCliente = new modelo.ModeloCliente();

        String consulta = "Update cliente SET cliente.nombres=?, cliente.appaterno =?, cliente.apmaterno =? where cliente.idcliente =?;";

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();
            
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());

            CallableStatement cs = conn.prepareCall(consulta);

            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());
            cs.setInt(4, objetoCliente.getIdCliente());

            cs.execute();
            JOptionPane.showMessageDialog(null, "se modifico!");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al modificar" + e.toString());
        } finally {
            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

    public void limpiarCamposClientes(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {

        id.setText("");
        nombres.setText("");
        appaterno.setText("");
        apmaterno.setText("");

    }

    public void EliminarClientes(JTextField id) {

        //configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloCliente objetoCliente = new modelo.ModeloCliente();

        String consulta = "delete from cliente where cliente.idcliente=?;";

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();
            
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));

            CallableStatement cs = conn.prepareCall(consulta);

            cs.setInt(1, objetoCliente.getIdCliente());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No se eliminó correctamente" + e.toString());

        } finally {
            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

}
