package commands;


import java.util.List;

/**
 * Класс, предназначенный для вывода количества элементов с заданным <b>возрастом</b> (<i>обязательный аргумент команды</i>)
 */
public class CountByAge extends Command {

    public CountByAge(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        if (args.size() != 1) {
            outPuter.outPut("Неверное количество параметров");
            return -1;
        }
        Long age;
        try{
            age = Long.parseLong(args.get(0));
        }
        catch(RuntimeException e){
            outPuter.outPut("Типы данных не совпали");
            return -1;
        }

        int ageCount = 0;
        for (Dragon dragon : dao.getAll()) {
            if (dragon.getAge().equals(age)) {
                ageCount++;
            }
        }
        outPuter.outPut(ageCount);
        return 0;
    }
}
