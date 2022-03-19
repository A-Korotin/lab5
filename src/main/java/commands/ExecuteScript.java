package commands;

import io.InputReader;
import log.Logger;

import java.util.List;

/**
 * Класс, предназначенный для выполнения скрипта (<i>последовательности команд</i>) из файла <br>
 * Путь до искомого файла является обязательным аргументом команды
 */
public class ExecuteScript extends Command {
    public ExecuteScript(List<String> args) {
        super(args, 1);
    }

    @Override
    public int execute(Instances instances) {
        if(Instances.filePathChain.contains(args.get(0))){
            instances.outPutter.output("Подумай головой сначала, а потом циклы скриптов пиши. Дурак.");
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
            instances.outPutter.output("Одна или несколько команд не были распознаны");
            return -1;
        }
        instances.outPutter.output("Все команды были распознаны и поданы на выполнение");

        int exitCode = 0;
        for (Command c : commands) {
            exitCode += c.execute(instances);
        }
        return exitCode;
    }
}