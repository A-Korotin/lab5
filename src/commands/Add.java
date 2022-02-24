package commands;


import collection.DAO;
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
    public int execute(DAO dao) {
        Properties properties;
        if (askForInput) {
            if (args.size() > 0) {
                //outPuter.outPut("Неверное количество параметров");
                return -1;
            }
            //properties = ConsoleRequester.request();
        }
        else {
            try {
                //properties = Properties.parseProperties(args, 0);
            } catch (RuntimeException e) {
                //outPuter.outPut(e.getMessage());
                return -1;
            }
        }
        int exitCode = dao.create(properties);
        //outPuter.outPut("Элемент успешно добавлен");
        return exitCode;
    }
}
