package PatronesComportamiento;

import javax.swing.*;

public interface IMediator {
    void exportarPDF(JLabel lblFactura, JLabel lblFechaFactura, JLabel lblNombreCliente,
                     JLabel lblAppaterno, JLabel lblApmaterno,
                     JTable tablaProductos, JLabel lblIVA, JLabel lblTotal);
}
