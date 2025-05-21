package PatronesEstructurales;

import PatronesCreacionales.SesionUsuarioSingleton;
import PatronesEstructurales.Bridge.VistaFormulario;
import formulario.MenuPrincipal;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class Proxy {

    public interface AccesoFormularioProxy {
        void acceder(VistaFormulario vista, MenuPrincipal menu);
    }

    public static class AccesoProxy implements AccesoFormularioProxy {
        private final FormularioRealProxy real = new FormularioRealProxy();
        private final String boton;
        // Nombres EXACTOS de los botones (en mayúsculas) que el rango "Empleado" puede abrir:
        private static final List<String> permitidosEmpleado = Arrays.asList(
            "CLIENTES",
            "ALMACEN",
            "REPORTE POR FECHAS",
            "BUSCAR COMPROBANTE",
            "RENTA"
        );

        public AccesoProxy(String boton) {
            this.boton = boton;
        }

        @Override
        public void acceder(VistaFormulario vista, MenuPrincipal menu) {
            String rango = SesionUsuarioSingleton.getInstancia().getRango();
            System.out.println("Proxy.acceder(): boton=" + boton + ", rango=" + rango);

            boolean esAdmin = "ADMIN".equalsIgnoreCase(rango);
            boolean permitido = permitidosEmpleado.contains(boton.toUpperCase());

            if (esAdmin || permitido) {
                real.acceder(vista, menu);
            } else {
                JOptionPane.showMessageDialog(
                    menu,
                    "Acceso denegado a: " + boton +
                    "\nRango actual: " + rango,
                    "Acceso Denegado",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private static class FormularioRealProxy implements AccesoFormularioProxy {
        @Override
        public void acceder(VistaFormulario vista, MenuPrincipal menu) {
            vista.mostrar();
        }
    }
}
