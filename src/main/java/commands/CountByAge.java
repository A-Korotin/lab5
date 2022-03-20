package commands;


import dragon.Dragon;

import java.util.List;

/**
 * Класс, предназначенный для вывода количества элементов с заданным <b>возрастом</b> (<i>обязательный аргумент команды</i>)
 */
public final class CountByAge extends Command {

    public CountByAge(List<String> args) {
        super(args, 1);
    }

    @Override
    public int execute(Instances instances) {
        Long age;
        try{
            age = Long.parseLong(args.get(0));
        }
        catch(RuntimeException e){
            instances.outPutter.output("Типы данных не совпали");
            return -1;
        }

        int ageCount = 0;
        for (Dragon dragon : instances.dao.getAll()) {
            if (dragon.getAge().equals(age)) {
                ageCount++;
            }
        }
        instances.outPutter.output(ageCount);
        return 0;
    }
}
