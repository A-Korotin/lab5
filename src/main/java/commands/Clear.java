package commands;


import java.util.List;

/**
 * Класс, предназначенный для очищения коллекции (<i>безвозвратного удаления всех элементов</i>)
 */
public class Clear extends Command {

    public Clear(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.consoleOutput.output("Неверное количество параметров");
            return -1;
        }
        instances.dao.clear();
        instances.consoleOutput.output("Коллекция успешно очищена");
        return 0;
    }
}