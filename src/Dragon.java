public class Dragon {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле не может быть null
    private Color color; //Поле может быть null
    private DragonType type; //Поле не может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonCave cave; //Поле не может быть null
}
class Coordinates {
    private Float x; //Поле не может быть null
    private Integer y; //Максимальное значение поля: 998, Поле не может быть null
}
class DragonCave {
    private double depth;
    private Integer numberOfTreasures; //Поле может быть null, Значение поля должно быть больше 0
}
enum Color {
    BLACK,
    BLUE,
    WHITE,
    BROWN;
}
enum DragonType {
    UNDERGROUND,
    AIR,
    FIRE;
}
enum DragonCharacter {
    CUNNING,
    GOOD,
    CHAOTIC,
    CHAOTIC_EVIL,
    FICKLE;
}