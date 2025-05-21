// src/PatronesComportamiento/MacroCommand.java
package PatronesComportamiento;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite de comandos: agrupa varios Command y al ejecutar()
 * recorre la lista y llama a execute() de cada uno.
 */
public class MacroCommand extends Command {
    private final List<Command> comandos = new ArrayList<>();

    /** Agrega un subcomando */
    public void add(Command cmd) {
        comandos.add(cmd);
    }

    /** Quita un subcomando */
    public void remove(Command cmd) {
        comandos.remove(cmd);
    }
}
   
