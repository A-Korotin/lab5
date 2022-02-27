import io.*;
import io.request.ConsoleRequester;
import io.request.Properties;

public class Main {
    public static void main(String[] args) {
        ConsoleRequester requester = new ConsoleRequester();
        Properties p = requester.requestProperties();


    }
}
