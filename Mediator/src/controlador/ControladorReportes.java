/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Mediator.ConcreteMediator;
import Mediator.Mediator;
import com.toedter.calendar.JCalendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controlador.chain.ConsoleErrorHandler;
import controlador.chain.ErrorHandler;
import controlador.chain.GuiErrorHandler;

public class ControladorReportes {

    private final boolean habilitarLogs;
    private final boolean mostrarMensajesError;
    
    // Instancia del Mediator para notificaciones
    private final Mediator mediator;
    // Cadena de responsabilidad para el manejo de errores
    private final ErrorHandler errorHandler;

    // Constructor privado que se utiliza a través del Builder
    private ControladorReportes(Builder builder) {
        this.habilitarLogs = builder.habilitarLogs;
        this.mostrarMensajesError = builder.mostrarMensajesError;
        // Se inicializa el Mediator
        this.mediator = new ConcreteMediator();
        // Se configura la cadena de manejo de errores
        this.errorHandler = new ConsoleErrorHandler();
        this.errorHandler.setSiguiente(new GuiErrorHandler());
    }
    
    // Clase interna Builder
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
    
    // Método para buscar una factura y mostrar los datos del cliente
    public void buscarFacturaMostrarDatosClientes(JTextField numeroFacturaEncontrada, JLabel numeroFacturaEncontrado, 
                                                  JLabel fechaFacturaEncontrado, JLabel nombreCliente, 
                                                  JLabel appaterno, JLabel apmaterno) {
        if (numeroFacturaEncontrada == null || numeroFacturaEncontrada.getText().trim().isEmpty()) {
            errorHandler.handleError("Ingrese un número de factura válido.", null);
            return;
        }
        
        configuracion.Conexion objetoConexion = configuracion.Conexion.getInstancia();
        
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
                
                mediator.notificar(this, "FacturaEncontrada");
            } else {
                errorHandler.handleError("No se encontró la factura.", null);
            }
        } catch (NumberFormatException e) {
            errorHandler.handleError("Número de factura inválido.", e);
        } catch (Exception e) {
            errorHandler.handleError("Error al buscar la factura", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
    
    // Método para buscar productos asociados a una factura
    public void buscarFacturaDatosProductos(JTextField numeroFactura, JTable tablaProductos, JLabel IVA, JLabel total) {
        if (numeroFactura == null || numeroFactura.getText().trim().isEmpty()) {
            errorHandler.handleError("Ingrese un número de factura válido.", null);
            return;
        }
        
        configuracion.Conexion objetoConexion = configuracion.Conexion.getInstancia();
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
            
            mediator.notificar(this, "ProductosFacturaEncontrados");
            
        } catch (NumberFormatException e) {
            errorHandler.handleError("Número de factura inválido.", e);
        } catch (Exception e) {
            errorHandler.handleError("Error al mostrar los productos", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
    
    // Método para mostrar el total de ventas en un rango de fechas
    public void mostrarTotalVentaPorFecha(JCalendar desde, JCalendar hasta, JTable tablaVentas, JLabel totalGeneral) {
        if (desde.getDate() == null || hasta.getDate() == null) {
            errorHandler.handleError("Seleccione ambas fechas antes de buscar.", null);
            return;
        }
        
        configuracion.Conexion objetoConexion = configuracion.Conexion.getInstancia();
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
            
            mediator.notificar(this, "TotalVentasCalculado");
            
        } catch (Exception e) {
            errorHandler.handleError("Error al buscar los ingresos por fechas", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
    
    // Método adicional: buscarComprobante
    public void buscarComprobante(JTextField txtNumeroComprobante, JLabel lblComprobante, JLabel lblFecha, JLabel lblCliente) {
        if (txtNumeroComprobante == null || txtNumeroComprobante.getText().trim().isEmpty()) {
            errorHandler.handleError("Ingrese un número de comprobante válido.", null);
            return;
        }
        
        configuracion.Conexion objetoConexion = configuracion.Conexion.getInstancia();
        
        try {
            // Consulta de ejemplo. Adapta nombres de tabla y campos según tu base de datos.
            String consulta = "SELECT comprobante.idcomprobante, comprobante.fecha, cliente.nombres " +
                              "FROM comprobante " +
                              "INNER JOIN cliente ON cliente.idcliente = comprobante.fkcliente " +
                              "WHERE comprobante.idcomprobante = ?;";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setInt(1, Integer.parseInt(txtNumeroComprobante.getText().trim()));
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblComprobante.setText(String.valueOf(rs.getInt("idcomprobante")));
                lblFecha.setText(rs.getDate("fecha").toString());
                lblCliente.setText(rs.getString("nombres"));
                
                mediator.notificar(this, "ComprobanteEncontrado");
            } else {
                errorHandler.handleError("No se encontró el comprobante.", null);
            }
        } catch (NumberFormatException e) {
            errorHandler.handleError("Número de comprobante inválido.", e);
        } catch (Exception e) {
            errorHandler.handleError("Error al buscar el comprobante", e);
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
}
