package commands;


import dragon.Dragon;

import java.util.List;

/**
 * Класс, предназначенный для вывода количества элементов с заданным <b>возрастом</b> (<i>обязательный аргумент команды</i>)
 */
public class CountByAge extends Command {

    public CountByAge(List<String> args) {
        super(args, 1);
    }

    @Override
    public int execute(Instances instances) {
        long age;
        try{
            age = Long.parseLong(args.get(0));
        }
        catch(RuntimeException e){
            instances.outPutter.output("\"%s\" не является Long".formatted(args.get(0)));
            return -1;
        }

        long amount = instances.dao.getAll().stream().filter(dragon -> dragon.getAge().equals(age)).count();

        instances.outPutter.output(amount);
        return 0;
    }
}
