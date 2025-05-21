package command;

public class Invoker {
    private Command comando;

    public void setCommand(Command comando) {
        this.comando = comando;
    }

    public void ejecutar() {
        if (comando != null) {
            comando.execute();
        }
    }
}
