package commands;


import collection.DAO;
import io.ConsoleReader;
import io.request.ConsoleRequester;
import io.request.Properties;

import java.awt.*;
import java.io.Console;
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
            exitCode = instances.dao.create(GetProperties.getProperties(askForInput,args,instances,0));
        }
        catch (RuntimeException e){
            instances.consoleOutputout.output(e.getMessage());
            exitCode = -1;
        }
        if (exitCode == 0)
            instances.consoleOutputout.output("Элемент успешно добавлен");
        return exitCode;
    }
}
