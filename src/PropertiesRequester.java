import javax.management.relation.RoleInfoNotFoundException;
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
     * Метод, который присваивает полям объекта типа DragonProperties значения, преобразованные из строк в нужный тип данных
     * @param input - список полученных данных
     * @param indexShift - параметр, нужный для корректного присвоения элементов по индексам
     * @return properties - параметры элементов коллекции
     * */
    public static DragonProperties parseProperties(List<String> input, int indexShift){
        DragonProperties properties = new DragonProperties();
        if (input.size() != 9 + indexShift)
            throw new RuntimeException("ОШИБКА! Неверное количество параметров");
        try {
            properties.name = input.get(0 + indexShift);
            properties.xCoord = Float.parseFloat(input.get(1 + indexShift));
            properties.yCoord = Integer.parseInt(input.get(2 + indexShift));
            properties.age = Long.parseLong(input.get(3 + indexShift));

            if (input.get(4 + indexShift).trim() == ""){
                properties.color = null;
            }
            else{
                properties.color = Color.valueOf(input.get(4 + indexShift));
            }

            properties.type = DragonType.valueOf(input.get(5 + indexShift));

            if (input.get(6 + indexShift).trim() == ""){
                properties.character = null;
            }
            else{
                properties.character = DragonCharacter.valueOf(input.get(6 + indexShift));
            }
            properties.depth = Double.parseDouble(input.get(7 + indexShift));

            if (input.get(8 + indexShift).trim() == ""){
                properties.numberOfTreasures = null;
            }
            else{
                properties.numberOfTreasures = Integer.parseInt(input.get(8 + indexShift));
            }

        } catch (RuntimeException e) {
            throw new RuntimeException("ОШИБКА! Типы данных несовместимы");
        }
        if (properties.name.isEmpty())
            throw new RuntimeException("ОШИБКА! Параметр ИМЯ не может быть пустым");
        if (properties.yCoord > 998)
            throw new RuntimeException("ОШИБКА! Параметр КООРДИНАТА_Y не может быть >998");
        if (properties.age <= 0)
            throw new RuntimeException("ОШИБКА !Параметр ВОЗРАСТ не может быть <= 0");
        if (properties.numberOfTreasures <= 0)
            throw new RuntimeException("ОШИБКА! Параметр КОЛИЧЕСТВО_СОКРОВИЩ_В_ПЕЩЕРЕ не может быть <= 0");
        return properties;
    }
}
/**
* Класс, который имплементируется от PropertiesRequester, запрашивающий данные от пользователя с консоли */
class ConsoleRequester implements PropertiesRequester {
    /**
     * Метод, который присваивает полям объекта типа DragonProperties значения, считанные с консоли, преобразованные из строк в нужный тип данных
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
                if (output.name.isEmpty()) {
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
                output.xCoord = Float.parseFloat(scanner.nextLine());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        System.out.println("Введите координату Y пещеры дракона, Integer, не больше 998");
        while (true) {
            try {
                output.yCoord = Integer.parseInt(scanner.nextLine());
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
                output.age = Long.parseLong(scanner.nextLine());
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
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    output.color = null;
                    break;
                }
                output.color = Color.valueOf(line);
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        System.out.println("Введите тип дракона: UNDERGROUND, AIR, FIRE");
        while (true) {
            try {
                output.type = DragonType.valueOf(scanner.nextLine());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        System.out.println("Введите характер дракона: CUNNING, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE. Для присвоения null введите пустую строку");
        while (true) {
            try {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    output.character = null;
                    break;
                }
                output.character = DragonCharacter.valueOf(scanner.nextLine());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        System.out.println("Введите глубину пещеры дракона, double");
        while (true) {
            try {
                output.depth = Double.parseDouble(scanner.nextLine());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }
        System.out.println("Введите количество сокровищ в пещере дракона, Integer, >0. Для присвоения null введите пустую строку");
        while (true) {
            try {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()){
                    output.numberOfTreasures = null;
                    break;
                }
                output.numberOfTreasures = Integer.parseInt(scanner.nextLine());
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
