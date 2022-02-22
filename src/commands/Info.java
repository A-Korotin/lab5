package commands;

import java.util.List;

/**
 * Класс, предназначенный для вывода информации о коллекции в ранее заданный поток вывода в формате <b>JSON</b>
 *
 * <table align="left" border="1">
 *     <thead>Формат вывода информации:</thead>
 *     <tr><i>Тип коллекции</i></tr>
 *     <tr><i>Количество элементов в коллекции</i></tr>
 *     <tr><i>Дата инициализации коллекции (с точностью до секунд)</i></tr>
 *     <tr><i>Последний доступный id для элементов коллекции</i></tr>
 *     <tr><i>Элементы коллекции</i></tr>
 * </table>
 */
public class Info extends Command {

    public Info(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        if (args.size() > 0) {
            outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        outPuter.outPut(dao.getJSONDescription().toString());
        return 0;
    }
}
