package commands;

import io.request.Properties;

import java.util.List;
/**
 * Класс, предназначенный для добавления элемента в коллекцию<br>
 * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
 * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
 */
public class Add extends Command {

    public Add(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        int exitCode;
        try{
            Properties properties = GetProperties.getProperties(askForInput,args,instances,0);
            exitCode = instances.dao.create(properties);
        }
        catch (RuntimeException e){
            instances.outPutter.output(e.getMessage());
            exitCode = -1;
        }
        if (exitCode == 0)
            instances.outPutter.output("Элемент успешно добавлен");
        return exitCode;
    }
}
