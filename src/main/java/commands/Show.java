package commands;

import dragon.Dragon;

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

    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.consoleOutput.output("Неверное количество параметров");
            return -1;
        }
        List<Dragon> dragons = instances.dao.getAll();
        if (dragons.size() == 0) {
            instances.consoleOutput.output("пусто");
            return 0;
        }
        for (Dragon d : dragons) {
            instances.consoleOutput.output(d);
        }
        return 0;
    }
}
