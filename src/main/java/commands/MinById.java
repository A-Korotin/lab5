package commands;

import dragon.Dragon;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

        Optional<Dragon> minDragon = instances.dao.getAll().stream().min(Comparator.comparingInt(Dragon::getId));

        Integer minId = minDragon.isPresent() ? minDragon.get().getId() : null;

        if (minId == null)
            instances.outPutter.output("Коллекция пуста");
        else
            instances.outPutter.output(minId);


        return 0;
    }
}