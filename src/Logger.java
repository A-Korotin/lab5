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
        List<String> historyCopy = new ArrayList<>();
        Collections.copy(historyCopy, history);
        return historyCopy;
    }

    public static void setCapacity(int capacity) {
        Logger.capacity = capacity;
    }
}
