/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesComportamiento;

import controlador.ControlAlquiler;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Michel Mendez
 */
public class Command {
    public interface Comando {
        void ejecutar();
    }

    public class ComandoCrearFactura extends ControlAlquiler implements Comando {
        private JTextField cliente;
        public ComandoCrearFactura(JTextField cliente) {
            this.cliente = cliente;
        }

        public void ejecutar() {
            crearFactura(cliente);
        }
    }

    public class ComandoRealizarVenta extends ControlAlquiler implements Comando {
        private JTable resumen;
        public ComandoRealizarVenta(JTable resumen) {
            this.resumen = resumen;
        }

        public void ejecutar() {
            realizarVenta(resumen);
        }
    }

    public class ControladorComando {
        private List<Comando> historial = new ArrayList<>();
        public void ejecutarComando(Comando comando) {
            comando.ejecutar();
            historial.add(comando);
        }
    }
}
