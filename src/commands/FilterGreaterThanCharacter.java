package commands;


import java.util.List;

/**
 * Класс, предназначенный для вывода элементов, значение поля <b>характер</b> которых больше заданного (<i>обязательный агрумент команды</i>)<br>
 * Сравнение характеров происходит по <b>длине названия</b> характера
 *
 */
public class FilterGreaterThanCharacter extends Command {

    public FilterGreaterThanCharacter(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        DragonCharacter character;
        if (args.size() != 1) {
            outPuter.outPut("Неверное количество параметров");
            return -1;
        }

        try{

            character = args.get(0).equals("null")? null : DragonCharacter.valueOf(args.get(0));
        }
        catch (RuntimeException e){
            outPuter.outPut("Характер не определён");
            return -1;
        }
        for (Dragon dragon: dao.getAll()){
            if (DragonCharacter.compare(dragon.getCharacter(), character) > 0){
                outPuter.outPut(dragon);
            }
        }
        return 0;
    }
}
