package commands;

import collection.Orderable;
import commands.dependencies.Instances;
import io.OutPutter;

import java.util.List;

/**
 * Класс, предназначенный для сортировки коллекции. Сортировка производится по <i>возрастанию</i> поля <b>"возраст"</b>
 */
public final class Sort extends Command {

    public Sort(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        int exitCode = ((Orderable)instances.dao).sort();
        outPutter.output("Коллекция успешно отсортирована");
        return exitCode;
    }
}