package commands;

import dragon.Dragon;

import java.util.List;

/**
 * Класс, предназначенный для вывода элемента с наименьшим <b>ID</b> в заранее заданный поток вывода
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
 *
 */
public class MinById extends Command {

    public MinById(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.consoleOutputout.output("Неверное количество параметров");
            return -1;
        }
        int minId = Integer.MAX_VALUE;
        for (Dragon d : instances.dao.getAll())
            minId = d.getId() < minId ? d.getId() : minId;
        if (minId == Integer.MAX_VALUE){
            instances.consoleOutputout.output("Коллекция пуста");
            return -1;
        }
        instances.consoleOutputout.output(instances.dao.get(minId));
        return 0;
    }
}