/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ControlAlquiler {

    // ==== PATRÓN STATE ====
    private EstadoAlquiler estadoActual;

    private interface EstadoAlquiler {
        void ejecutar();
    }

    public void cambiarEstado(EstadoAlquiler nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.estadoActual.ejecutar();
    }

    // ==== MÉTODO CON STATE + FACADE ====
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

    // ==== MÉTODOS ADICIONALES PARA COMPLETAR LA FACHADA ====
    public void seleccionarProducto(JTable tablaProducto, JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
        int fila = tablaProducto.getSelectedRow();
        if (fila >= 0) {
            id.setText(tablaProducto.getValueAt(fila, 0).toString());
            nombre.setText(tablaProducto.getValueAt(fila, 1).toString());
            precio.setText(tablaProducto.getValueAt(fila, 2).toString());
            stock.setText(tablaProducto.getValueAt(fila, 3).toString());
        }
    }

    public void agregarProductoATablaVenta(JTable tablaVenta, JTextField id, JTextField nombre, JTextField precio, JTextField cantidad, JTextField stock) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        int stockDisponible = Integer.parseInt(stock.getText());
        int cantidadVenta = Integer.parseInt(cantidad.getText());

        if (cantidadVenta > stockDisponible) {
            JOptionPane.showMessageDialog(null, "Cantidad no disponible");
            return;
        }

        double precioUnitario = Double.parseDouble(precio.getText());
        double subtotal = precioUnitario * cantidadVenta;

        modelo.addRow(new Object[]{
            id.getText(), nombre.getText(), precioUnitario, cantidadVenta, subtotal
        });
    }

    public void calcularTotalYIVA(JTable tablaVenta, JLabel total, JLabel iva) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        double suma = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            suma += (double) modelo.getValueAt(i, 4);
        }
        double ivaTotal = suma * 0.16;
        total.setText(String.format("%.2f", suma));
        iva.setText(String.format("%.2f", ivaTotal));
    }

    public void limpiarFormulario(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    public void mostrarUltimaFactura(JLabel labelUltimaFactura) {
        configuracion.Conexion conexion = new configuracion.Conexion();
        try {
            String sql = "SELECT MAX(idfactura) AS ultima FROM factura;";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                labelUltimaFactura.setText(String.valueOf(rs.getInt("ultima")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener factura: " + e);
        } finally {
            conexion.cerrarConexion();
        }
    }
}
