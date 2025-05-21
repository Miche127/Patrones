/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesComportamiento;

/**
 *
 * @author Michel Mendez
 */
public class Responsabilidad {

    public static abstract class Validador {
        protected Validador siguiente;
        public void setSiguiente(Validador siguiente) {
            this.siguiente = siguiente;
        }
        public void manejar(String dato) {
            if (siguiente != null) {
                siguiente.manejar(dato);
            }
        }
    }

    public static class ValidadorStock extends Validador {
        public void manejar(String producto) {
            System.out.println("Validando stock para " + producto);
            super.manejar(producto);
        }
    }

    public static class ValidadorCliente extends Validador {
        public void manejar(String producto) {
            System.out.println("Validando cliente para producto: " + producto);
            super.manejar(producto);
        }
    }

}
