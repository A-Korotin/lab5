package commands;


import collection.DAO;

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
    public int execute(DAO dao) {
        if (args.size() > 0) {
            //outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        System.exit(0);
        return 0;
    }
}
