/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Samuel
 */
public class ControladorReportes {

    private static ControladorReportes instancia;

    private ControladorReportes() {}

    public static ControladorReportes getInstancia() {
        if (instancia == null) {
            instancia = new ControladorReportes();
        }
        return instancia;
    }

    
    public void BuscarFacturaMostrarDatosclientes(JTextField numeroFacturaEncontrada, JLabel numeroFacturaEncontrado, 
                                              JLabel fechaFacturaEncontrado, JLabel nombrecliente, 
                                              JLabel appaterno, JLabel apmaterno) {
    
    if (numeroFacturaEncontrada == null || numeroFacturaEncontrada.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingrese un número de factura válido.");
        return;
    }

    configuracion.Conexion objetoConexion = new configuracion.Conexion();

    try {
        String consulta = "SELECT factura.idfactura, factura.fechaFactura, cliente.nombres, cliente.appaterno, cliente.apmaterno "
                        + "FROM factura "
                        + "INNER JOIN cliente ON cliente.idcliente = factura.fkcliente "
                        + "WHERE factura.idfactura = ?;";

        PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
        ps.setInt(1, Integer.parseInt(numeroFacturaEncontrada.getText().trim())); // Convertir solo si hay valor

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            numeroFacturaEncontrado.setText(String.valueOf(rs.getInt("idfactura"))); 
            fechaFacturaEncontrado.setText(rs.getDate("fechaFactura").toString());
            nombrecliente.setText(rs.getString("nombres"));
            appaterno.setText(rs.getString("appaterno"));
            apmaterno.setText(rs.getString("apmaterno"));
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la factura.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Número de factura inválido.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al buscar la factura: " + e.toString());
    } finally {
        objetoConexion.cerrarConexion();
    }
}

    
    public void BucarFacturaDatosProductos(JTextField numeroFactura, JTable tablaProductos, JLabel IVA, JLabel total) {

    // Verificar si el JTextField es null
    if (numeroFactura == null) {
        System.out.println("El JTextField numeroFactura es NULL en el método.");
        JOptionPane.showMessageDialog(null, "Error: Campo de número de factura no inicializado.");
        return;
    }

    // Verificar si el JTextField está vacío
    if (numeroFactura.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingrese un número de factura válido.");
        return;
    }

    // Imprimir el valor del JTextField para depuración
    System.out.println("Valor de numeroFactura: " + numeroFactura.getText());

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
        ps.setInt(1, Integer.parseInt(numeroFactura.getText().trim())); // Convertir solo si hay valor

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
        JOptionPane.showMessageDialog(null, "Número de factura inválido.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar los productos: " + e.toString());
    } finally {
        objetoConexion.cerrarConexion();
    }
}


    
    public void MostrarTotalVentaPorFecha(JCalendar desde, JCalendar hasta, JTable tablaVentas, JLabel totalGeneral){
        
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idFActura");
        modelo.addColumn("FechaFactura");
        modelo.addColumn("NProduct");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("SubTotal");
        
        tablaVentas.setModel(modelo);
        
        try {
         
            
            String consulta = "SELECT factura.idfactura, factura.fechaFactura, producto.nombre, detalle.cantidad, detalle.precioVenta from detalle " +
                              "INNER JOIN factura ON factura.idfactura = detalle.fkfactura " +
                              "INNER JOIN producto ON producto.idproducto = detalle.fkproducto " +
                              "where factura.fechaFactura Between ? and ?;";
            
            
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            
            java.util.Date fechaDesde= desde.getDate();
            java.util.Date fechaHasta= hasta.getDate();
            
            
            java.sql.Date fechaDesdeSQL = new java.sql.Date(fechaDesde.getTime());
            java.sql.Date fechaHastaSQL = new java.sql.Date(fechaHasta.getTime());
            
            ps.setDate(1, fechaDesdeSQL);
            ps.setDate(2, fechaHastaSQL);


            
            ResultSet rs= ps.executeQuery();
            
            double totalFactura = 0.0;
            
            DecimalFormat formato = new DecimalFormat("#.##");
            
            while (rs.next()) {                
                
                int idFactura = rs.getInt("idfactura");
                Date fechaFactura = rs.getDate("fechaFactura");
                String nombreProducto = rs.getString("idFactura");
                int cantidad = rs.getInt("cantidad");
                double precioVenta = rs.getDouble("precioVenta");
                
                double subtotal = cantidad* precioVenta;
                
                totalFactura = Double.parseDouble(formato.format(totalFactura+subtotal));

                modelo.addRow(new Object[]{idFactura, fechaFactura, nombreProducto, cantidad, precioVenta, subtotal});
            }

          totalGeneral.setText(String.valueOf(totalFactura));
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar los ingresos por fechas"+e.toString());
            
        } finally {
           objetoConexion.cerrarConexion();
        }
        
        for (int column = 0; column < tablaVentas.getColumnCount(); column++) {


            Class<?> columClass = tablaVentas.getColumnClass(column);
            tablaVentas.setDefaultEditor(columClass, null);
        }
        
    }   
    
    
}
