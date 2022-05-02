package commands;


import commands.dependencies.Instances;
import dragon.Dragon;

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
            int id = Integer.parseInt(args.get(0));
            Dragon toRemove = instances.dao.get(id);
            if(toRemove != null && !toRemove.getCreatorName().equals(userName)) {
                instances.outPutter.output("Элемент с id %d не был создан пользователем %s. Удаление невозможно".formatted(id, userName));
                return -1;
            }

            if ((exitCode = instances.dao.delete(id)) == 0)
                instances.outPutter.output("Элемент успешно удален");
            else{
                instances.outPutter.output("Элемент не найден.");
            }
            return exitCode;
        }
        catch (RuntimeException e){
            instances.outPutter.output("Нецелочисленный тип данных id");
            return -1;
        }
    }
}
