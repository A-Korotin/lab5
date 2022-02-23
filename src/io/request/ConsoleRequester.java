package io.request;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Класс, который имплементируется от io.PropertiesRequester, запрашивающий данные от пользователя с консоли */
public final class ConsoleRequester implements Requester {
    private InputStream inputStream = System.in;
    private PrintStream printStream = System.out;

    @Override
    public String request(ValidationFunction function, String startMsg, String errorMsg) {
        Scanner scanner = new Scanner(inputStream);
        boolean validInput = false;
        String input = null;
        printStream.print(startMsg);
        while(!validInput) {
            try {
                input = scanner.nextLine().trim();
                validInput = function.validate(input);
                if(!validInput)
                    printStream.println(errorMsg);
            } catch (RuntimeException e) {
                printStream.println(errorMsg);
            }
        }
        return input;
    }

    @Override
    public void setInputStream(InputStream stream) {
        inputStream = stream;
    }

    @Override
    public void setPrintStream(PrintStream stream) {
        printStream = stream;
    }
}
