package commands;


import commands.dependencies.Instances;

import java.util.List;

/**
 * Класс, предназначенный для удаления элемента коллекции по его <b>ID</b> (<i>обязательный аргумент команды</i>)
 */
public final class RemoveById extends Command {

    public RemoveById(List<String> args) {
        super(args, 1);
    }

    @Override
    public int execute(Instances instances) {
        int exitCode;
        try{
            if ((exitCode = instances.dao.delete(Integer.parseInt(args.get(0)))) == 0)
                instances.outPutter.output("Элемент успешно удален");
            else{
                instances.outPutter.output("Элемент не найден.");
            }
            return exitCode;
        }
        catch (RuntimeException e){
            instances.outPutter.output("\"%s\" не является Integer".formatted(args.get(0)));
            return -1;
        }
    }
}
