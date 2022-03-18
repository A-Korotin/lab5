package commands;

import log.Logger;

import java.util.List;

public class History extends Command {

    public History(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.consoleOutput.output("Неверное количество параметров");
            return -1;
        }
        for (String msg : Logger.getAll()) {
            instances.consoleOutput.output(msg);
        }
        return 0;
    }
}
