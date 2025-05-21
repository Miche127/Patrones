package command;

import javax.swing.JTable;
import controlador.ControladorCliente;

public class MostrarClientesCommand implements Command {
    private ControladorCliente controlador;
    private JTable tabla;

    public MostrarClientesCommand(ControladorCliente controlador, JTable tabla) {
        this.controlador = controlador;
        this.tabla = tabla;
    }

    @Override
    public void execute() {
        controlador.Mostrarclientes(tabla);
    }
}
