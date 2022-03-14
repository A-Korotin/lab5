import collection.DAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.*;
import io.request.*;


public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        DAO dao = new FileManipulator().get();
        ConsoleRequester requester = new ConsoleRequester();
        Properties p = requester.requestProperties();
        dao.create(p);
    }
}
