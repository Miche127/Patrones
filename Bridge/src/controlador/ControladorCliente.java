/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorCliente {
    private JTable tablaTotalClientes; 
    private final boolean habilitarLogs;
    private final boolean mostrarMensajesError;

    // ✅ Constructor privado para forzar el uso del Builder
    private ControladorCliente(Builder builder) {
        this.habilitarLogs = builder.habilitarLogs;
        this.mostrarMensajesError = builder.mostrarMensajesError;
    }
   public void setTablaTotalClientes(JTable tabla) {
        this.tablaTotalClientes = tabla;
    }  
    public void limpiarCamposClientes(JTextField idcliente, JTextField txtnombrecliente, JTextField txtappaterno, JTextField txtapmaterno) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void modificarCliente(JTextField idcliente, JTextField txtnombrecliente, JTextField txtappaterno, JTextField txtapmaterno) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void eliminarClientes(JTextField idcliente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void seleccionar(JTable tbclientes, JTextField idcliente, JTextField txtnombrecliente, JTextField txtappaterno, JTextField txtapmaterno) {
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

        public ControladorCliente build() {
            return new ControladorCliente(this);
        }
         
        
    }

    // ✅ Método para mostrar clientes
    public void mostrarClientes(JTable tablaTotalClientes) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        Connection conn = objetoConexion.estableceConexion();

        if (conn == null) {
            mostrarError("No se pudo conectar a la base de datos", null);
            return;
        }

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("nombres");
        modelo.addColumn("appaterno");
        modelo.addColumn("apmaterno");

        tablaTotalClientes.setModel(modelo);

        String sql = "SELECT idcliente, nombres, appaterno, apmaterno FROM cliente";

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idcliente"),
                    rs.getString("nombres"),
                    rs.getString("appaterno"),
                    rs.getString("apmaterno")
                });
            }

            tablaTotalClientes.setModel(modelo);
            log("Clientes cargados correctamente.");

        } catch (Exception e) {
            mostrarError("Error al mostrar clientes", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método para agregar cliente
    public void agregarCliente(JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        Connection conn = objetoConexion.estableceConexion();

        if (conn == null) {
            mostrarError("No se pudo conectar a la base de datos", null);
            return;
        }

        String consulta = "INSERT INTO cliente (nombres, appaterno, apmaterno) VALUES (?, ?, ?)";

        try (CallableStatement cs = conn.prepareCall(consulta)) {
            cs.setString(1, nombres.getText().trim());
            cs.setString(2, appaterno.getText().trim());
            cs.setString(3, apmaterno.getText().trim());
            cs.execute();

            JOptionPane.showMessageDialog(null, "✅ Cliente guardado correctamente");
            log("Cliente agregado: " + nombres.getText());

        } catch (Exception e) {
            mostrarError("Error al guardar cliente", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Métodos auxiliares de logs y errores
    private void log(String mensaje) {
        if (habilitarLogs) {
            System.out.println("[LOG] " + mensaje);
        }
    }

    private void mostrarError(String mensaje, Exception e) {
        if (habilitarLogs && e != null) {
            System.out.println("[ERROR] " + mensaje + " - " + e.toString());
        }
        if (mostrarMensajesError) {
            JOptionPane.showMessageDialog(null, mensaje + (e != null ? ": " + e.toString() : ""));
        }
    }
}
