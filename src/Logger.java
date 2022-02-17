import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {
    private static int capacity = 6;
    private static final List<String> history = new ArrayList<>();
    public static void log(String command){
        history.add(0,command);
        if (history.size() > capacity) {
            history.remove(capacity);
        }
    }
    public static List<String> getAll(){
        return new ArrayList<>(history);
    }

    public static void setCapacity(int capacity) {
        Logger.capacity = capacity;
    }
}
