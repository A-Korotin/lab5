package commands;


import commands.dependencies.Instances;
import dragon.Dragon;
import io.OutPutter;

import java.util.List;

/**
 * Класс, предназначенный для вывода количества элементов с заданным <b>возрастом</b> (<i>обязательный аргумент команды</i>)
 */
public final class CountByAge extends Command {

    public CountByAge(List<String> args) {
        super(args, 1);
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        Long age;
        try{
            age = Long.parseLong(args.get(0));
        }
        catch(RuntimeException e){
            outPutter.output("Типы данных не совпали");
            return -1;
        }
        Long ageCount = instances.dao.getAll().stream().filter(dragon -> dragon.getAge().equals(age)).count();

        outPutter.output(ageCount);
        return 0;
    }
}
