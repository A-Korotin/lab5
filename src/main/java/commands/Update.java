package commands;

import dragon.Dragon;
import io.request.Properties;

import java.util.List;

/**
 * Класс, предназначенный для обновления существующего элемента по его <b>ID</b> (<i>обязательный аргумент команды</i>)<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public class Update extends Command {

    public Update(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() != 1) {
            instances.outPutter.output("Неверное количество параметров");
            return -1;
        }
        int id;
        try{
            id = Integer.parseInt(args.get(0));
        }
        catch (RuntimeException e){
            instances.outPutter.output("Нецелочисленный тип данных id");
            return -1;
        }
        boolean found = false;
        for(Dragon d: instances.dao.getAll()) {
            if(d.getId() == id) {
                found = true;
                break;
            }
        }
        if (!found){
            instances.outPutter.output("Элемент с id %d не существует".formatted(id));
            return -1;
        }

        int exitCode;
        try{
            exitCode = instances.dao.update(id, GetProperties.getProperties(askForInput,args,instances,1));
        }
        catch(RuntimeException e){
            instances.outPutter.output(e.getMessage());
            exitCode = -1;
        }
        if (exitCode == 0)
            instances.outPutter.output("Элемент успешно обновлён");
        return exitCode;
    }
}
