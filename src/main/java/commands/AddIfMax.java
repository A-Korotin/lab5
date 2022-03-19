package commands;

import dragon.Dragon;
import exceptions.InvalidValueException;
import io.Properties;

import java.util.List;

/**
 * Класс, предназначенный для добавления элемента в коллекцию, если <b>возраст</b> нового элемента больше возраста всех существующих элементов<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public class AddIfMax extends Command {

    public AddIfMax(List<String> args) {
        super(args, 0, 9);
    }

    @Override
    public int execute(Instances instances) {
        Long ageMax = -1L;
        for (Dragon dragon : instances.dao.getAll()) {
            if (dragon.getAge() > ageMax) {
                ageMax = dragon.getAge();
            }
        }
        int exitCode = 0;
        try{
            Properties properties = GetProperties.getProperties(askForInput,args,instances,0);

            if (properties.age > ageMax){
                exitCode = instances.dao.create(properties);
            }
            else {
                exitCode = 1;
            }
        }
        catch (RuntimeException e){
            instances.consoleOutput.output(e.getMessage());
            exitCode = -1;
        }
        if (exitCode == 0)
            instances.consoleOutput.output("Элемент успешно добавлен");
        if (exitCode == 1)
            instances.consoleOutput.output("Значение этого элемента меньше максимального в коллекции. Элемент не добавлен");
        return exitCode;
    }
}
