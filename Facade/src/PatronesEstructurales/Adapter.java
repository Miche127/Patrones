package PatronesEstructurales;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JLabel;
import javax.swing.JTable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Adaptador para generar comprobantes de factura en PDF.
 */
public class Adapter {

    public void exportarPDFCompleto(JLabel lblFactura, JLabel lblFechaFactura, JLabel lblNombreCliente,
                                    JLabel lblAppaterno, JLabel lblApmaterno,
                                    JTable tablaProductos, JLabel lblIVA, JLabel lblTotal) {

        Document documento = new Document();
        try {
            // Nombre del archivo con fecha y número de factura para evitar sobrescritura
            String nombreArchivo = "reporte_comprobante_" + lblFactura.getText().replaceAll("\\s+", "_")
                                + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
            
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            // Título
            documento.add(new Paragraph("==================================="));
            documento.add(new Paragraph("           COMPROBANTE DE ALQUILER"));
            documento.add(new Paragraph("===================================\n"));

            // Datos del cliente
            documento.add(new Paragraph("Factura N°: " + lblFactura.getText()));
            documento.add(new Paragraph("Fecha de Venta: " + lblFechaFactura.getText()));
            documento.add(new Paragraph("Cliente: " + lblNombreCliente.getText() + " " +
                                        lblAppaterno.getText() + " " + lblApmaterno.getText()));
            documento.add(new Paragraph(" "));

            // Tabla de productos
            int columnas = tablaProductos.getColumnCount();
            if (columnas < 4) {
                throw new IllegalArgumentException("La tabla debe tener al menos 4 columnas: Producto, Cantidad, Precio, Subtotal.");
            }

            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell("Producto");
            tabla.addCell("Cantidad");
            tabla.addCell("Precio Venta");
            tabla.addCell("Subtotal");

            for (int i = 0; i < tablaProductos.getRowCount(); i++) {
                for (int j = 0; j < 4; j++) {
                    Object valor = tablaProductos.getValueAt(i, j);
                    tabla.addCell(valor != null ? valor.toString() : "");
                }
            }

            documento.add(tabla);

            // Totales
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("IVA: " + lblIVA.getText()));
            documento.add(new Paragraph("Total a Pagar: S/ " + lblTotal.getText()));

            documento.close();
            System.out.println("PDF generado exitosamente: " + nombreArchivo);

        } catch (Exception e) {
            System.err.println("Error al generar el PDF:");
            e.printStackTrace();
        } finally {
            if (documento.isOpen()) {
                documento.close();
            }
        }
    }
}
