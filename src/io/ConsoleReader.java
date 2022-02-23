package io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для чтения ввода с консоли, наследуется от абстрактного класса io.InputReader*/
public class ConsoleReader extends InputReader {

    {
        askForInput = true;
    }
    /**
     * Метод получения данных с консоли
     * @return out - Список списков, считанный с консоли
     * */
    public List<List<String>> getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isBlank())
            throw new RuntimeException("Blank input!");
        List<String> output = new ArrayList<>(List.of(input.split(" ")));
        output.removeIf(String::isBlank);

        List<List<String>> out = new ArrayList<>();
        out.add(output);
        return out;
    }
}

