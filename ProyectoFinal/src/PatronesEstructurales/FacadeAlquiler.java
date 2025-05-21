// src/PatronesEstructurales/FacadeAlquiler.java
package PatronesEstructurales;

import PatronesCreacionales.ControladorFactory;
import controlador.IControlador;
import controlador.ControlAlquiler;
import controlador.IControlador;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class FacadeAlquiler {
    private final ControlAlquiler control;

    public FacadeAlquiler() {
        // en lugar de new ControlAlquiler():
        IControlador ctrl = new ControladorFactory().crear("alquiler");
        // casteamos al tipo concreto para seguir usando sus métodos
        this.control = (ControlAlquiler) ctrl;
    }

    /**
     * Realiza el proceso completo de crear factura y registrar la venta.
     * 
     * @param cliente   Campo de texto con el ID del cliente
     * @param productos Tabla resumen de productos seleccionados
     */
    public void realizarAlquiler(JTextField cliente, JTable productos) {
        if (cliente.getText().isEmpty() || productos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null,
                "Debe seleccionar un cliente y agregar productos antes de alquilar.");
            return;
        }

        control.crearFactura(cliente);
        control.realizarVenta(productos);

        JOptionPane.showMessageDialog(null, "¡Alquiler completado exitosamente!");
    }
}
