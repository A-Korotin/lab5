package commands;

import collection.DAO;
import collection.Orderable;

import java.util.List;

/**
 * Класс, предназначенный для сортировки коллекции. Сортировка производится по <i>возрастанию</i> поля <b>"возраст"</b>
 */
public class Sort extends Command {

    public Sort(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        if (args.size() > 0) {
            //outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        assert dao instanceof Orderable;
        int exitCode = ((Orderable)dao).sort();
        //outPuter.outPut("Коллекция успешно отсортирована");
        return exitCode;
    }
}