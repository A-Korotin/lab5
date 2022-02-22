package io;

import collection.Color;
import collection.DragonCharacter;
import collection.DragonType;

import java.util.List;
import java.util.Scanner;
/**
* Интерфейс для запроса информации у пользователя*/

public interface PropertiesRequester {
    DragonProperties request();
}
/**Класс присваивания полям элементов значений, полученных с консоли или из файла */
class DragonProperties {
    String name;
    Float xCoord;
    Integer yCoord;
    Long age;
    Color color;
    DragonType type;
    DragonCharacter character;
    double depth;
    Integer numberOfTreasures;
    /**
     * Метод, который присваивает полям объекта типа io.DragonProperties значения, преобразованные из строк в нужный тип данных
     * @param input - список полученных данных
     * @param indexShift - параметр, нужный для корректного присвоения элементов по индексам
     * @return properties - параметры элементов коллекции
     * */
    public static DragonProperties parseProperties(List<String> input, int indexShift){
        DragonProperties properties = new DragonProperties();
        if (input.size() != 9 + indexShift)
            throw new RuntimeException("ОШИБКА! Неверное количество параметров");
        try {
            properties.name = input.get(0+indexShift);
            properties.xCoord = Float.parseFloat(input.get(1 + indexShift));
            properties.yCoord = Integer.parseInt(input.get(2 + indexShift));
            properties.age = Long.parseLong(input.get(3 + indexShift));
            properties.color = input.get(4 + indexShift).equals("null") ? null: Color.valueOf(input.get(4 + indexShift));
            properties.type = DragonType.valueOf(input.get(5 + indexShift));
            properties.character = input.get(6 + indexShift).equals("null") ? null : DragonCharacter.valueOf(input.get(6 + indexShift));
            properties.depth = Double.parseDouble(input.get(7 + indexShift));
            properties.numberOfTreasures = input.get(8 + indexShift).equals("null") ? null: Integer.parseInt(input.get(8 + indexShift));
        } catch (RuntimeException e) {
            throw new RuntimeException("ОШИБКА! Типы данных несовместимы");
        }
        if (properties.name.isEmpty()) // redundant
            throw new RuntimeException("ОШИБКА! Параметр ИМЯ не может быть пустым");
        if (properties.yCoord > 998)
            throw new RuntimeException("ОШИБКА! Параметр КООРДИНАТА_Y не может быть >998");
        if (properties.age <= 0)
            throw new RuntimeException("ОШИБКА !Параметр ВОЗРАСТ не может быть <= 0");
        if (properties.numberOfTreasures != null && properties.numberOfTreasures <= 0)
            throw new RuntimeException("ОШИБКА! Параметр КОЛИЧЕСТВО_СОКРОВИЩ_В_ПЕЩЕРЕ не может быть <= 0");
        return properties;
    }
}
/**
* Класс, который имплементируется от io.PropertiesRequester, запрашивающий данные от пользователя с консоли */
class ConsoleRequester implements PropertiesRequester {
    /**
     * Метод, который присваивает полям объекта типа io.DragonProperties значения, считанные с консоли, преобразованные из строк в нужный тип данных
     * @return output - параметры элементов коллекции
     * */
    @Override
    public DragonProperties request() {
        Scanner scanner = new Scanner(System.in);
        DragonProperties output = new DragonProperties();

        System.out.println("Введите имя дракона, не может быть пустым");
        while (true) {
            try {
                output.name = scanner.nextLine();
                if (output.name.trim().isEmpty()) {
                    System.out.println("Неверный ввод.");
                    continue;
                }
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        System.out.println("Введите координату X пещеры дракона, Float");
        while(true) {
            try{
                output.xCoord = Float.parseFloat(scanner.nextLine().trim());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        System.out.println("Введите координату Y пещеры дракона, Integer, не больше 998");
        while (true) {
            try {
                output.yCoord = Integer.parseInt(scanner.nextLine().trim());
                if (output.yCoord > 998){
                    System.out.println("Неверный ввод.");
                    continue;
                }
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        System.out.println("Введите возраст дракона, Long, >0");
        while (true) {
            try {
                output.age = Long.parseLong(scanner.nextLine().trim());
                if (output.age <= 0) {
                    System.out.println("Неверный ввод.");
                    continue;
                }
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        System.out.println("Введите цвет дракона: BLACK, BLUE, WHITE, BROWN. Для присвоения null введите пустую строку");
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    output.color = null;
                    break;
                }
                output.color = Color.valueOf(line.toUpperCase());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        System.out.println("Введите тип дракона: UNDERGROUND, AIR, FIRE");
        while (true) {
            try {
                output.type = DragonType.valueOf(scanner.nextLine().trim().toUpperCase());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        System.out.println("Введите характер дракона: CUNNING, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE. Для присвоения null введите пустую строку");
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    output.character = null;
                    break;
                }
                output.character = DragonCharacter.valueOf(line.toUpperCase());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        System.out.println("Введите глубину пещеры дракона, double");
        while (true) {
            try {
                output.depth = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        System.out.println("Введите количество сокровищ в пещере дракона, Integer, >0. Для присвоения null введите пустую строку");
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()){
                    output.numberOfTreasures = null;
                    break;
                }
                output.numberOfTreasures = Integer.parseInt(line);
                if (output.numberOfTreasures <= 0) {
                    System.out.println("Неверный ввод.");
                    continue;
                }
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        return output;
    }
}
