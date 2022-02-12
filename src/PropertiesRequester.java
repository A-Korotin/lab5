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

        while (true) {
            try {
                System.out.println("Введите имя дракона, не может быть пустым");
                output.name = scanner.nextLine();
                if (output.name.isEmpty())
                    continue;
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        while (true) {
            try {
                System.out.println("Введите координату X пещеры дракона, Float");
                output.xCoord = scanner.nextFloat();
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        while (true) {
            try {
                System.out.println("Введите координату Y пещеры дракона, Integer, не больше 998");
                int y = scanner.nextInt();
                if (y > 998) {
                    System.out.println("Неверный ввод.");
                    continue;
                }
                output.yCoord = y;
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        while (true) {
            try {
                System.out.println("Введите возраст дракона, Long, >0");
                long age = scanner.nextLong();
                if(age <= 0) {
                    System.out.println("Неверный ввод.");
                    continue;
                }
                output.age = age;
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        while (true) {
            try {
                System.out.println("Введите цвет дракона: BLACK, BLUE, WHITE, BROWN");
                output.color = Color.valueOf(scanner.nextLine());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        while (true) {
            try {
                System.out.println("Введите тип дракона: UNDERGROUND, AIR, FIRE");
                output.type = DragonType.valueOf(scanner.nextLine());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        while (true) {
            try {
                System.out.println("Введите характер дракона: CUNNING, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE;");
                output.character = DragonCharacter.valueOf(scanner.nextLine());
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }


        while (true) {
            try {
                System.out.println("Введите глубину пещеры дракона, double");
                output.depth = scanner.nextDouble();
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод.");
            }
        }

        while (true) {
            try {
                System.out.println("Введите количество сокровищ в пещере дракона, Integer, >0");
                output.numberOfTreasures = scanner.nextInt();
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
