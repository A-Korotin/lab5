package commands;

import collection.DAO;

import java.util.List;

/**
 * Класс, предназначенный для сохранения информации о коллекции в файл формата <b>JSON</b><br>
 * Путь до искомого файла передается через <i>переменную окружения</i> <b>DAO_COLLECTION_FILEPATH</b>
 */
public class Save extends Command {

    public Save(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.consoleOutputout.output("Неверное количество параметров");
            return -1;
        }
        try {
            instances.fileManipulator.save(instances.dao);
            instances.consoleOutputout.output("Коллекция успешно сохранена");
            return 0;
        } catch (RuntimeException e) {
            instances.consoleOutputout.output("Не удалось сохранить коллекцию (" + e.getMessage() + ")");
        }
        return -1;

    }
}
