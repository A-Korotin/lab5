import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {
    private static final List<String> history = new ArrayList<>();
    public static void log(String command){
        history.add(0,command);
        if (history.size() > 6) {
            history.remove(6);
        }
    }
    public static List<String> getAll(){
        List<String> historyCopy = new ArrayList<>();
        Collections.copy(historyCopy, history);
        return historyCopy;
    }
}
