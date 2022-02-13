import java.util.*;

public abstract class Command {
    protected List<String> args;
    protected boolean askForInput;

    public void setAskForInput(boolean ask) {
        askForInput = ask;
    }

    public Command(List<String> args) {
        this.args=args;
    }

    public abstract int execute(DAO dao, OutPuter outPuter);

}

class Help extends Command {

    public Help(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        /* TODO commands description */
        outPuter.outPut("""
                help : вывести справку по доступным командам
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                add {element} : добавить новый элемент в коллекцию
                update id {element} : обновить значение элемента коллекции, id которого равен заданному
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                save : сохранить коллекцию в файл
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                exit : завершить программу (без сохранения в файл)
                add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
                sort : отсортировать коллекцию в естественном порядке
                history : вывести последние 6 команд (без их аргументов)
                min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным
                count_by_age age : вывести количество элементов, значение поля age которых равно заданному
                filter_greater_than_character character : вывести элементы, значение поля character которых больше заданного""");
        return 0;
    }

}

class Info extends Command {

    public Info(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        // TODO info
        return 0;
    }
}

class Show extends Command {

    public Show(List<String> args) {
        super(args);
    }

    public int execute(DAO dao, OutPuter outPuter) {
        for (Dragon d: dao.getAll())
            System.out.println(d);

        return 0;
    }
}

class Add extends Command {

    public Add(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        if (askForInput)
            dao.create(new Dragon(new ConsoleRequester().request()));
        return 0;


    }
}

class Update extends Command {

    public Update(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        // TODO
        int id = Integer.getInteger(this.args.get(0));
        dao.update(null);
        return 0;
    }
}

class RemoveById extends Command {

    public RemoveById(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        dao.delete(Integer.getInteger(args.get(0)));
        return 0;
    }
}

class Clear extends Command {

    public Clear(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        dao.clear();
        return 0;
    }
}

class Save extends Command {

    public Save(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        // TODO CollectionManipulator
        return 0;
    }
}

class ExecuteScript extends Command {
    public ExecuteScript(List<String> args){
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        String filePath = args.get(0);
        InputReader reader = new FileReader();
        reader.addProperties(filePath);

        List<Command> commands = CommandCreator.getCommands(reader);

        int exitCode = 0;

        for(Command c: commands)
            exitCode += c.execute(dao, outPuter);

        return exitCode;
    }
}

class Exit extends Command {

    public Exit(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        System.exit(0);
        return 0;
    }
}

class AddIfMax extends Command {

    public AddIfMax(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        //TODO ...
        return 0;
    }
}

class Sort extends Command {

    public Sort(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        // TODO dao.sort()
        return 0;
    }
}

class History extends Command {

    public History(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        //TODO ...
        return 0;
    }
}

class MinById extends Command {

    public MinById(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        int minId = Integer.MAX_VALUE;
        for(Dragon d: dao.getAll())
            minId = d.getId() < minId? d.getId(): minId;
        outPuter.outPut(dao.get(minId));
        return 0;
    }
}

class CountByAge extends Command {

    public CountByAge(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        int age = Integer.getInteger(args.get(0));
        int ageCount = 0;
        for (Dragon dragon : dao.getAll()) {
            if (dragon.getAge() == age) {
                ageCount++;
            }
        }
        return ageCount;
    }
}
class FilterGreaterThanCharacter extends Command {

    public FilterGreaterThanCharacter(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        //TODO ...
        return 0;
    }
}