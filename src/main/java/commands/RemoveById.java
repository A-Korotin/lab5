package commands;


import java.util.List;

/**
 * Класс, предназначенный для удаления элемента коллекции по его <b>ID</b> (<i>обязательный аргумент команды</i>)
 */
public class RemoveById extends Command {

    public RemoveById(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() != 1) {
            instances.consoleOutput.output("Неверное количество параметров");
            return -1;
        }
        int exitCode;
        try{
            if ((exitCode = instances.dao.delete(Integer.parseInt(args.get(0)))) == 0)
                instances.consoleOutput.output("Элемент успешно удален");
            else{
                instances.consoleOutput.output("Элемент не найден.");
            }
            return exitCode;
        }
        catch (RuntimeException e){
            instances.consoleOutput.output("Нецелочисленный тип данных id");
            return -1;
        }
    }
}
