package PatronesEstructurales;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
 * Adaptador para generar comprobantes de factura en PDF,
 * desglosado en tres pasos para el Template Method:
 *   - agregarEncabezado(...)
 *   - agregarTabla(...)
 *   - agregarTotales(...)
 */
public class Adapter {

    private Document documento;
    private String nombreArchivo;

    public Adapter() {
        this.documento = new Document();
    }

    /**
     * Inicializa el Document y lo abre para escritura.
     */
    private void iniciarDocumento(JLabel lblFactura) throws DocumentException, IOException {
        // Genera un nombre único para el archivo
        nombreArchivo = "reporte_comprobante_" +
                lblFactura.getText().replaceAll("\\s+", "_") + "_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) +
                ".pdf";

        PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
        documento.open();
    }

    /**
     * Paso 1: Genera la cabecera del PDF (título y datos del cliente).
     */
    public void agregarEncabezado(JLabel lblFactura,
                                  JLabel lblFechaFactura,
                                  JLabel lblNombreCliente,
                                  JLabel lblAppaterno,
                                  JLabel lblApmaterno) {
        try {
            iniciarDocumento(lblFactura);

            documento.add(new Paragraph("==================================="));
            documento.add(new Paragraph("           COMPROBANTE DE ALQUILER"));
            documento.add(new Paragraph("===================================\n"));

            documento.add(new Paragraph("Factura N°: " + lblFactura.getText()));
            documento.add(new Paragraph("Fecha de Venta: " + lblFechaFactura.getText()));
            documento.add(new Paragraph("Cliente: " +
                    lblNombreCliente.getText() + " " +
                    lblAppaterno.getText() + " " +
                    lblApmaterno.getText()));
            documento.add(new Paragraph("\n"));

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paso 2: Volca la tabla de productos al PDF.
     */
    public void agregarTabla(JTable tablaProductos) {
        try {
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
            documento.add(new Paragraph("\n"));

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paso 3: Agrega IVA y total, y cierra el documento.
     */
    public void agregarTotales(JLabel lblIVA, JLabel lblTotal) {
        try {
            documento.add(new Paragraph("IVA: " + lblIVA.getText()));
            documento.add(new Paragraph("Total a Pagar: " + lblTotal.getText()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (documento.isOpen()) {
                documento.close();
                System.out.println("✅ PDF generado correctamente: " + nombreArchivo);
            }
        }
    }
}
