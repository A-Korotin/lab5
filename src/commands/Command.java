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
    protected OutPutter outPutter;

    public void setAskForInput(boolean ask) {
        askForInput = ask;
    }

    public Command(List<String> args) {
        this.args = args;
        name = args.get(0);
        args.remove(0);
    }

    /**
     *
     * @param dao Коллекция, которой управляет пользователь
     * @return Код выхода команды.
     * <table align="left">
     *     <tr><b>0</b>  - команда завершена <i>без ошибок</i></tr>
     *     <tr><b>-1</b> - команда завершена <i>c ошибками</i></tr>
     * </table>
     *
     */
    public abstract int execute(DAO dao);
}
