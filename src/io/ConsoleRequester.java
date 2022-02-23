package io;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;

import java.util.Scanner;

/**
 * Класс, который имплементируется от io.PropertiesRequester, запрашивающий данные от пользователя с консоли */
public class ConsoleRequester implements PropertiesRequester {
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
