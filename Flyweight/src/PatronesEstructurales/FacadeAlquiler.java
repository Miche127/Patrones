/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesEstructurales;

import controlador.ControlAlquiler;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Michel Mendez
 */
public class FacadeAlquiler {
    private ControlAlquiler control;

    public FacadeAlquiler() {
        this.control = new ControlAlquiler();
    }

    /**
     * Realiza el proceso completo de crear factura y registrar la venta.
     * 
     * @param cliente   Campo de texto con el ID del cliente
     * @param productos Tabla resumen de productos seleccionados
     */
    public void realizarAlquiler(JTextField cliente, JTable productos) {
        if (cliente.getText().isEmpty() || productos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente y agregar productos antes de alquilar.");
            return;
        }

        control.crearFactura(cliente);
        control.realizarVenta(productos);

        JOptionPane.showMessageDialog(null, "¡Alquiler completado exitosamente!");
    }
}
