package commands;

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
    public int execute(DAO dao) {
        if (args.size() != 1) {
            outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        if(filePathChain.contains(args.get(0))){
            outPuter.outPut("Подумай головой сначала, а потом циклы скриптов пиши. Дурак.");
            return -1;
        }
        String filePath = args.get(0);
        filePathChain.add(filePath);
        InputReader reader = new FileReader();
        reader.addProperties(filePath);
        List<Command> commands;

        try{
            commands = CommandCreator.getCommands(reader);
        }
        catch(RuntimeException e){
            outPuter.outPut(e.getMessage());
            return -1;
        }
        outPuter.outPut("Все команды были распознаны и поданы на выполнение");

        int exitCode = 0;
        for (Command c : commands)
            exitCode += c.execute(dao);
        return exitCode;
    }
}