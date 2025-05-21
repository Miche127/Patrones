// src/PatronesComportamiento/MacroExportPDF.java
package PatronesComportamiento;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite de IMediator: delega exportarPDF() a todos sus hijos.
 */
public class MacroExportPDF implements IMediator {
    private final List<IMediator> mediadores = new ArrayList<>();

    /** Agrega un exportador al composite */
    public void add(IMediator m) {
        mediadores.add(m);
    }

    /** Quita un exportador del composite */
    public void remove(IMediator m) {
        mediadores.remove(m);
    }

    @Override
    public void exportarPDF(JLabel lblFactura,
                             JLabel lblFechaFactura,
                             JLabel lblNombreCliente,
                             JLabel lblAppaterno,
                             JLabel lblApmaterno,
                             JTable tablaProductos,
                             JLabel lblIVA,
                             JLabel lblTotal) {
        for (IMediator m : mediadores) {
            m.exportarPDF(
                lblFactura, lblFechaFactura,
                lblNombreCliente, lblAppaterno, lblApmaterno,
                tablaProductos, lblIVA, lblTotal
            );
        }
    }
}
