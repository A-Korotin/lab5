package commands;

import collection.DAO;
import io.InputReader;

import java.util.List;

/**
 * Класс, предназначенный для выполнения скрипта (<i>последовательности команд</i>) из файла <br>
 * Путь до искомого файла является обязательным аргументом команды
 */
public class ExecuteScript extends Command {
    public ExecuteScript(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() != 1) {
            instances.consoleOutputout.output("Неверное количество параметров");
            return -1;
        }
        if(Instances.filePathChain.contains(args.get(0))){
            instances.consoleOutputout.output("Подумай головой сначала, а потом циклы скриптов пиши. Дурак.");
            return -1;
        }
        String filePath = args.get(0);
        Instances.filePathChain.add(filePath);
        InputReader reader = instances.fileReader;
        reader.addProperties(filePath);
        List<Command> commands;

        try{
            commands = CommandCreator.getCommands(reader);
        }
        catch(RuntimeException e){
            instances.consoleOutputout.output(e.getMessage());
            return -1;
        }
        instances.consoleOutputout.output("Все команды были распознаны и поданы на выполнение");

        int exitCode = 0;
        for (Command c : commands)
            exitCode += c.execute(instances);
        return exitCode;
    }
}