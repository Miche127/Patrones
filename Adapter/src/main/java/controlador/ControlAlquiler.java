/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

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
import java.sql.Connection;

/**
 *
 * @author Samuel
 */
public class ControlAlquiler {

    Conexion conexion;

    public ControlAlquiler(DBAdapter adapter) {
        this.conexion = new Conexion(adapter);
    }

    public void BuscarProducto(JTextField nombreProducto, JTable tablaproducto) {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("PrecioProducto");
        modelo.addColumn("stock");

        tablaproducto.setModel(modelo);

        String consulta = "SELECT * FROM producto WHERE producto.nombre LIKE CONCAT('%', ? , '%');";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e.toString());
        }
    }

    public void SeleccionarProductoAlquiler(JTable tablaProducto, JTextField id, JTextField nombre, JTextField precioProducto, JTextField stock, JTextField precioFinal) {

        int fila = tablaProducto.getSelectedRow();

        try {

            if (fila >= 0) {

                id.setText(tablaProducto.getValueAt(fila, 0).toString());
                nombre.setText(tablaProducto.getValueAt(fila, 1).toString());
                precioProducto.setText(tablaProducto.getValueAt(fila, 2).toString());
                stock.setText(tablaProducto.getValueAt(fila, 3).toString());
                precioFinal.setText(tablaProducto.getValueAt(fila, 2).toString());

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "error al mostrar" + e.toString());

        }
    }

    public void BuscarCliente(JTextField nombreCliente, JTable tablaCliente) {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("ApPaterno");
        modelo.addColumn("ApMaterno");
        tablaCliente.setModel(modelo);

        String consulta = "SELECT * FROM cliente WHERE cliente.nombres LIKE CONCAT('%', ? , '%');";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setString(1, nombreCliente.getText());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idcliente"),
                    rs.getString("nombres"),
                    rs.getString("appaterno"),
                    rs.getString("apmaterno")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e.toString());
        }

    }

    public void SeleccionarClienteAlquiler(JTable tablaCliente, JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {

        int fila = tablaCliente.getSelectedRow();

        try {

            if (fila >= 0) {

                id.setText(tablaCliente.getValueAt(fila, 0).toString());
                nombres.setText(tablaCliente.getValueAt(fila, 1).toString());
                appaterno.setText(tablaCliente.getValueAt(fila, 2).toString());
                apmaterno.setText(tablaCliente.getValueAt(fila, 3).toString());

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "error al mostrar" + e.toString());

        }
    }

    public void pasarproductosVentas(JTable tablaResumen, JTextField idproducto, JTextField nombreProducto, JTextField precioProducto, JTextField cantidadVenta, JTextField stock) {

        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();

        int stockDisponible = Integer.parseInt(stock.getText());

        String idProducto = idproducto.getText();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            String idExistente = (String) modelo.getValueAt(i, 0);

            if (idExistente.equals(idproducto)) {
                JOptionPane.showMessageDialog(null, "El producto ya está registrado");
                return;
            }
        }

        String nProducto = nombreProducto.getText();
        double precioUnitario = Double.parseDouble(precioProducto.getText());
        int cantidad = Integer.parseInt(cantidadVenta.getText());

        if (cantidad > stockDisponible) {
            JOptionPane.showMessageDialog(null, "La cantidad de venta no puede ser mayor al atock disponible");
            return;
        }

        double subtotal = precioUnitario * cantidad;

        modelo.addRow(new Object[]{idProducto, nProducto, precioUnitario, cantidad, subtotal});
    }

    public void eliminarProductoSeleccionadoResumenVenta(JTable tablaResumen) {

        try {

            DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();

            int indiceSeleccionado = tablaResumen.getSelectedRow();

            if (indiceSeleccionado != -1) {
                modelo.removeRow(indiceSeleccionado);
            } else {
                JOptionPane.showMessageDialog(null, "seleccione una fila para eliminar");

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al seleccionar: " + e.toString());
        }

    }

    public void calcularTotalPagar(JTable tablaResumen, JLabel IVA, JLabel totalPagar) {

        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
        double totalSubtotal = 0;
        double iva = 0.18;
        double totaliva = 0;

        DecimalFormat formato = new DecimalFormat("#.##");
        for (int i = 0; i < modelo.getRowCount(); i++) {

            totalSubtotal = Double.parseDouble(formato.format(totalSubtotal + (double) modelo.getValueAt(i, 4)));
            totaliva = Double.parseDouble(formato.format(iva * totalSubtotal));
        }

        totalPagar.setText(String.valueOf(totalSubtotal));
        IVA.setText(String.valueOf(totaliva));

    }

    public void crearFactura(JTextField codcliente) {
        String consulta = "INSERT INTO factura (fechaFactura, fkcliente) VALUES (CURDATE(), ?);";

        try (Connection conn = conexion.estableceConexion(); 
             PreparedStatement ps = conn.prepareStatement(consulta)) {

            int idCliente = Integer.parseInt(codcliente.getText());
            ps.setInt(1, idCliente);
            ps.execute();

            JOptionPane.showMessageDialog(null, "La factura se creó correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear la factura: " + e.toString());
        }
    }

    public void realizarVenta(JTable tablaResumenVenta) {

        String consultaDetalle = "insert into detalle (fkfactura, fkproducto, cantidad, precioVenta) values ((SELECT MAX(idfactura)from factura),?,?,?);";
        String consultaStock = "update producto set producto.stock = stock - ? where idproducto = ?;";

        try {
            Connection conn = conexion.estableceConexion();

            PreparedStatement psDetalle = conn.prepareStatement(consultaDetalle);
            PreparedStatement psStock = conn.prepareStatement(consultaStock);

            int filas = tablaResumenVenta.getRowCount();
            for (int i = 0; i < filas; i++) {
                int idProducto = Integer.parseInt(tablaResumenVenta.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(tablaResumenVenta.getValueAt(i, 3).toString());
                double precioVenta = Double.parseDouble(tablaResumenVenta.getValueAt(i, 2).toString());

                psDetalle.setInt(1, idProducto);
                psDetalle.setInt(2, cantidad);
                psDetalle.setDouble(3, precioVenta);
                psDetalle.execute();

                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProducto);
                psStock.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Se realizó la Renta");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al realizar Renta" + e.toString());

        }
    }

    public void limpiarLuegoVenta(JTextField buscarCliente, JTable tablaCliente, JTextField buscarProducto, JTable tablaproducto,
            JTextField selectIdCliente, JTextField selectNombreCliente, JTextField AppaternoCliente,
            JTextField selectApmaternoCliente, JTextField selectIdProducto, JTextField selectecNombreProducto,
            JTextField selectecPrecioProducto, JTextField selectStockProducto, JTextField precioVenta,
            JTextField cantidadVenta, JTable tablaresumen, JLabel IVA, JLabel total) {

        buscarCliente.setText("");
        buscarCliente.requestFocus();
        DefaultTableModel modeloCliente = (DefaultTableModel) tablaCliente.getModel();
        modeloCliente.setRowCount(0);

        buscarProducto.setText("");
        DefaultTableModel modeloProducto = (DefaultTableModel) tablaproducto.getModel();
        modeloProducto.setRowCount(0);

        selectIdCliente.setText(" ");
        selectNombreCliente.setText(" ");
        selectApmaternoCliente.setText(" ");
        selectApmaternoCliente.setText(" ");

        selectIdProducto.setText(" ");
        selectecNombreProducto.setText(" ");
        selectecPrecioProducto.setText(" ");
        selectStockProducto.setText(" ");

        precioVenta.setText("");
        precioVenta.setEnabled(false);

        cantidadVenta.setText("");

        DefaultTableModel modeloResumenVenta = (DefaultTableModel) tablaresumen.getModel();
        modeloResumenVenta.setRowCount(0);

        IVA.setText("----");
        total.setText("----");
    }

    public void MostrarUltimaFactura(JLabel ultimaFactura) {
        String consulta = "SELECT MAX(idfactura) as UltimaFactura from factura;";
        try {
            Connection conn = conexion.estableceConexion();
            PreparedStatement ps = conn.prepareStatement(consulta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                ultimaFactura.setText(String.valueOf(rs.getInt("UltimaFactura")));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar ñla ultima factura" + e.toString());
        }

    }

}


