package commands;


import java.util.List;
import exceptions.ProgramExitException;
/**
 * Класс, предназначенный для завершения работы программы в штатном режиме (<i>без сохранения изменений в коллекции</i>)
 *
 */
public class Exit extends Command {

    public Exit(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances) {
        throw new ProgramExitException("Завершение программы...");
    }
}
