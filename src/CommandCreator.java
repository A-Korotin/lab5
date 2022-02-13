import java.util.*;
public class CommandCreator {
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

    private interface ConstructorReference {
        Command construct(List<String> args);
    }

    public static List<Command> getCommands(InputReader source) {
        List<Command> output = new ArrayList<>();
        List<List<String>> input = source.getInput();
        for (List<String> args: input) {
            String commandName = args.get(0);
            args.remove(0);
            Command command = availableCommands.get(commandName).construct(args);
            command.setAskForInput(source.getAskForInput());
            output.add(command);
            Logger.log(commandName);
        }
        return output;
    }

    private CommandCreator() {}
}
