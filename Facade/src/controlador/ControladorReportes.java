/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

//import com.toedter.calendar.JCalendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorReportes {

    // === PATRÓN STATE ===
    private EstadoReporte estadoActual;

    private interface EstadoReporte {
        void ejecutar();
    }

    private void cambiarEstado(EstadoReporte nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.estadoActual.ejecutar();
    }

    // === MÉTODO CON STATE + FACADE ===
    public void buscarFacturaConDatosCliente(JTextField numeroFacturaInput, JLabel idFactura, JLabel fecha, JLabel nombre, JLabel apPaterno, JLabel apMaterno) {
        cambiarEstado(() -> {
            if (numeroFacturaInput == null || numeroFacturaInput.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un número de factura válido.");
                return;
            }

            configuracion.Conexion conexion = new configuracion.Conexion();

            try {
                String sql = """
                    SELECT factura.idfactura, factura.fechaFactura, cliente.nombres, cliente.appaterno, cliente.apmaterno
                    FROM factura
                    INNER JOIN cliente ON cliente.idcliente = factura.fkcliente
                    WHERE factura.idfactura = ?;
                    """;

                PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(numeroFacturaInput.getText().trim()));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    idFactura.setText(String.valueOf(rs.getInt("idfactura")));
                    fecha.setText(rs.getDate("fechaFactura").toString());
                    nombre.setText(rs.getString("nombres"));
                    apPaterno.setText(rs.getString("appaterno"));
                    apMaterno.setText(rs.getString("apmaterno"));
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la factura.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número de factura inválido.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al buscar la factura: " + e.toString());
            } finally {
                conexion.cerrarConexion();
            }
        });
    }

    // === MÉTODOS FACADE ===
    public void buscarProductosPorFactura(JTextField numeroFactura, JTable tablaProductos, JLabel iva, JLabel total) {
        if (numeroFactura == null || numeroFactura.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un número de factura válido.");
            return;
        }

        configuracion.Conexion conexion = new configuracion.Conexion();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("N.Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("Subtotal");

        tablaProductos.setModel(modelo);

        try {
            String sql = """
                SELECT producto.nombre, detalle.cantidad, detalle.precioVenta
                FROM detalle
                INNER JOIN factura ON factura.idfactura = detalle.fkfactura
                INNER JOIN producto ON producto.idproducto = detalle.fkproducto
                WHERE factura.idfactura = ?;
                """;

            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(numeroFactura.getText().trim()));
            ResultSet rs = ps.executeQuery();

            double totalFactura = 0.0;
            double ivaPorcentaje = 0.10;
            DecimalFormat formato = new DecimalFormat("#.##");

            while (rs.next()) {
                String nombreProducto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precioVenta = rs.getDouble("precioVenta");
                double subtotal = cantidad * precioVenta;

                totalFactura += subtotal;
                modelo.addRow(new Object[]{nombreProducto, cantidad, precioVenta, subtotal});
            }

            double totalIVA = Double.parseDouble(formato.format(totalFactura * ivaPorcentaje));
            iva.setText(String.valueOf(totalIVA));
            total.setText(String.valueOf(totalFactura));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los productos: " + e.toString());
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void mostrarVentasPorFecha(JCalendar desde, JCalendar hasta, JTable tablaVentas, JLabel totalGeneral) {
        configuracion.Conexion conexion = new configuracion.Conexion();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idFactura");
        modelo.addColumn("FechaFactura");
        modelo.addColumn("N.Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("SubTotal");

        tablaVentas.setModel(modelo);

        try {
            String sql = """
                SELECT factura.idfactura, factura.fechaFactura, producto.nombre, detalle.cantidad, detalle.precioVenta
                FROM detalle
                INNER JOIN factura ON factura.idfactura = detalle.fkfactura
                INNER JOIN producto ON producto.idproducto = detalle.fkproducto
                WHERE factura.fechaFactura BETWEEN ? AND ?;
                """;

            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);

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

                totalFactura += subtotal;
                modelo.addRow(new Object[]{idFactura, fechaFactura, nombreProducto, cantidad, precioVenta, subtotal});
            }

            totalGeneral.setText(String.valueOf(formato.format(totalFactura)));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar los ingresos por fechas: " + e.toString());
        } finally {
            conexion.cerrarConexion();
        }

        for (int column = 0; column < tablaVentas.getColumnCount(); column++) {
            tablaVentas.setDefaultEditor(tablaVentas.getColumnClass(column), null);
        }
    }
}
