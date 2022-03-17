package commands;

import collection.DAO;
import log.Logger;

import java.util.List;

public class History extends Command {

    public History(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.outPutter.output("Неверное количество параметров");
            return -1;
        }
        for (String msg : Logger.getAll()) {
            instances.outPutter.output(msg);
        }
        return 0;
    }
}
