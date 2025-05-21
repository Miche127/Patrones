/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import com.toedter.calendar.JCalendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import adaptador.DBAdapter;
import configuracion.Conexion;
import java.sql.*;

/**
 *
 * @author Michel Mendez
 */
public class ControladorReportes {

    private Conexion conexion;

    public ControladorReportes(DBAdapter adapter) {
        this.conexion = new Conexion(adapter);
    }

    public void BuscarFacturaMostrarDatosclientes(JTextField numeroFactura, JLabel lblFactura, JLabel lblFecha,
            JLabel lblNombre, JLabel lblApPaterno, JLabel lblApMaterno) {
        String consulta = "SELECT factura.idfactura, factura.fechaFactura, cliente.nombres, "
                + "cliente.appaterno, cliente.apmaterno FROM factura "
                + "INNER JOIN cliente ON cliente.idcliente = factura.fkcliente "
                + "WHERE factura.idfactura = ?";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setInt(1, Integer.parseInt(numeroFactura.getText().trim()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblFactura.setText(String.valueOf(rs.getInt("idfactura")));
                lblFecha.setText(rs.getString("fechaFactura"));
                lblNombre.setText(rs.getString("nombres"));
                lblApPaterno.setText(rs.getString("appaterno"));
                lblApMaterno.setText(rs.getString("apmaterno"));
            } else {
                JOptionPane.showMessageDialog(null, "Factura no encontrada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar factura: " + e.toString());
        }
    }

    public void BucarFacturaDatosProductos(JTextField numeroFactura, JTable tablaProductos,
            JLabel lblIVA, JLabel lblTotal) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Subtotal");
        tablaProductos.setModel(modelo);

        String consulta = "SELECT producto.nombre, detalle.cantidad, detalle.precioVenta "
                + "FROM detalle INNER JOIN factura ON factura.idfactura = detalle.fkfactura "
                + "INNER JOIN producto ON producto.idproducto = detalle.fkproducto "
                + "WHERE factura.idfactura = ?";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setInt(1, Integer.parseInt(numeroFactura.getText().trim()));
            ResultSet rs = ps.executeQuery();

            double totalFactura = 0.0;
            DecimalFormat formato = new DecimalFormat("#.##");

            while (rs.next()) {
                double subtotal = rs.getInt("cantidad") * rs.getDouble("precioVenta");
                totalFactura += subtotal;
                modelo.addRow(new Object[]{rs.getString("nombre"), rs.getInt("cantidad"),
                    rs.getDouble("precioVenta"), subtotal});
            }
            lblIVA.setText(String.valueOf(totalFactura * 0.18));
            lblTotal.setText(String.valueOf(totalFactura));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar productos: " + e.toString());
        }
    }

    public void MostrarTotalVentaPorFecha(JCalendar desde, JCalendar hasta, JTable tablaVentas, JLabel totalGeneral) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idFactura");
        modelo.addColumn("FechaFactura");
        modelo.addColumn("NProducto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("SubTotal");

        tablaVentas.setModel(modelo);

        String consulta = "SELECT factura.idfactura, factura.fechaFactura, producto.nombre, "
                + "detalle.cantidad, detalle.precioVenta FROM detalle "
                + "INNER JOIN factura ON factura.idfactura = detalle.fkfactura "
                + "INNER JOIN producto ON producto.idproducto = detalle.fkproducto "
                + "WHERE factura.fechaFactura BETWEEN ? AND ?";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

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
                String nombreProducto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precioVenta = rs.getDouble("precioVenta");

                double subtotal = cantidad * precioVenta;
                totalFactura = Double.parseDouble(formato.format(totalFactura + subtotal));

                modelo.addRow(new Object[]{idFactura, fechaFactura, nombreProducto, cantidad, precioVenta, subtotal});
            }

            totalGeneral.setText(String.valueOf(totalFactura));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar los ingresos por fechas: " + e.toString());
        }

        for (int column = 0; column < tablaVentas.getColumnCount(); column++) {
            Class<?> columnClass = tablaVentas.getColumnClass(column);
            tablaVentas.setDefaultEditor(columnClass, null);
        }
    }
}
