package io.request;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Класс, запрашивающий данные от пользователя с консоли */
public final class ConsoleRequester {
    private InputStream inputStream = System.in;
    private PrintStream printStream = System.out;

    private String request(ValidationFunction function, String startMsg) {
        Scanner scanner = new Scanner(inputStream);
        boolean validInput = false;
        String input = null;
        printStream.print(startMsg);
        while(!validInput) {
            try {
                input = scanner.nextLine().trim();
                validInput = function.validate(input);
                if(!validInput)
                    printStream.println("Неверный ввод");
            } catch (RuntimeException e) {
                printStream.println("Неверный ввод");
            }
        }
        return input;
    }

    public void setInputStream(InputStream stream) {
        inputStream = stream;
    }

    public void setPrintStream(PrintStream stream) {
        printStream = stream;
    }

    public Properties requestProperties() {
        Properties out = new Properties();
        out.name = request(input -> !input.isEmpty(),
                "Введите имя, не может быть пустым: "
        );

        String xCoord = request(input -> !Float.isNaN(Float.parseFloat(input)) ,
                "Введите координату X пещеры дракона: "
        );

        out.xCoord = Float.parseFloat(xCoord);

        String yCoord = request(input -> Integer.parseInt(input) <= 998,
                "Введите координату Y пещеры дракона, <= 998: "
        );

        out.yCoord = Integer.parseInt(yCoord);

        String age = request(input -> Long.parseLong(input) > 0,
                "Введите возраст, >0: "
        );

        out.age = Long.parseLong(age);

        String color = request(input -> Color.valueOf(input.toUpperCase()) != null || input.toLowerCase().equals("null"),
                "Введите цвет дракона BLACK, BLUE, WHITE, BROWN, null: "
        );
        out.color = color.equals("null") ? null: Color.valueOf(color.toUpperCase());

        String type = request(input -> DragonType.valueOf(input.toUpperCase()) != null,
        "Введите тип дракона UNDERGROUND, AIR, FIRE: "
        );

        out.type = DragonType.valueOf(type.toUpperCase());

        String character = request(input -> input.equalsIgnoreCase("null") || DragonCharacter.valueOf(input.toUpperCase()) != null,
                "Введите характер дракона CUNNING, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE, null"
        );

        out.character = DragonCharacter.valueOf(character.toUpperCase());

        String depth = request(input -> !Double.isNaN(Double.parseDouble(input)),
                "Введите глубину пещеры дракона: "
        );

        out.depth = Double.parseDouble(depth);

        String nTreasures = request(input -> Integer.parseInt(input) > 0 || input.equalsIgnoreCase("null"),
                "Введите количество сокровищ в пещере дракона, >0 или null"
        );

        out.numberOfTreasures = Integer.parseInt(nTreasures);

        return out;
    }
}
