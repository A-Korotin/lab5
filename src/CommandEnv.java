import java.time.LocalDate;
import java.util.*;

enum Destination {
    file, console, // server, etc...
}


public class CommandEnv {

    public static void mainLoop() {
        if (outPuter == null || manipulator == null || requester == null) {
            new ConsoleOutPut().outPut("Командная среда не инициализирована.");
            return;
        }
        InputReader reader = new ConsoleReader();
        DAO dao = manipulator.get();

        int exitCode;
        List<Command> commands;
        while (true) {
            try {
                commands = CommandCreator.getCommands(reader);
            } catch (RuntimeException e) {
                outPuter.outPut("Команда не найдена. Для подробной информации введите help.");
                continue;
            }
            for(Command c: commands) {

                if ((exitCode = c.execute(dao)) != 0)
                    outPuter.outPut("Команда %s не была успешно выполнена. Код выхода: %d".formatted(c.name, exitCode));
            }
        }
    }

    private CommandEnv() {}

    private static OutPuter outPuter;
    private static CollectionManipulator manipulator ;
    private static PropertiesRequester requester;


    public static void setOutputDestination(Destination destination) {
        switch (destination) {
            case file -> outPuter = new FileOutPut();
            case console -> outPuter = new ConsoleOutPut();
        }
    }

    public static void setSaveAndLoadDestination(Destination destination) {
        switch (destination) {
            case file -> manipulator = new FileManipulator();
        }
    }

    public static void setRequestDestination(Destination destination) {
        switch (destination) {
            case console -> requester = new ConsoleRequester();
        }
    }

    public static void defaultInit() {
        setOutputDestination(Destination.console);
        setRequestDestination(Destination.console);
        setSaveAndLoadDestination(Destination.file);
    }

    private static class CommandCreator {

        private interface ConstructorReference {
            Command construct(List<String> args);
        }

        private static final Map<String, ConstructorReference> availableCommands = new HashMap<>();

        static {
            availableCommands.put("help", Help::new);
            availableCommands.put("info", Info::new);
            availableCommands.put("show", Show::new);
            availableCommands.put("add",  Add::new);
            availableCommands.put("update", Update::new);
            availableCommands.put("remove_by_id", RemoveById::new);
            availableCommands.put("clear", Clear::new);
            availableCommands.put("save", Save::new);
            availableCommands.put("execute_script", ExecuteScript::new);
            availableCommands.put("exit", Exit::new);
            availableCommands.put("add_if_max", AddIfMax::new);
            availableCommands.put("sort", Sort::new);
            availableCommands.put("history", History::new);
            availableCommands.put("min_by_id", MinById::new);
            availableCommands.put("count_by_age", CountByAge::new);
            availableCommands.put("filter_greater_than_character", FilterGreaterThanCharacter::new);

        }

        public static List<Command> getCommands(InputReader source) {
            List<Command> output = new ArrayList<>();
            List<List<String>> input = source.getInput();
            for (List<String> args: input) {
                String commandName = args.get(0);
                Command command = availableCommands.get(commandName).construct(args);
                command.setAskForInput(source.getAskForInput());
                output.add(command);
                Logger.log(commandName);
            }
            return output;
        }

        private CommandCreator() {}
    }


    private static abstract class Command {
        protected List<String> args;
        protected boolean askForInput;
        protected String name;

        public void setAskForInput(boolean ask) {
            askForInput = ask;
        }

        public Command(List<String> args) {
            this.args = args;
            name = args.get(0);
            args.remove(0);
        }

        public abstract int execute(DAO dao);

    }

    private static class Help extends Command {

        public Help(List<String> args) {
            super(args);
        }

    @Override
    public int execute(DAO dao) {
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

    private static class Info extends Command {

        public Info(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            outPuter.outPut(dao.getJSONDescription().toString());
            return 0;
        }
    }

    private static class Show extends Command {

        public Show(List<String> args) {
            super(args);
        }

        public int execute(DAO dao) {
            List<Dragon> dragons = dao.getAll();
            if (dragons.size() == 0) {
                outPuter.outPut("пусто");
                return 0;
            }
            for (Dragon d : dragons)
                outPuter.outPut(d);
            return 0;
        }
    }

    private static class Add extends Command {

        public Add(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            if (askForInput)
                dao.create(requester.request());
            else {
                if (args.size() != 9) {
                    outPuter.outPut("Недостаточно введённых данных");
                    return -1;
                }
                try {
                    DragonProperties properties = new DragonProperties();
                    properties.name = args.get(0);
                    properties.xCoord = Float.parseFloat(args.get(1));
                    properties.yCoord = Integer.parseInt(args.get(2));
                    properties.age = Long.parseLong(args.get(3));
                    properties.color = Color.valueOf(args.get(4));
                    properties.type = DragonType.valueOf(args.get(5));
                    properties.character = DragonCharacter.valueOf(args.get(6));
                    properties.depth = Double.parseDouble(args.get(7));
                    properties.numberOfTreasures = Integer.parseInt(args.get(8));
                    dao.create(properties);
                } catch (RuntimeException e) {
                    outPuter.outPut("Типы данных не совпадают");
                    return -1;
                }
            }
            outPuter.outPut("Элемент успешно добавлен");
            return 0;
        }
    }

    private static class Update extends Command {

        public Update(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            int id;
            try{
                id = Integer.parseInt(args.get(0));
            }
            catch (RuntimeException e){
                outPuter.outPut("Нецелочисленный тип данных id");
                return -1;
            }
            boolean found = false;
            for(Dragon d: dao.getAll()) {
                if(d.getId() == id) {
                    found = true;
                    break;
                }
            }
            if (!found){
                outPuter.outPut("Элемент с id %d не существует".formatted(id));
                return -1;
            }

            int exitCode;
            if (askForInput)
                exitCode = dao.update(id, requester.request());
            else {
                if (args.size() != 10){
                    outPuter.outPut("Недостаточно введённых данных");
                    return -1;
                }
                try {
                    DragonProperties dragonProperties = new DragonProperties();
                    dragonProperties.name = args.get(1);
                    dragonProperties.xCoord = Float.parseFloat(args.get(2));
                    dragonProperties.yCoord = Integer.parseInt(args.get(3));
                    dragonProperties.age = Long.parseLong(args.get(4));
                    dragonProperties.color = Color.valueOf(args.get(5));
                    dragonProperties.type = DragonType.valueOf(args.get(6));
                    dragonProperties.character = DragonCharacter.valueOf(args.get(7));
                    dragonProperties.depth = Double.parseDouble(args.get(8));
                    dragonProperties.numberOfTreasures = Integer.parseInt(args.get(9));
                    exitCode = dao.update(id,dragonProperties);
                }
                catch (RuntimeException e){
                    outPuter.outPut("Типы данных не совпали");
                    return -1;
                }

            }
            outPuter.outPut("Элемент успешно обновлен");
            return exitCode;
        }
    }

    private static class RemoveById extends Command {

        public RemoveById(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            int exitCode;
            try{
                if ((exitCode = dao.delete(Integer.parseInt(args.get(0)))) == 0)
                    outPuter.outPut("Элемент успешно удален");
                else
                    outPuter.outPut("Элемент не найден.");
                return exitCode;
            }
            catch (RuntimeException e){
                outPuter.outPut("Нецелочисленный тип данных id");
                return -1;
            }
        }
    }

    private static class Clear extends Command {

        public Clear(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            dao.clear();
            outPuter.outPut("Коллекция успешно очищена");
            return 0;
        }
    }

    private static class Save extends Command {

        public Save(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            try {
                manipulator.save(dao);
                outPuter.outPut("Коллекция успешно сохранена");
                return 0;
            } catch (RuntimeException e) {
                outPuter.outPut("Не удалось сохранить коллекцию (" + e.getMessage() + ")");
            }
            return -1;

        }
    }

    private static class ExecuteScript extends Command {
        public ExecuteScript(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            if (args.size() == 0){
                outPuter.outPut("Недостаточно аргументов");
                return -1;
            }

            String filePath = args.get(0);
            InputReader reader = new FileReader();
            reader.addProperties(filePath);
            List<Command> commands;

            try{
                commands = CommandCreator.getCommands(reader);
            }
            catch(RuntimeException e){
                outPuter.outPut("Команда не найдена");
                return -1;
            }

            int exitCode = 0;

            for (Command c : commands)
                exitCode += c.execute(dao);

            return exitCode;
        }
    }

    private static class Exit extends Command {

        public Exit(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            System.exit(0);
            return 0;
        }
    }

    private static class AddIfMax extends Command {

        public AddIfMax(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            Long ageMax = -1L;
            for (Dragon dragon : dao.getAll()) {
                if (dragon.getAge() > ageMax) {
                    ageMax = dragon.getAge();
                }
            }
            DragonProperties properties;
            if (askForInput)
                properties = requester.request();
            else
                try{
                    properties = new DragonProperties();
                    properties.name = args.get(0);
                    properties.xCoord = Float.parseFloat(args.get(1));
                    properties.yCoord = Integer.parseInt(args.get(2));
                    properties.age = Long.parseLong(args.get(3));
                    properties.color = Color.valueOf(args.get(4));
                    properties.type = DragonType.valueOf(args.get(5));
                    properties.character = DragonCharacter.valueOf(args.get(6));
                    properties.depth = Double.parseDouble(args.get(7));
                    properties.numberOfTreasures = Integer.parseInt(args.get(8));
                }
                catch (RuntimeException e){
                    outPuter.outPut("Типы данных не совпадают");
                    return -1;
                }

            if (properties.age > ageMax){
                dao.create(properties);
                outPuter.outPut("Элемент успешно добавлен");
            }
            else {
                outPuter.outPut("Значение этого элемента меньше максимального в коллекции. Элемент не добавлен");
            }
            return 0;
        }
    }

    private static class Sort extends Command {

        public Sort(List<String> args) {
            super(args);
        }

    @Override
    public int execute(DAO dao) {
        dao.sort();
        outPuter.outPut("Коллекция успешно отсортирована");
        return 0;
    }
}

    private static class History extends Command {

        public History(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            for (String msg : Logger.getAll())
                outPuter.outPut(msg);
            return 0;
        }
    }

    private static class MinById extends Command {

        public MinById(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            int minId = Integer.MAX_VALUE;
            for (Dragon d : dao.getAll())
                minId = d.getId() < minId ? d.getId() : minId;
            if (minId == Integer.MAX_VALUE){
                outPuter.outPut("Коллекция пуста");
                return -1;
            }
            outPuter.outPut(dao.get(minId));
            return 0;
        }
    }

    private static class CountByAge extends Command {

        public CountByAge(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            if (args.size() == 0){
                outPuter.outPut("Не хватает аргументов");
                return -1;
            }
            int age;
            try{
                age = Integer.parseInt(args.get(0));
            }
            catch(RuntimeException e){
                outPuter.outPut("Типы данных не совпали");
                return -1;
            }

            int ageCount = 0;
            for (Dragon dragon : dao.getAll()) {
                if (dragon.getAge() == age) {
                    ageCount++;
                }
            }
            outPuter.outPut(ageCount);
            return 0;
        }
    }

    private static class FilterGreaterThanCharacter extends Command {

        public FilterGreaterThanCharacter(List<String> args) {
            super(args);
        }

        @Override
        public int execute(DAO dao) {
            DragonCharacter character;
            if (args.size() == 0){
                outPuter.outPut("Не хватает аргументов");
                return -1;
            }

            try{
                character = DragonCharacter.valueOf(args.get(0));
            }
            catch (RuntimeException e){
                outPuter.outPut("Характер не определён");
                return -1;
            }
            for (Dragon dragon: dao.getAll()){
                if (dragon.getCharacter().compareCharacter(character)){
                    outPuter.outPut(dragon);
                }
            }
            return 0;
        }
    }
}