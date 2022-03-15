package commands;


import collection.DAO
import collection.DragonDAO;
import io.*;
import io.request.ConsoleRequester;
import io.request.Properties;

import java.util.ArrayList;
import java.util.List;

public class Instances {
    OutPutter consoleOutputout = new ConsoleOutput();
    DragonDAO dao = new DragonDAO();
    Properties properties = new Properties();
    ConsoleReader consoleReader = new ConsoleReader();
    ConsoleRequester consoleRequester = new ConsoleRequester();
    InputReader fileReader = new FileReader();
    FileManipulator fileManipulator = new FileManipulator();
    static List<String> filePathChain = new ArrayList<>();


}
