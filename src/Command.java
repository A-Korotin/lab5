import java.time.LocalDate;
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
        outPuter.outPut("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "sort : отсортировать коллекцию в естественном порядке\n" +
                "history : вывести последние 6 команд (без их аргументов)\n" +
                "min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным\n" +
                "count_by_age age : вывести количество элементов, значение поля age которых равно заданному\n" +
                "filter_greater_than_character character : вывести элементы, значение поля character которых больше заданного");
        return 0;
    }

}

class Info extends Command {

    public Info(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        outPuter.outPut(dao.getJSONDescription().toString());
        return 0;
    }
}

class Show extends Command {

    public Show(List<String> args) {
        super(args);
    }

    public int execute(DAO dao, OutPuter outPuter) {
        for (Dragon d: dao.getAll())
            outPuter.outPut(d);
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
        else
            dao.create(new Dragon(
                    args.get(0), // name
                    new Coordinates(Float.parseFloat(args.get(1)), Integer.parseInt(args.get(2))), // Coordinates
                    LocalDate.now(), // creation date
                    Long.parseLong(args.get(3)), // age
                    Color.valueOf(args.get(4)), // color
                    DragonType.valueOf(args.get(5)), // type
                    DragonCharacter.valueOf(args.get(6)), // character
                    new DragonCave(Double.parseDouble(args.get(7)), Integer.parseInt(args.get(8))) // cave
                    ));

            outPuter.outPut("Элемент успешно добавлен");
        return 0;
    }
}

class Update extends Command {

    public Update(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
    // TODO update
        int id = Integer.parseInt(args.get(0));
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
        int exitCode;
        if((exitCode = dao.delete(Integer.getInteger(args.get(0)))) == 1)
            outPuter.outPut("Элемент успешно удален");
        else
            outPuter.outPut("Элемент не найден.");
        return exitCode;
    }
}

class Clear extends Command {

    public Clear(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        dao.clear();
        outPuter.outPut("Коллекция успешно очищена");
        return 0;
    }
}

class Save extends Command {

    public Save(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        try {
            new FileManipulator().save(dao);
            outPuter.outPut("Коллекция успешно сохранена");
            return 0;
        } catch (RuntimeException e) {
            outPuter.outPut("Не удалось сохранить коллекцию (" + e.getMessage() + ")");
        }
        return -1;

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
        dao.sort();
        outPuter.outPut("Коллекция успешно отсортирована");
        return 0;
    }
}

class History extends Command {

    public History(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao, OutPuter outPuter) {
        for (String msg: Logger.getAll())
            outPuter.outPut(msg);
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