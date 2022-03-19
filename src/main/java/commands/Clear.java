package commands;


import java.util.List;

/**
 * Класс, предназначенный для очищения коллекции (<i>безвозвратного удаления всех элементов</i>)
 */
public class Clear extends Command {

    public Clear(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances) {
        instances.dao.clear();
        instances.outPutter.output("Коллекция успешно очищена");
        return 0;
    }
}