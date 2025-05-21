/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesEstructurales;

import PatronesCreacionales.SesionUsuarioSingleton;
import PatronesEstructurales.Bridge.VistaFormulario;
import formulario.MenuPrincipal;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author Michel Mendez
 */
public class Proxy {

    public interface AccesoFormularioProxy {
        void acceder(VistaFormulario vista, MenuPrincipal menu);
    }

    public static class AccesoProxy implements AccesoFormularioProxy {
        private FormularioRealProxy real = new FormularioRealProxy();
        private String boton;

        public AccesoProxy(String boton) {
            this.boton = boton;
        }

        @Override
        public void acceder(VistaFormulario vista, MenuPrincipal menu) {
            String rango = SesionUsuarioSingleton.getInstancia().getRango();
            List<String> permitidosEmpleado = Arrays.asList(
                "rentas", "busqueda de comprobantes", "busqueda de reportes"
            );

            if (rango.equals("Admin") || permitidosEmpleado.contains(boton)) {
                real.acceder(vista, menu);
            } else {
                JOptionPane.showMessageDialog(null, "Acceso denegado a: " + boton +
                        "\nRango actual: " + rango);
            }
        }
    }

    public static class FormularioRealProxy implements AccesoFormularioProxy {
        @Override
        public void acceder(VistaFormulario vista, MenuPrincipal menu) {
            vista.mostrar(); // Esto usa Bridge
        }
    }
}

