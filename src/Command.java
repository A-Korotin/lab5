import java.util.*;

public abstract class Command {
    protected List<String> args;

    public Command(List<String> args) {
        this.args=args;
    }

    public abstract int execute(DAO dao);

}

class Help extends Command {

    public Help(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        //TODO commands description
        System.out.println("");
        return 0;
    }

}

class Info extends Command {

    public Info(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        // TODO info
        return 0;
    }
}

class Show extends Command {

    public Show(List<String> args) {
        super(args);
    }

    public int execute(DAO dao) {
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
    public int execute(DAO dao) {
        //dao.create(new Dragon(args.get(0), );
        return 0;
    }
}

class Update extends Command {

    public Update(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
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
    public int execute(DAO dao) {
        dao.delete(Integer.getInteger(args.get(0)));
        return 0;
    }
}

class Clear extends Command {

    public Clear(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        dao.clear();
        return 0;
    }
}

class Save extends Command {

    public Save(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        // TODO CollectionManipulator
        return 0;
    }
}

class ExecuteScript extends Command {
    public ExecuteScript(List<String> args){
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        String filePath = args.get(0);
        InputReader reader = new FileReader();
        reader.addProperties(filePath);

        List<Command> commands = CommandCreator.getCommands(reader);

        int exitCode = 0;

        for(Command c: commands)
            exitCode += c.execute(dao);

        return exitCode;
    }
}

class Exit extends Command {

    public Exit(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        System.exit(0);
        return 0;
    }
}

class AddIfMax extends Command {

    public AddIfMax(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        //TODO ...
        return 0;
    }
}

class Sort extends Command {

    public Sort(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        // TODO dao.sort()
        return 0;
    }
}

class History extends Command {

    public History(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        //TODO ...
        return 0;
    }
}

class MinById extends Command {

    public MinById(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
        int minId = Integer.MAX_VALUE;
        for(Dragon d: dao.getAll())
            minId = d.getId() < minId? d.getId(): minId;

        System.out.println(dao.get(minId));
        return 0;
    }
}

class CountByAge extends Command {

    public CountByAge(List<String> args) {
        super(args);
    }

    @Override
    public int execute(DAO dao) {
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
    public int execute(DAO dao) {
        //TODO ...
        return 0;
    }
}