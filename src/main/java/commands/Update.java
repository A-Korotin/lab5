package commands;

import commands.dependencies.GetProperties;
import commands.dependencies.Instances;
import commands.dependencies.PropertiesDependant;
import dragon.Dragon;
import exceptions.InvalidValueException;
import io.OutPutter;

import java.awt.dnd.DropTarget;
import java.util.List;
import java.util.Optional;

/**
 * Класс, предназначенный для обновления существующего элемента по его <b>ID</b> (<i>обязательный аргумент команды</i>)<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public final class Update extends Command implements PropertiesDependant {

    public Update(List<String> args) {
        super(args, 1, 10);
        indexShift = 1;
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        int id;
        try{
            id = Integer.parseInt(args.get(0));
        }
        catch (RuntimeException e){
            outPutter.output("Нецелочисленный тип данных id");
            return -1;
        }

        Optional<Dragon> dragon = instances.dao.getAll().stream().filter(d -> d.getId() == id).findFirst();



        if (dragon.isEmpty()){
            outPutter.output("Элемент с id %d не существует".formatted(id));
            return -1;
        }
        if (!dragon.get().getCreatorName().equals(user.login)) {
            outPutter.output("Невозможно обновить элемент с id %d, так как он не был создан пользователем %s".formatted(id, user.login));
            return -1;
        }
        int exitCode = instances.dao.update(id, properties);

        if (exitCode == 0)
            outPutter.output("Элемент успешно обновлён");
        return exitCode;
    }
}
