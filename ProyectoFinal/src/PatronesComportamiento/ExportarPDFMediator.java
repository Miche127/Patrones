package PatronesComportamiento;

import PatronesEstructurales.Adapter;
import javax.swing.*;

/**
 * Mediator que extiende el template de exportación y solo implementa
 * los pasos concretos, delegando a tu Adapter para el volcado real.
 */
public class ExportarPDFMediator extends ExportadorPDFTemplate {

    private final Adapter adaptadorPDF;

    public ExportarPDFMediator() {
        this.adaptadorPDF = new Adapter();
    }

    @Override
    protected void generarCabecera(JLabel lblFactura,
                                   JLabel lblFechaFactura,
                                   JLabel lblNombreCliente,
                                   JLabel lblAppaterno,
                                   JLabel lblApmaterno) {
        // Aquí “arma” la cabecera usando tu adapter
        adaptadorPDF.agregarEncabezado(
            lblFactura, lblFechaFactura,
            lblNombreCliente, lblAppaterno, lblApmaterno
        );
    }

    @Override
    protected void generarContenido(JTable tablaProductos) {
        // Volcar la tabla al PDF
        adaptadorPDF.agregarTabla(tablaProductos);
    }

    @Override
    protected void generarPie(JLabel lblIVA, JLabel lblTotal) {
        // Agregar líneas finales (IVA, totales)
        adaptadorPDF.agregarTotales(lblIVA, lblTotal);
    }
}
