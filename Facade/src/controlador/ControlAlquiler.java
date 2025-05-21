/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import PatronesCreacionales.ConexionSingleton;
import modelo.ModeloCliente;
import modelo.ModeloProducto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;

/**
 *
 * @author Michel Mendez
 */

public class ControlAlquiler {

    public void BuscarProducto(JTextField nombreProducto, JTable tablaproducto) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"id", "Nombre", "Precio", "Stock"}, 0);
        tablaproducto.setModel(modelo);

        try (Connection conn = ConexionSingleton.getInstancia().getConexion()) {
            String consulta = "SELECT * FROM producto WHERE nombre LIKE CONCAT('%', ?, '%')";
            PreparedStatement ps = conn.prepareStatement(consulta);
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
            JOptionPane.showMessageDialog(null, "Error al buscar productos: " + e.getMessage());
        }

        bloquearEdicionTabla(tablaproducto);
    }

    public void SeleccionarProductoAlquiler(JTable tabla, JTextField id, JTextField nombre,
                                            JTextField precio, JTextField stock, JTextField precioFinal) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            id.setText(tabla.getValueAt(fila, 0).toString());
            nombre.setText(tabla.getValueAt(fila, 1).toString());
            precio.setText(tabla.getValueAt(fila, 2).toString());
            stock.setText(tabla.getValueAt(fila, 3).toString());
            precioFinal.setText(tabla.getValueAt(fila, 2).toString());
        }
    }

    public void BuscarCliente(JTextField nombre, JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"id", "Nombres", "ApPaterno", "ApMaterno"}, 0);
        tabla.setModel(modelo);

        try (Connection conn = ConexionSingleton.getInstancia().getConexion()) {
            String consulta = "SELECT * FROM cliente WHERE nombres LIKE CONCAT('%', ?, '%')";
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, nombre.getText());

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
            JOptionPane.showMessageDialog(null, "Error al buscar clientes: " + e.getMessage());
        }

        bloquearEdicionTabla(tabla);
    }

    public void SeleccionarClienteAlquiler(JTable tabla, JTextField id, JTextField nombre,
                                           JTextField appaterno, JTextField apmaterno) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            id.setText(tabla.getValueAt(fila, 0).toString());
            nombre.setText(tabla.getValueAt(fila, 1).toString());
            appaterno.setText(tabla.getValueAt(fila, 2).toString());
            apmaterno.setText(tabla.getValueAt(fila, 3).toString());
        }
    }

    public void pasarproductosVentas(JTable resumen, JTextField id, JTextField nombre,
                                     JTextField precio, JTextField cantidad, JTextField stock) {
        try {
            int cantidadVenta = Integer.parseInt(cantidad.getText());
            int stockDisponible = Integer.parseInt(stock.getText());

            if (cantidadVenta > stockDisponible) {
                JOptionPane.showMessageDialog(null, "Cantidad mayor al stock disponible.");
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) resumen.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (modelo.getValueAt(i, 0).toString().equals(id.getText())) {
                    JOptionPane.showMessageDialog(null, "Producto ya añadido.");
                    return;
                }
            }

            double precioUnitario = Double.parseDouble(precio.getText());
            double subtotal = precioUnitario * cantidadVenta;

            modelo.addRow(new Object[]{id.getText(), nombre.getText(), precioUnitario, cantidadVenta, subtotal});

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad o precio inválido.");
        }
    }

    public void eliminarProductoSeleccionadoResumenVenta(JTable resumen) {
        DefaultTableModel modelo = (DefaultTableModel) resumen.getModel();
        int fila = resumen.getSelectedRow();
        if (fila >= 0) {
            modelo.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.");
        }
    }

    public void calcularTotalPagar(JTable resumen, JLabel iva, JLabel totalPagar) {
        DefaultTableModel modelo = (DefaultTableModel) resumen.getModel();
        double subtotal = 0.0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            subtotal += (double) modelo.getValueAt(i, 4);
        }

        double totalIVA = subtotal * 0.18;
        totalPagar.setText(String.format("%.2f", subtotal));
        iva.setText(String.format("%.2f", totalIVA));
    }

    public void crearFactura(JTextField codcliente) {
        try (Connection conn = ConexionSingleton.getInstancia().getConexion()) {
            int idCliente = Integer.parseInt(codcliente.getText());
            String consulta = "INSERT INTO factura (fechaFactura, fkcliente) VALUES (CURDATE(), ?)";
            CallableStatement cs = conn.prepareCall(consulta);
            cs.setInt(1, idCliente);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Factura creada correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear factura: " + e.getMessage());
        }
    }

    public void realizarVenta(JTable resumen) {
        String sqlDetalle = "INSERT INTO detalle (fkfactura, fkproducto, cantidad, precioVenta) " +
                "VALUES ((SELECT MAX(idfactura) FROM factura), ?, ?, ?)";
        String sqlStock = "UPDATE producto SET stock = stock - ? WHERE idproducto = ?";

        try (Connection conn = ConexionSingleton.getInstancia().getConexion()) {
            PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle);
            PreparedStatement psStock = conn.prepareStatement(sqlStock);

            for (int i = 0; i < resumen.getRowCount(); i++) {
                int idProd = Integer.parseInt(resumen.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(resumen.getValueAt(i, 3).toString());
                double precio = Double.parseDouble(resumen.getValueAt(i, 2).toString());

                psDetalle.setInt(1, idProd);
                psDetalle.setInt(2, cantidad);
                psDetalle.setDouble(3, precio);
                psDetalle.execute();

                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProd);
                psStock.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Renta realizada exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar venta: " + e.getMessage());
        }
    }

    public void limpiarLuegoVenta(JTextField buscarCliente, JTable tablaCliente,
                                  JTextField buscarProducto, JTable tablaProducto,
                                  JTextField idCliente, JTextField nombreCliente,
                                  JTextField appaterno, JTextField apmaterno,
                                  JTextField idProducto, JTextField nombreProducto,
                                  JTextField precioProducto, JTextField stockProducto,
                                  JTextField precioVenta, JTextField cantidad,
                                  JTable resumen, JLabel iva, JLabel total) {
        buscarCliente.setText("");
        buscarProducto.setText("");
        idCliente.setText("");
        nombreCliente.setText("");
        appaterno.setText("");
        apmaterno.setText("");
        idProducto.setText("");
        nombreProducto.setText("");
        precioProducto.setText("");
        stockProducto.setText("");
        precioVenta.setText("");
        cantidad.setText("");
        iva.setText("0.00");
        total.setText("0.00");

        ((DefaultTableModel) tablaCliente.getModel()).setRowCount(0);
        ((DefaultTableModel) tablaProducto.getModel()).setRowCount(0);
        ((DefaultTableModel) resumen.getModel()).setRowCount(0);
    }

    public void MostrarUltimaFactura(JLabel lblUltima) {
        try (Connection conn = ConexionSingleton.getInstancia().getConexion()) {
            String sql = "SELECT MAX(idfactura) AS UltimaFactura FROM factura";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lblUltima.setText(String.valueOf(rs.getInt("UltimaFactura")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar última factura: " + e.getMessage());
        }
    }

    private void bloquearEdicionTabla(JTable tabla) {
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.setDefaultEditor(tabla.getColumnClass(i), null);
        }
    }
}
