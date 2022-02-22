package commands;

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
    public int execute(DAO dao) {
        if (args.size() > 0) {
            outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        try {
            manipulator.save(dao);
            outPuter.outPut("Коллекция успешно сохранена");
            return 0;
        } catch (RuntimeException e) {
            outPuter.outPut("Не удалось сохранить коллекцию (" + e.getMessage() + ")");
        }
        return -1;

    }
}
