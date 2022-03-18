package commands;


import java.util.List;

/**
 * Класс, предназначенный для завершения работы программы в штатном режиме (<i>без сохранения изменений в коллекции</i>)
 *
 */
public class Exit extends Command {

    public Exit(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.outPutter.output("Неверное количество параметров");
            return -1;
        }
        System.exit(0);
        return 0;
    }
}
