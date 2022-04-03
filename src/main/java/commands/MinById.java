package commands;

import commands.dependencies.Instances;
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
public final class MinById extends Command {

    public MinById(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances) {
        int minId = Integer.MAX_VALUE;
        for (Dragon d : instances.dao.getAll())
            minId = d.getId() < minId ? d.getId() : minId;
        if (minId == Integer.MAX_VALUE){
            instances.outPutter.output("Коллекция пуста");
            return 0;
        }
        instances.outPutter.output(instances.dao.get(minId));
        return 0;
    }
}