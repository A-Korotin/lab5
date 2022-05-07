package commands;

import commands.dependencies.Instances;
import io.OutPutter;
import log.Logger;

import java.util.List;

public final class History extends Command {

    public History(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        Logger.getAll().forEach(outPutter::output);

        return 0;
    }
}
