/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesComportamiento;

/**
 *
 * @author Michel Mendez
 */
public class Strategy {

    public interface EstrategiaDescuento {

        double aplicarDescuento(double total);
    }

    public class DescuentoFijo implements EstrategiaDescuento {

        public double aplicarDescuento(double total) {
            return total - 10;
        }
    }

    public class DescuentoPorcentaje implements EstrategiaDescuento {

        public double aplicarDescuento(double total) {
            return total * 0.9;
        }
    }

    public class ProcesadorDescuento {

        private EstrategiaDescuento estrategia;

        public void setEstrategia(EstrategiaDescuento estrategia) {
            this.estrategia = estrategia;
        }

        public double calcularTotalConDescuento(double total) {
            return estrategia.aplicarDescuento(total);
        }
    }
}
