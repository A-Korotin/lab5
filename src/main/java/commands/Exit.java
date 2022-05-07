package commands;


import java.util.List;

import commands.dependencies.Instances;
import exceptions.ProgramExitException;
import io.OutPutter;

/**
 * Класс, предназначенный для завершения работы программы в штатном режиме (<i>без сохранения изменений в коллекции</i>)
 *
 */
public final class Exit extends Command {

    public Exit(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        throw new ProgramExitException("Завершение программы...");
    }
}
