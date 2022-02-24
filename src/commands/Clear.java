package commands;

import collection.DAO;

import java.util.List;

/**
 * Класс, предназначенный для очищения коллекции (<i>безвозвратного удаления всех элементов</i>)
 */
public class Clear extends Command {

    public Clear(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        if (args.size() > 0) {
            //outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        dao.clear();
        //outPuter.outPut("Коллекция успешно очищена");
        return 0;
    }
}