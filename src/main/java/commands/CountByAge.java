package commands;


import dragon.Dragon;

import java.util.List;

/**
 * Класс, предназначенный для вывода количества элементов с заданным <b>возрастом</b> (<i>обязательный аргумент команды</i>)
 */
public class CountByAge extends Command {

    public CountByAge(List<String> args) {
        super(args);
    }

    @Override
    public int execute(Instances instances) {
        if (args.size() != 1) {
            instances.consoleOutput.output("Неверное количество параметров");
            return -1;
        }
        Long age;
        try{
            age = Long.parseLong(args.get(0));
        }
        catch(RuntimeException e){
            instances.consoleOutput.output("Типы данных не совпали");
            return -1;
        }

        int ageCount = 0;
        for (Dragon dragon : instances.dao.getAll()) {
            if (dragon.getAge().equals(age)) {
                ageCount++;
            }
        }
        instances.consoleOutput.output(ageCount);
        return 0;
    }
}
