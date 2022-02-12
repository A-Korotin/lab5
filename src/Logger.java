import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static List<String> history = new ArrayList<>();

    public static void log(String command){
        history.add(0,command);
        if (history.size() > 6) {
            history.remove(6);
        }
    }

}
