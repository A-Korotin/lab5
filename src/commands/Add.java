package commands;


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
        DragonProperties properties;
        if (askForInput) {
            if (args.size() > 0) {
                outPuter.outPut("Неверное количество параметров");
                return -1;
            }
            properties = requester.request();
        }
        else {
            try {
                properties = DragonProperties.parseProperties(args, 0);
            } catch (RuntimeException e) {
                outPuter.outPut(e.getMessage());
                return -1;
            }
        }
        int exitCode = dao.create(properties);
        outPuter.outPut("Элемент успешно добавлен");
        return exitCode;
    }
}
