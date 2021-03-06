package commands;


import commands.dependencies.Instances;
import dragon.Dragon;
import dragon.DragonCharacter;
import io.OutPutter;

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
    public int execute(Instances instances, OutPutter outPutter) {
        DragonCharacter character;
        try {

            character = args.get(0).equalsIgnoreCase("null")? null : DragonCharacter.valueOf(args.get(0).toUpperCase());
        }
        catch (RuntimeException e){
            outPutter.output("Характер не определён");
            return -1;
        }
        long count = 0;
        count = instances.dao.getAll().stream()
                .filter(dragon -> DragonCharacter.compareBoolean(dragon.getCharacter(), character)).count();
        if (count == 0) {
            outPutter.output("Нет подходящих элементов");
        } else {
            instances.dao.getAll().stream()
                    .filter(dragon -> DragonCharacter.compareBoolean(dragon.getCharacter(), character))
                    .forEach(outPutter::output);
        }

        return 0;
    }
}
