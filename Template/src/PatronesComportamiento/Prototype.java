/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesComportamiento;

/**
 *
 * @author Michel Mendez
 */
public class Prototype {

    public interface Clonable<T> {
        T clonar();
    }

    /*public static class Cliente implements Clonable<Cliente> {
        private String nombre;

        public Cliente(String nombre) {
            this.nombre = nombre;
        }

        public Cliente clonar() {
            return new Cliente(this.nombre);
        }

        public String getNombre() {
            return nombre;
        }
    }

    public static class Producto implements Clonable<Producto> {
        private String nombre;
        private double precio;

        public Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        public Producto clonar() {
            return new Producto(this.nombre, this.precio);
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }
    }*/
}
