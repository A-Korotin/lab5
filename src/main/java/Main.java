import collection.DAO;
import collection.Describable;
import collection.DragonDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.*;
import io.request.ConsoleRequester;
import io.request.Properties;


public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        ConsoleRequester requester = new ConsoleRequester();
        Properties p = new Properties();

        DAO dao = new DragonDAO();

        dao.create(p);
        FileManipulator manipulator = new FileManipulator();
        manipulator.save((Describable) dao);

        DAO dao1 = manipulator.get();
        dao1.create(p);
        System.out.println(((Describable) dao1).description());
    }
}
