import collection.DAO;
import collection.Describable;
import collection.DragonDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.*;
import io.request.*;

import java.io.File;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        DAO dao = new FileManipulator().get();

        ConsoleRequester requester = new ConsoleRequester();
        Properties p = requester.requestProperties();
        dao.create(p);
    }
}
