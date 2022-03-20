package commands;

import log.Logger;

import java.util.List;

public class History extends Command {

    public History(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances) {
        Logger.getAll().forEach(instances.outPutter::output);

        return 0;
    }
}
