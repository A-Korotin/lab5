package commands;

import commands.dependencies.GetProperties;
import commands.dependencies.Instances;
import commands.dependencies.PropertiesDependant;
import exceptions.InvalidValueException;
import io.Properties;

import java.util.List;
/**
 * Класс, предназначенный для добавления элемента в коллекцию<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public final class Add extends Command implements PropertiesDependant {

    public Add(List<String> args) {
        super(args, 0, 9);
    }

    @Override
    public int execute(Instances instances) {
        int exitCode;
        try{
            Properties properties = GetProperties.getProperties(askForInput,args,instances,0);
            exitCode = instances.dao.create(properties);
        }
        catch (InvalidValueException e){
            instances.outPutter.output(e.getMessage());
            exitCode = -1;
        }
        if (exitCode == 0)
            instances.outPutter.output("Элемент успешно добавлен");
        return exitCode;
    }
}
