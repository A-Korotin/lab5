package io.request;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Класс, запрашивающий данные от пользователя с консоли */
public final class ConsoleRequester {

    private static final class StringProperties {
        String name;
        String xCoord;
        String yCoord;
        String age;
        String color;
        String type;
        String character;
        String depth;
        String nTreasures;
    }

    private static Properties convertToProperties(StringProperties p) {
        Properties out = new Properties();
        out.name = p.name;
        out.xCoord = Float.parseFloat(p.xCoord);
        out.yCoord = Integer.parseInt(p.yCoord);
        out.age = Long.parseLong(p.age);
        out.color = p.color.equalsIgnoreCase("null") ? null : Color.valueOf(p.color.toUpperCase());
        out.type = DragonType.valueOf(p.type.toUpperCase());
        out.character = p.character.equalsIgnoreCase("null") ? null : DragonCharacter.valueOf(p.character.toUpperCase());
        out.depth = Double.parseDouble(p.depth);
        out.numberOfTreasures = p.nTreasures.equalsIgnoreCase("null") ? null : Integer.parseInt(p.nTreasures);

        return out;
    }

    private InputStream inputStream = System.in;
    private PrintStream printStream = System.out;

    private String request(Predicate<String> function, String startMsg) {
        Scanner scanner = new Scanner(inputStream);

        boolean validInput = false;
        String input = null;
        printStream.print(startMsg);
        while(!validInput) {
            try {
                input = scanner.nextLine().trim();
                validInput = function.test(input);
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
        StringProperties tmp = new StringProperties();

        tmp.name = request(input -> !input.isEmpty(),
                "Введите имя, не может быть пустым: "
        );

        tmp.xCoord = request(input -> {
            Float.parseFloat(input); // contains float value
            return true;
            },
                "Введите координату X пещеры дракона, Float: "
        );

        tmp.yCoord = request(input -> Integer.parseInt(input) <= 998,
                "Введите координату Y пещеры дракона, Integer <= 998: "
        );

        tmp.age = request(input -> Long.parseLong(input) > 0,
                "Введите возраст дракона, Long >0: "
        );

        tmp.color = request(input -> {
            if ( input.equalsIgnoreCase("null") )
                return true;
            Color.valueOf(input.toUpperCase()); //contains Color value
            return true;
                },
                "Введите цвет дракона BLACK, BLUE, WHITE, BROWN, null: "
        );

        tmp.type = request(input -> {
            DragonType.valueOf(input.toUpperCase()); //contains DragonType value
            return true;
                },
        "Введите тип дракона UNDERGROUND, AIR, FIRE: "
        );

        tmp.character = request(input -> {
            if ( input.equalsIgnoreCase("null") )
                return true;
            DragonCharacter.valueOf(input.toUpperCase()); // contains DragonCharacter value
            return true;
                },
                "Введите характер дракона CUNNING, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE, null: "
        );

        tmp.depth = request(input -> {
            Double.parseDouble(input); // contains double value
            return true;
            },
                "Введите глубину пещеры дракона, Double: "
        );

        tmp.nTreasures = request(input -> input.equalsIgnoreCase("null") || Integer.parseInt(input) > 0,
                "Введите количество сокровищ в пещере дракона, Integer >0 или null: "
        );


        return convertToProperties(tmp);
    }
}
