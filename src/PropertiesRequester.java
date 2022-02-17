import java.util.Scanner;

public interface PropertiesRequester {
    DragonProperties request();
}

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
}

class ConsoleRequester implements PropertiesRequester {

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
        System.out.println("Введите цвет дракона: BLACK, BLUE, WHITE, BROWN");
        while (true) {
            try {
                output.color = Color.valueOf(scanner.nextLine());
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
        System.out.println("Введите характер дракона: CUNNING, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE;");
        while (true) {
            try {
                output.character = DragonCharacter.valueOf(scanner.nextLine());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
                scanner.nextLine();
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
        System.out.println("Введите количество сокровищ в пещере дракона, Integer, >0");
        while (true) {
            try {
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
