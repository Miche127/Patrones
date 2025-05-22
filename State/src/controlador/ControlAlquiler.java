/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ControlAlquiler {

    private EstadoAlquiler estadoActual;

    // Interfaz interna del patrón State
    private interface EstadoAlquiler {
        void ejecutar();
    }

    // Método público para cambiar de estado y ejecutarlo
    public void cambiarEstado(EstadoAlquiler nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.estadoActual.ejecutar();
    }

    // Método específico para buscar producto usando el patrón State
    public void buscarProducto(JTextField nombreProducto, JTable tablaProducto) {
        cambiarEstado(() -> {
            configuracion.Conexion conexion = new configuracion.Conexion();
            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("id");
            modelo.addColumn("Nombre");
            modelo.addColumn("PrecioProducto");
            modelo.addColumn("stock");

            tablaProducto.setModel(modelo);

            try {
                String consulta = "SELECT * FROM producto WHERE producto.nombre LIKE CONCAT('%', ? , '%');";
                PreparedStatement ps = conexion.estableceConexion().prepareStatement(consulta);
                ps.setString(1, nombreProducto.getText());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("idproducto"),
                        rs.getString("nombre"),
                        rs.getDouble("precioProducto"),
                        rs.getInt("stock")
                    });
                }

                tablaProducto.setModel(modelo);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e);
            } finally {
                conexion.cerrarConexion();
            }

            for (int i = 0; i < tablaProducto.getColumnCount(); i++) {
                tablaProducto.setDefaultEditor(tablaProducto.getColumnClass(i), null);
            }
        });
    }
}
