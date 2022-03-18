package commands;

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
    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.consoleOutput.output("Неверное количество параметров");
            return -1;
        }
        assert instances.dao instanceof Orderable;
        int exitCode = ((Orderable)instances.dao).sort();
        instances.consoleOutput.output("Коллекция успешно отсортирована");
        return exitCode;
    }
}