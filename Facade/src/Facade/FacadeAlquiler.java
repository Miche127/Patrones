/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facade;

import controlador.ControlAlquiler;
import javax.swing.*;

/**
 *
 * @author Michel Mendez
 */

//Facade encapsula el proceso de alquiler completo.

public class FacadeAlquiler {

    private final ControlAlquiler control;

    public FacadeAlquiler() {
        this.control = new ControlAlquiler();
    }

    public boolean realizarProcesoAlquiler(
            JTextField cliente,
            JTable resumen,
            JLabel lblIVA,
            JLabel lblTotal,
            JTextField buscarCliente,
            JTable tablaClientes,
            JTextField buscarProducto,
            JTable tablaProductos,
            JTextField idCliente,
            JTextField nombreCliente,
            JTextField appaterno,
            JTextField apmaterno,
            JTextField idProducto,
            JTextField nombreProducto,
            JTextField precioProducto,
            JTextField stockProducto,
            JTextField precioVenta,
            JTextField cantidad,
            JLabel lblUltimaFactura
    ) {
        if (cliente.getText().isEmpty() || resumen.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente y agregar productos antes de alquilar.");
            return false;
        }

        control.crearFactura(cliente);
        control.realizarVenta(resumen);

        control.limpiarLuegoVenta(
                buscarCliente, tablaClientes,
                buscarProducto, tablaProductos,
                idCliente, nombreCliente, appaterno, apmaterno,
                idProducto, nombreProducto, precioProducto, stockProducto,
                precioVenta, cantidad, resumen, lblIVA, lblTotal
        );

        control.MostrarUltimaFactura(lblUltimaFactura);

        JOptionPane.showMessageDialog(null, "¡Alquiler completado exitosamente!");
        return true;
    }
}
