import collection.DragonDAO;
import commands.Command;
import commands.CommandCreator;
import commands.Instances;
import exceptions.InvalidArgsSizeException;
import exceptions.ProgramExitException;
import io.ConsoleOutput;
import io.ConsoleReader;
import io.FileManipulator;
import io.FileReader;
import io.request.ConsoleRequester;
import log.Logger;

import java.util.List;

public final class MainLayer {
    private final Instances instances = new Instances();

    public MainLayer() {
        instances.outPutter = new ConsoleOutput();

        try {
            instances.dao = FileManipulator.get();
        } catch (RuntimeException e) {
            instances.outPutter.output(e.getMessage());
            instances.dao = new DragonDAO();
        }

        instances.consoleReader = new ConsoleReader();
        instances.consoleRequester = new ConsoleRequester();
        instances.fileReader = new FileReader();

    }

    public void run() {
        instances.outPutter.output("Введите команду");

        while (true) {
            try {
                loopBody();
            } catch (ProgramExitException e) {
                instances.outPutter.output(e.getMessage());
                break;
            }
        }

    }

    private void loopBody() {
        List<Command> commands;
        try {
            commands = CommandCreator.getCommands(instances.consoleReader);
        } catch (InvalidArgsSizeException e) {
            instances.outPutter.output(e.getMessage());
            return;
        } catch (RuntimeException e) {
            instances.outPutter.output("Такой команды не существует. Введите help для подробной информации");
            return;
        }

        int exit;
        for (Command c: commands) {
            if (c.getName().equals("execute_script"))
                Instances.filePathChain.clear();


            if ((exit = c.execute(instances)) != 0)
                instances.outPutter.output("Команда %s не была выполнена корректно. Код выхода %d".formatted(c.getName(), exit));
            exit = 0;
        }
    }
}
