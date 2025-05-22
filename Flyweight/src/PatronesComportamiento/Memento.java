/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesComportamiento;
/**
 *
 * @author Michel Mendez
 */
public class Memento {
    public static class EstadoFormulario {
        private final String cliente;
        private final String producto;
        
        public EstadoFormulario(String cliente, String producto) {
            this.cliente = cliente;
            this.producto = producto;
        }
        public String getCliente() {
            return cliente;
        }
        public String getProducto() {
            return producto;
        }
    }

    public static class FormularioVenta {
        private String cliente;
        private String producto;

        public void setEstado(String cliente, String producto) {
            this.cliente = cliente;
            this.producto = producto;
        }
        public EstadoFormulario guardarEstado() {
            return new EstadoFormulario(cliente, producto);
        }
        public void restaurarEstado(EstadoFormulario estado) {
            this.cliente = estado.getCliente();
            this.producto = estado.getProducto();
        }
    }

}
