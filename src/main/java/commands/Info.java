package commands;

import collection.Describable;
import com.fasterxml.jackson.core.JsonProcessingException;

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
    public int execute(Instances instances) {
        if (args.size() > 0) {
            instances.consoleOutputout.output("Неверное количество параметров");
            return -1;
        }
        try{
            instances.consoleOutputout.output(((Describable) instances.dao).description());
        }
        catch (JsonProcessingException e){
            instances.consoleOutputout.output("Что-то пошло не так...");
        }
        return 0;
    }
}
