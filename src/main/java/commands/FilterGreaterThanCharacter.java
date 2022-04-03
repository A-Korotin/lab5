package commands;


import commands.dependencies.Instances;
import dragon.Dragon;
import dragon.DragonCharacter;

import java.util.List;

/**
 * Класс, предназначенный для вывода элементов, значение поля <b>характер</b> которых больше заданного (<i>обязательный агрумент команды</i>)<br>
 * Сравнение характеров происходит по <b>длине названия</b> характера
 *
 */
public final class FilterGreaterThanCharacter extends Command {

    public FilterGreaterThanCharacter(List<String> args) {
        super(args, 1);
    }

    @Override
    public int execute(Instances instances) {
        DragonCharacter character;
        try {

            character = args.get(0).equalsIgnoreCase("null")? null : DragonCharacter.valueOf(args.get(0).toUpperCase());
        }
        catch (RuntimeException e){
            instances.outPutter.output("Характер не определён");
            return -1;
        }

        instances.dao.getAll().stream()
                .filter(dragon -> DragonCharacter.compareBoolean(dragon.getCharacter(), character))
                .forEach(dragon -> instances.outPutter.output(dragon));

        return 0;
    }
}
