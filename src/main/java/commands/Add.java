package commands;


import collection.DAO;
import io.ConsoleReader;
import io.request.ConsoleRequester;
import io.request.Properties;

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
        Properties properties;
        if (askForInput) {
            if (args.size() > 0) {
                instances.consoleOutputout.output("Неверное количество параметров");
                return -1;
            }
            properties = instances.consoleRequester.requestProperties();
        }
        else {
            try {
                properties = instances.properties.parseProperties(args, 0);
            } catch (Exception e) {
                instances.consoleOutputout.output(e.getMessage());
                return -1;
            }
        }
        int exitCode = instances.dao.create(properties);
        instances.consoleOutputout.output("Элемент успешно добавлен");
        return exitCode;
    }
}
