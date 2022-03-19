package commands;

import dragon.Dragon;
import io.request.Properties;

import java.util.List;

/**
 * Класс, предназначенный для добавления элемента в коллекцию, если <b>возраст</b> нового элемента больше возраста всех существующих элементов<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public class AddIfMax extends Command {

    public AddIfMax(List<String> args) {
        super(args, 0, 8);
    }

    @Override
    public int execute(Instances instances) {
        Long ageMax = -1L;
        for (Dragon dragon : instances.dao.getAll()) {
            if (dragon.getAge() > ageMax) {
                ageMax = dragon.getAge();
            }
        }
        int exitCode;
        Properties properties;
        try {
            properties = GetProperties.getProperties(askForInput, args, instances, 0);

            if (properties.age > ageMax) {
                exitCode = instances.dao.create(properties);
            } else {
                exitCode = 0;
            }
        } catch (RuntimeException e) {
            instances.outPutter.output(e.getMessage());
            return -1;
        }
        if (properties.age > ageMax){
            instances.dao.create(properties);
            instances.outPutter.output("Элемент успешно добавлен");
        }
        else {
            instances.outPutter.output("Значение этого элемента меньше максимального в коллекции. Элемент не добавлен");
        }
        return exitCode;
    }
}
