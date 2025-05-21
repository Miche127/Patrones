package command;

import javax.swing.JTable;
import javax.swing.JTextField;
import controlador.ControladorCliente;
import controlador.ControladorProducto;
import controlador.ControlAlquiler;

public class Main {
    public static void main(String[] args) {
        // Este main es solo un ejemplo de cómo se invocan los comandos.
        // En NetBeans, las llamadas reales se hacen desde formularios Swing.

        System.out.println("Simulando comandos usando patrón Command...");

        ControladorCliente cCliente = new ControladorCliente();
        JTable tablaClientes = new JTable();
        Command mostrarClientes = new MostrarClientesCommand(cCliente, tablaClientes);

        ControladorProducto cProducto = new ControladorProducto();
        JTable tablaProductos = new JTable();
        Command mostrarProductos = new MostrarProductosCommand(cProducto, tablaProductos);

        ControlAlquiler cAlquiler = new ControlAlquiler();
        JTextField campoBusqueda = new JTextField("ejemplo");
        JTable tablaResultados = new JTable();
        Command buscarProducto = new BuscarProductoCommand(cAlquiler, campoBusqueda, tablaResultados);

        Invoker invoker = new Invoker();

        invoker.setCommand(mostrarClientes);
        invoker.ejecutar();

        invoker.setCommand(mostrarProductos);
        invoker.ejecutar();

        invoker.setCommand(buscarProducto);
        invoker.ejecutar();

        System.out.println("Comandos ejecutados (simulados en consola).");
    }
}
