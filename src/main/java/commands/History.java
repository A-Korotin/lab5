package commands;

import collection.DAO;
import log.Logger;

import java.util.List;

public class History extends Command {

    public History(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        if (args.size() > 0) {
            //outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        for (String msg : Logger.getAll()){}
            //outPuter.outPut(msg);
        return 0;
    }
}
