package commands;

import commands.dependencies.GetProperties;
import commands.dependencies.Instances;
import commands.dependencies.PropertiesDependant;
import dragon.Dragon;
import exceptions.InvalidValueException;
import io.OutPutter;
import io.Properties;

import java.util.List;
import java.util.Optional;

/**
 * Класс, предназначенный для добавления элемента в коллекцию, если <b>возраст</b> нового элемента больше возраста всех существующих элементов<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public final class AddIfMax extends Command implements PropertiesDependant {

    public AddIfMax(List<String> args) {
        super(args, 0, 9);
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        properties.creator_name = user.login;
        Optional<Dragon> maxDragon = instances.dao.getAll().stream().max((d1, d2) -> (int) (d1.getAge() - d2.getAge()));

        Long ageMax = maxDragon.isPresent() ? maxDragon.get().getAge() : -1L;

        int exitCode;

        if (properties.age > ageMax){
            exitCode = instances.dao.create(properties);
            outPutter.output("Элемент успешно добавлен");
        }
        else {
            outPutter.output("Значение этого элемента меньше максимального в коллекции. Элемент не добавлен");
            return 0;
        }
        return exitCode;
    }
}
