package commands.dependencies;


import collection.DAO;
import io.*;
import io.request.ConsoleRequester;

import java.util.ArrayList;
import java.util.List;

public final class Instances {
    public OutPutter outPutter;
    public DAO dao;
    public InputReader consoleReader;
    public ConsoleRequester consoleRequester;
    public InputReader fileReader;
    public static List<String> filePathChain = new ArrayList<>();


}
