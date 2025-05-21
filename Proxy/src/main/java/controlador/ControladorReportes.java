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
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorReportes {
    
    private final boolean habilitarLogs;
    private final boolean mostrarMensajesError;

    // ✅ Constructor privado para usar Builder
    private ControladorReportes(Builder builder) {
        this.habilitarLogs = builder.habilitarLogs;
        this.mostrarMensajesError = builder.mostrarMensajesError;
    }

    public void BuscarFacturaMostrarDatosclientes(JTextField txtnumerofactura3, JLabel lblfactura, JLabel lblfechafactura, JLabel lblnombrecliente, JLabel lblappaterno, JLabel lblapmaterno) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void BucarFacturaDatosProductos(JTextField txtnumerofactura3, JTable tbproductos, JLabel lbliva, JLabel lbltotal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void MostrarTotalVentaPorFecha(JCalendar txtdesde, JCalendar txthasta, JTable tbtotal, JLabel lbltotal) {
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

        public ControladorReportes build() {
            return new ControladorReportes(this);
        }
    }

    // ✅ Método mejorado para buscar una factura y mostrar los datos del cliente
    public void buscarFacturaMostrarDatosClientes(JTextField numeroFacturaEncontrada, JLabel numeroFacturaEncontrado, 
                                                  JLabel fechaFacturaEncontrado, JLabel nombreCliente, 
                                                  JLabel appaterno, JLabel apmaterno) {
    
        if (numeroFacturaEncontrada == null || numeroFacturaEncontrada.getText().trim().isEmpty()) {
            mostrarError("Ingrese un número de factura válido.");
            return;
        }

        configuracion.Conexion objetoConexion = new configuracion.Conexion();

        try {
            String consulta = "SELECT factura.idfactura, factura.fechaFactura, cliente.nombres, cliente.appaterno, cliente.apmaterno "
                            + "FROM factura "
                            + "INNER JOIN cliente ON cliente.idcliente = factura.fkcliente "
                            + "WHERE factura.idfactura = ?;";

            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setInt(1, Integer.parseInt(numeroFacturaEncontrada.getText().trim()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                numeroFacturaEncontrado.setText(String.valueOf(rs.getInt("idfactura"))); 
                fechaFacturaEncontrado.setText(rs.getDate("fechaFactura").toString());
                nombreCliente.setText(rs.getString("nombres"));
                appaterno.setText(rs.getString("appaterno"));
                apmaterno.setText(rs.getString("apmaterno"));
            } else {
                mostrarError("No se encontró la factura.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Número de factura inválido.");
        } catch (Exception e) {
            manejarError("Error al buscar la factura", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método mejorado para buscar productos de una factura
    public void buscarFacturaDatosProductos(JTextField numeroFactura, JTable tablaProductos, JLabel IVA, JLabel total) {
        if (numeroFactura == null || numeroFactura.getText().trim().isEmpty()) {
            mostrarError("Ingrese un número de factura válido.");
            return;
        }

        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("N.Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("Subtotal");

        tablaProductos.setModel(modelo);

        try {
            String consulta = "SELECT producto.nombre, detalle.cantidad, detalle.precioVenta FROM detalle " +
                              "INNER JOIN factura ON factura.idfactura = detalle.fkfactura " +
                              "INNER JOIN producto ON producto.idproducto = detalle.fkproducto " +
                              "WHERE factura.idfactura = ?;";

            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setInt(1, Integer.parseInt(numeroFactura.getText().trim()));

            ResultSet rs = ps.executeQuery();

            double totalFactura = 0.0;
            double valorIVA = 0.10;
            DecimalFormat formato = new DecimalFormat("#.##");

            while (rs.next()) {
                String nombreProducto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precioVenta = rs.getDouble("precioVenta");
                double subtotal = cantidad * precioVenta;

                totalFactura += subtotal;
                modelo.addRow(new Object[]{nombreProducto, cantidad, precioVenta, subtotal});
            }

            double totalIVA = Double.parseDouble(formato.format(totalFactura * valorIVA));

            IVA.setText(String.valueOf(totalIVA));
            total.setText(String.valueOf(totalFactura));

        } catch (NumberFormatException e) {
            mostrarError("Número de factura inválido.");
        } catch (Exception e) {
            manejarError("Error al mostrar los productos", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Método mejorado para mostrar total de ventas por fecha
    public void mostrarTotalVentaPorFecha(JCalendar desde, JCalendar hasta, JTable tablaVentas, JLabel totalGeneral) {
        if (desde.getDate() == null || hasta.getDate() == null) {
            mostrarError("Seleccione ambas fechas antes de buscar.");
            return;
        }

        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("idFactura");
        modelo.addColumn("FechaFactura");
        modelo.addColumn("NProduct");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("SubTotal");

        tablaVentas.setModel(modelo);

        try {
            String consulta = "SELECT factura.idfactura, factura.fechaFactura, producto.nombre, detalle.cantidad, detalle.precioVenta FROM detalle " +
                              "INNER JOIN factura ON factura.idfactura = detalle.fkfactura " +
                              "INNER JOIN producto ON producto.idproducto = detalle.fkproducto " +
                              "WHERE factura.fechaFactura BETWEEN ? AND ?;";

            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);

            Date fechaDesdeSQL = new Date(desde.getDate().getTime());
            Date fechaHastaSQL = new Date(hasta.getDate().getTime());

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

                totalFactura += subtotal;

                modelo.addRow(new Object[]{idFactura, fechaFactura, nombreProducto, cantidad, precioVenta, subtotal});
            }

            totalGeneral.setText(String.valueOf(totalFactura));

        } catch (Exception e) {
            manejarError("Error al buscar los ingresos por fechas", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // ✅ Métodos de manejo de errores
    private void manejarError(String mensaje, Exception e) {
        if (habilitarLogs) {
            System.out.println("LOG: " + mensaje + " - " + e.toString());
        }
        if (mostrarMensajesError) {
            JOptionPane.showMessageDialog(null, mensaje + ": " + e.toString());
        }
    }

    private void mostrarError(String mensaje) {
        if (mostrarMensajesError) {
            JOptionPane.showMessageDialog(null, mensaje);
        }
    }
}
