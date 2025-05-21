package PatronesCreacionales;

import controlador.IControlador;
import java.util.Map;
import java.util.function.Supplier;
import controlador.IControlador;
import controlador.ControlAlquiler;
import controlador.ControladorCliente;
import controlador.ControladorProducto;
import controlador.ControladorReportes;
         
public class ControladorFactory {
    private final Map<String, Supplier<IControlador>> proveedores;

    public ControladorFactory() {
        proveedores = Map.of(
            "producto", ControladorProducto::new,
            "cliente",  ControladorCliente::new,
            "alquiler", ControlAlquiler::new,
            "reportes", ControladorReportes::new
        );
    }

    public IControlador crear(String tipo) {
        Supplier<IControlador> prov = proveedores.get(tipo.toLowerCase());
        if (prov != null) return prov.get();
        throw new IllegalArgumentException("Tipo desconocido: " + tipo);
    }
}