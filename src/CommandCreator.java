import java.util.*;



public class CommandCreator {
    private static final Map<String, ConstructorReference> availableCommands = new HashMap<>();

    static {
        // TODO commands here
    }

    private interface ConstructorReference {
        Command construct(List<String> args);
    }

    public static List<Command> getCommands(InputReader source) {

    }

    private CommandCreator() {}
}
