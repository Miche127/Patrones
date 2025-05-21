package command;

import javax.swing.JTable;
import controlador.ControladorProducto;

public class MostrarProductosCommand implements Command {
    private ControladorProducto controlador;
    private JTable tabla;

    public MostrarProductosCommand(ControladorProducto controlador, JTable tabla) {
        this.controlador = controlador;
        this.tabla = tabla;
    }

    @Override
    public void execute() {
        controlador.MostrarProductos(tabla);
    }
}
