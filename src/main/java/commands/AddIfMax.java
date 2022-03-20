package commands;

import dragon.Dragon;
import exceptions.InvalidValueException;
import io.Properties;

import java.util.List;
import java.util.Optional;

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

        Optional<Dragon> maxDragon = instances.dao.getAll().stream().max((d1, d2) -> (int) (d1.getAge() - d2.getAge()));

        Long maxAge = maxDragon.isPresent() ? maxDragon.get().getAge() : -1L;
        System.out.println(maxAge);

        int exitCode;
        Properties properties;

        try {
            properties = GetProperties.getProperties(askForInput, args, instances, 0);
        } catch (InvalidValueException e) {
            instances.outPutter.output(e.getMessage());
            return -1;
        }

        if (properties.age > maxAge){
            exitCode = instances.dao.create(properties);
            instances.outPutter.output("Элемент успешно добавлен");
        }
        else {
            instances.outPutter.output("Значение этого элемента меньше максимального в коллекции. Элемент не добавлен");
            return 0;
        }
        return exitCode;
    }
}
