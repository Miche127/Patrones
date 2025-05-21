/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formulario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

public class MenuPrincipal extends JFrame {
    
    private JDesktopPane dpFormularios;
    private JButton btnRenta, btnBuscarComprobante, btnReporteFechas, btnAlmacen, btnClientes, btnCerrar;

    public MenuPrincipal() {
        setTitle("Menú Principal - Alquileres California");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializar componentes
        dpFormularios = new JDesktopPane();
        btnRenta = new JButton("RENTA");
        btnBuscarComprobante = new JButton("BUSCAR COMPROBANTE");
        btnReporteFechas = new JButton("REPORTE POR FECHAS");
        btnAlmacen = new JButton("ALMACÉN");
        btnClientes = new JButton("CLIENTES");
        btnCerrar = new JButton("CERRAR");
        
        // Configurar botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6, 1, 10, 10));
        panelBotones.add(btnRenta);
        panelBotones.add(btnBuscarComprobante);
        panelBotones.add(btnReporteFechas);
        panelBotones.add(btnAlmacen);
        panelBotones.add(btnClientes);
        panelBotones.add(btnCerrar);
        
        // Eventos de los botones
        btnRenta.addActionListener(this::abrirRenta);
        btnBuscarComprobante.addActionListener(this::abrirBuscarComprobante);
        btnReporteFechas.addActionListener(this::abrirReporteFechas);
        btnAlmacen.addActionListener(this::abrirAlmacen);
        btnClientes.addActionListener(this::abrirClientes);
        btnCerrar.addActionListener(e -> System.exit(0));
        
        // Agregar componentes al JFrame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelBotones, BorderLayout.WEST);
        getContentPane().add(dpFormularios, BorderLayout.CENTER);
    }

    // Método para abrir formularios en el JDesktopPane evitando duplicados
    private void abrirFormulario(JInternalFrame formulario) {
        for (JInternalFrame frame : dpFormularios.getAllFrames()) {
            if (frame.getClass().equals(formulario.getClass())) {
                try {
                    frame.setSelected(true);
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        formulario.setClosable(true);
        formulario.setIconifiable(true);
        formulario.setMaximizable(true);
        formulario.setResizable(true);
        formulario.setSize(600, 400);
        formulario.setLocation((dpFormularios.getWidth() - formulario.getWidth()) / 2, 
                               (dpFormularios.getHeight() - formulario.getHeight()) / 2);

        dpFormularios.add(formulario);
        formulario.setVisible(true);
    }

    private void abrirRenta(ActionEvent evt) { new FormAlquiler().setVisible(true); }
private void abrirBuscarComprobante(ActionEvent evt) { new FormBuscarComprobante().setVisible(true); }
private void abrirReporteFechas(ActionEvent evt) { new FormReportesFechas().setVisible(true); }
private void abrirAlmacen(ActionEvent evt) { new FormProducts().setVisible(true); }
private void abrirClientes(ActionEvent evt) { new formClientes().setVisible(true); }


    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}

