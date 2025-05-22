/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import PatronesCreacionales.ConexionSingleton;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;

/**
 *
 * @author Michel Mendez
 */

public class ControladorReportes {

    public void BuscarFacturaMostrarDatosclientes(JTextField numeroFactura, JLabel lblFactura,
            JLabel lblFecha, JLabel lblNombre,
            JLabel lblAppaterno, JLabel lblApmaterno) {
        if (numeroFactura.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un número de factura.");
            return;
        }

        try (Connection conn = ConexionSingleton.getInstancia().getConexion()) {
            String sql = "SELECT f.idfactura, f.fechaFactura, c.nombres, c.appaterno, c.apmaterno "
                    + "FROM factura f JOIN cliente c ON f.fkcliente = c.idcliente "
                    + "WHERE f.idfactura = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(numeroFactura.getText().trim()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblFactura.setText(rs.getString("idfactura"));
                lblFecha.setText(rs.getString("fechaFactura"));
                lblNombre.setText(rs.getString("nombres"));
                lblAppaterno.setText(rs.getString("appaterno"));
                lblApmaterno.setText(rs.getString("apmaterno"));
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la factura.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar factura: " + e.getMessage());
        }
    }

    public void BucarFacturaDatosProductos(JTextField numeroFactura, JTable tabla, JLabel lblIVA, JLabel lblTotal) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Producto", "Cantidad", "Precio", "Subtotal"}, 0);
        tabla.setModel(modelo);

        try (Connection conn = ConexionSingleton.getInstancia().getConexion()) {
            String sql = "SELECT p.nombre, d.cantidad, d.precioVenta "
                    + "FROM detalle d JOIN producto p ON d.fkproducto = p.idproducto "
                    + "WHERE d.fkfactura = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(numeroFactura.getText().trim()));
            ResultSet rs = ps.executeQuery();

            double total = 0;
            while (rs.next()) {
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precioVenta");
                double subtotal = cantidad * precio;
                total += subtotal;
                modelo.addRow(new Object[]{rs.getString("nombre"), cantidad, precio, subtotal});
            }

            DecimalFormat df = new DecimalFormat("#.##");
            lblTotal.setText(df.format(total));
            lblIVA.setText(df.format(total * 0.18));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e.getMessage());
        }
    }

    public void MostrarTotalVentaPorFecha(JCalendar desde, JCalendar hasta, JTable tablaVentas, JLabel totalGeneral) {

        //configuracion.Conexion objetoConexion = new configuracion.Conexion();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idFActura");
        modelo.addColumn("FechaFactura");
        modelo.addColumn("NProduct");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("SubTotal");

        tablaVentas.setModel(modelo);

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();

            String consulta = "SELECT factura.idfactura, factura.fechaFactura, producto.nombre, detalle.cantidad, detalle.precioVenta from detalle "
                    + "INNER JOIN factura ON factura.idfactura = detalle.fkfactura "
                    + "INNER JOIN producto ON producto.idproducto = detalle.fkproducto "
                    + "where factura.fechaFactura Between ? and ?;";

            PreparedStatement ps = conn.prepareStatement(consulta);

            java.util.Date fechaDesde = desde.getDate();
            java.util.Date fechaHasta = hasta.getDate();

            java.sql.Date fechaDesdeSQL = new java.sql.Date(fechaDesde.getTime());
            java.sql.Date fechaHastaSQL = new java.sql.Date(fechaHasta.getTime());

            ps.setDate(1, fechaDesdeSQL);
            ps.setDate(2, fechaHastaSQL);

            ResultSet rs = ps.executeQuery();

            double totalFactura = 0.0;

            DecimalFormat formato = new DecimalFormat("#.##");

            while (rs.next()) {

                int idFactura = rs.getInt("idfactura");
                Date fechaFactura = rs.getDate("fechaFactura");
                String nombreProducto = rs.getString("idFactura");
                int cantidad = rs.getInt("cantidad");
                double precioVenta = rs.getDouble("precioVenta");

                double subtotal = cantidad * precioVenta;

                totalFactura = Double.parseDouble(formato.format(totalFactura + subtotal));

                modelo.addRow(new Object[]{idFactura, fechaFactura, nombreProducto, cantidad, precioVenta, subtotal});
            }

            totalGeneral.setText(String.valueOf(totalFactura));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar los ingresos por fechas" + e.toString());

        } finally {
            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();
        }

        for (int column = 0; column < tablaVentas.getColumnCount(); column++) {

            Class<?> columClass = tablaVentas.getColumnClass(column);
            tablaVentas.setDefaultEditor(columClass, null);
        }

    }

}
