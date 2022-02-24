package commands;

import collection.DAO;

import java.util.List;

/**
 * Класс, предназначенный для удаления элемента коллекции по его <b>ID</b> (<i>обязательный аргумент команды</i>)
 */
public class RemoveById extends Command {

    public RemoveById(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        if (args.size() != 1) {
            //outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        int exitCode;
        try{
            if ((exitCode = dao.delete(Integer.parseInt(args.get(0)))) == 0){}
                //outPuter.outPut("Элемент успешно удален");
            else{}
                //outPuter.outPut("Элемент не найден.");
            return exitCode;
        }
        catch (RuntimeException e){
            //outPuter.outPut("Нецелочисленный тип данных id");
            return -1;
        }
    }
}
