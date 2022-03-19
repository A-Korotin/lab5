package commands;


import exceptions.InvalidArgsSizeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный надкласс всех команд
 */

public abstract class Command {
    protected List<String> args;
    protected boolean askForInput;
    protected String name;

    public void setAskForInput(boolean ask) {
        askForInput = ask;
    }

    public String getName() {
        return name;
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
