package command;

import javax.swing.JTable;
import javax.swing.JTextField;
import controlador.ControlAlquiler;

public class BuscarProductoCommand implements Command {
    private ControlAlquiler controlador;
    private JTextField campoBusqueda;
    private JTable tabla;

    public BuscarProductoCommand(ControlAlquiler controlador, JTextField campoBusqueda, JTable tabla) {
        this.controlador = controlador;
        this.campoBusqueda = campoBusqueda;
        this.tabla = tabla;
    }

    @Override
    public void execute() {
        controlador.BuscarProducto(campoBusqueda, tabla);
    }
}
