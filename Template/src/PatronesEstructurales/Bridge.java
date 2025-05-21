/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesEstructurales;

import formulario.MenuPrincipal;
import formulario.alquiler;
import formulario.buscarComprobante;
import formulario.clientes;
import formulario.products;
import formulario.reporteFechas;
import javax.swing.JPanel;

/**
 *
 * @author Michel Mendez
 */
public class Bridge {

    public static interface VistaFormulario {
        void mostrar();
    }

    public static interface VistaFormularioImplementacion {
        void mostrarVista();
    }

    public static class VistaFormularioProducto implements VistaFormularioImplementacion {
        @Override
        public void mostrarVista() {
            JPanel panel = new products();
            MenuPrincipal.getInstancia().mostrarPanel(panel); // Asegúrate que MenuPrincipal tenga un método así
        }
    }
    
    public static class VistaFormularioAlquiler implements VistaFormularioImplementacion {
        @Override
        public void mostrarVista() {
            JPanel panel = new alquiler();
            MenuPrincipal.getInstancia().mostrarPanel(panel); // Asegúrate que MenuPrincipal tenga un método así
        }
    }

    public static class VistaFormularioCliente implements VistaFormularioImplementacion {
        @Override
        public void mostrarVista() {
            JPanel panel = new clientes();
            MenuPrincipal.getInstancia().mostrarPanel(panel); // Asegúrate que MenuPrincipal tenga un método así
        }
    }
    
    public static class VistaFormularioBuscarComprobante implements VistaFormularioImplementacion {
        @Override
        public void mostrarVista() {
            JPanel panel = new buscarComprobante();
            MenuPrincipal.getInstancia().mostrarPanel(panel); // Asegúrate que MenuPrincipal tenga un método así
        }
    }
    
    public static class VistaFormularioReporteFechas implements VistaFormularioImplementacion {
        @Override
        public void mostrarVista() {
            JPanel panel = new reporteFechas();
            MenuPrincipal.getInstancia().mostrarPanel(panel); // Asegúrate que MenuPrincipal tenga un método así
        }
    }

    public static class FormularioUI implements VistaFormulario {
        private VistaFormularioImplementacion implementacion;

        public FormularioUI(VistaFormularioImplementacion implementacion) {
            this.implementacion = implementacion;
        }

        @Override
        public void mostrar() {
            implementacion.mostrarVista();
        }
    }
}
