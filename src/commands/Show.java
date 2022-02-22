package commands;

import java.util.List;

/**
 * Класс, предназначенный для вывода информации о всех элементах коллекции в формате <b>JSON</b><br>
 *
 * <table align="left" border="1">
 *     <thead>Формат вывода элемента:</thead>
 *     <tr><i>ID</i></tr>
 *     <tr><i>Имя</i></tr>
 *     <tr><i>Координаты</i></tr>
 *     <tr><i>Дата инициализации(с точностью до дней)</i></tr>
 *     <tr><i>Возраст</i></tr>
 *     <tr><i>Цвет</i></tr>
 *     <tr><i>Тип</i></tr>
 *     <tr><i>Характер</i></tr>
 *     <tr><i>Информация о пещере</i></tr>
 * </table>
 */
public class Show extends Command {

    public Show(List<String> args) {
        super(args);
    }

    public int execute(DAO dao) {
        if (args.size() > 0) {
            outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        List<Dragon> dragons = dao.getAll();
        if (dragons.size() == 0) {
            outPuter.outPut("пусто");
            return 0;
        }
        for (Dragon d : dragons)
            outPuter.outPut(d);
        return 0;
    }
}
