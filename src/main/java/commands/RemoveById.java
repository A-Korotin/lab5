package commands;


import commands.dependencies.Instances;
import dragon.Dragon;
import io.OutPutter;

import java.util.List;

/**
 * Класс, предназначенный для удаления элемента коллекции по его <b>ID</b> (<i>обязательный аргумент команды</i>)
 */
public final class RemoveById extends Command {

    public RemoveById(List<String> args) {
        super(args, 1);
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        int exitCode;
        try{
            int id = Integer.parseInt(args.get(0));
            Dragon toRemove = instances.dao.get(id);
            if(toRemove != null && !toRemove.getCreatorName().equals(user.login)) {
                outPutter.output("Элемент с id %d не был создан пользователем %s. Удаление невозможно".formatted(id, user.login));
                return -1;
            }

            if ((exitCode = instances.dao.delete(id)) == 0)
                outPutter.output("Элемент успешно удален");
            else{
                outPutter.output("Элемент не найден.");
            }
            return exitCode;
        }
        catch (RuntimeException e){
            outPutter.output("\"%s\" не является Integer".formatted(args.get(0)));
            return -1;
        }
    }
}
