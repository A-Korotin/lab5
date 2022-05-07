package commands;

import collection.Describable;
import commands.dependencies.Instances;
import exceptions.SavedToTmpFileException;
import io.FileManipulator;
import io.OutPutter;

import java.util.List;

/**
 * Класс, предназначенный для сохранения информации о коллекции в файл формата <b>JSON</b><br>
 * Путь до искомого файла передается через <i>переменную окружения</i> <b>DAO_COLLECTION_FILEPATH</b>
 */
public final class Save extends Command {

    public Save(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        try {
            FileManipulator.save(((Describable) instances.dao));
            outPutter.output("Коллекция успешно сохранена");
            return 0;
        } catch (SavedToTmpFileException e) {
            outPutter.output(e.getMessage());
            return 0;
        }
        catch (RuntimeException e) {
            outPutter.output("Не удалось сохранить коллекцию (" + e.getMessage() + ")");
        }
        return -1;

    }
}
