package PatronesComportamiento;

import javax.swing.*;

/**
 * Clase que define la secuencia (template method) para exportar un PDF.
 * Los pasos fijos (iniciar y finalizar) están implementados aquí,
 * y los pasos variables (cabecera, contenido, pie) quedan como métodos
 * abstractos para que las subclases los implementen.
 */
public abstract class ExportadorPDFTemplate implements IMediator {

    @Override
    public final void exportarPDF(JLabel lblFactura,
                                  JLabel lblFechaFactura,
                                  JLabel lblNombreCliente,
                                  JLabel lblAppaterno,
                                  JLabel lblApmaterno,
                                  JTable tablaProductos,
                                  JLabel lblIVA,
                                  JLabel lblTotal) {
        iniciarDocumento();
        generarCabecera(lblFactura, lblFechaFactura, lblNombreCliente, lblAppaterno, lblApmaterno);
        generarContenido(tablaProductos);
        generarPie(lblIVA, lblTotal);
        finalizarDocumento();
    }

    /** Paso fijo (hook): configuración previa al PDF */
    protected void iniciarDocumento() {
        System.out.println("⏳ Iniciando exportación de PDF…");
        // Por ejemplo: crear carpeta, abrir stream, etc.
    }

    /** Paso variable: la subclase define cómo crea la cabecera */
    protected abstract void generarCabecera(JLabel lblFactura,
                                            JLabel lblFechaFactura,
                                            JLabel lblNombreCliente,
                                            JLabel lblAppaterno,
                                            JLabel lblApmaterno);

    /** Paso variable: la subclase define cómo vuelca la tabla */
    protected abstract void generarContenido(JTable tablaProductos);

    /** Paso variable: la subclase define cómo muestra totales e IVA */
    protected abstract void generarPie(JLabel lblIVA, JLabel lblTotal);

    /** Paso fijo (hook): cerrar archivos, streams, etc. */
    protected void finalizarDocumento() {
        System.out.println("✅ PDF generado correctamente.");
    }
}
