package commands;
import collection.DAO;

import java.util.List;
import io.OutPutter;

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

    public Command(List<String> args) {
        this.args = args;
        name = args.get(0);
        args.remove(0);
    }

    /**
     *
     * @param instances Экземпляры всех классов, необходимых для корректного исполнения команды
     * @return Код выхода команды.
     * <table align="left">
     *     <tr><b>0</b>  - команда завершена <i>без ошибок</i></tr>
     *     <tr><b>-1</b> - команда завершена <i>c ошибками</i></tr>
     * </table>
     *
     */
    public abstract int execute(Instances instances);
}
