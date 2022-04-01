package commands;


import commands.dependencies.Instances;
import commands.dependencies.PropertiesDependant;
import exceptions.InvalidArgsSizeException;
import io.Properties;
import commands.dependencies.CommandProperties;
import java.util.List;

/**
 * Абстрактный надкласс всех команд
 */

public abstract class Command {
    protected List<String> args;
    protected boolean askForInput;
    protected String name;
    protected Properties properties = null;

    public void setAskForInput(boolean ask) {
        askForInput = ask;
    }

    public String getName() {
        return name;
    }

    public final CommandProperties getProperties(Instances instances) {
        CommandProperties p = new CommandProperties();
        p.args = args;
        p.args.add(0, name);
        if (this instanceof PropertiesDependant)
            p.properties = instances.consoleRequester.requestProperties();
        return p;
    }

    public static Command restoreFromProperties(CommandProperties properties) {
        List<String> args = properties.args;
        Command c = CommandCreator.getCommandDirect(args);
        c.properties = properties.properties;
        return c;
    }

    private boolean validArgsSize(Integer[] expected) {
        for(Integer i: expected) {
            if (i == args.size())
                return true;
        }
        return false;
    }

    public Command(List<String> args, Integer... nArgsExpected) {
        this.args = args;
        name = args.get(0);
        args.remove(0);
        args.removeIf(String::isEmpty);

        if (!validArgsSize(nArgsExpected))
            throw new InvalidArgsSizeException(name, args.size(), nArgsExpected);
    }

    /**
     *
     * @param instances класс необходимых экземпляров
     * @return Код выхода команды.
     * <table align="left">
     *     <tr><b>0</b>  - команда завершена <i>без ошибок</i></tr>
     *     <tr><b>-1</b> - команда завершена <i>c ошибками</i></tr>
     * </table>
     *
     */
    public abstract int execute(Instances instances);
}
