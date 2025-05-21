package PatronesComportamiento;

import PatronesEstructurales.Adapter;
import javax.swing.*;

/**
 * Clase Mediador que se encarga de coordinar la exportación de un PDF
 * sin que el formulario interactúe directamente con el Adapter.
 */
public class ExportarPDFMediator implements IMediator {

    private final Adapter adaptadorPDF;

    public ExportarPDFMediator() {
        this.adaptadorPDF = new Adapter(); // Inyección directa del adaptador
    }

    @Override
    public void exportarPDF(JLabel lblFactura, JLabel lblFechaFactura, JLabel lblNombreCliente,
                            JLabel lblAppaterno, JLabel lblApmaterno,
                            JTable tablaProductos, JLabel lblIVA, JLabel lblTotal) {

        // Aquí el mediador actúa como coordinador entre el formulario y el Adapter
        adaptadorPDF.exportarPDFCompleto(
            lblFactura,
            lblFechaFactura,
            lblNombreCliente,
            lblAppaterno,
            lblApmaterno,
            tablaProductos,
            lblIVA,
            lblTotal
        );
    }
}
