import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.json.*;

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

    public Dragon(String name, Coordinates coordinates, java.time.LocalDate creationDate, Long age, Color color, DragonType type, DragonCharacter character, DragonCave cave) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.cave = cave;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getAge() {
        return age;
    }

    public Color getColor() {
        return color;
    }

    public DragonType getType() {
        return type;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public DragonCave getCave() {
        return cave;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public void setCave(DragonCave cave) {
        this.cave = cave;
    }

    public JsonObject getJSONDescription() {
        JsonObject dragon = Json.createObjectBuilder().
                add("id", id).
                add("name", name).
                add("coordinates", Json.createObjectBuilder().
                        add("x", coordinates.getX()).
                        add("y", coordinates.getY())).
                add("creationDate", creationDate.toString()).
                add("age", age).
                add("color", color.getDescription()).
                add("type", type.getDescription()).
                add("character", character.getDescription()).
                add("cave", Json.createObjectBuilder().
                        add("depth", cave.getDepth()).
                        add("numberOfTreasures", cave.getNumberOfTreasures())).build();
        return dragon;
    }

}

class Coordinates {
    public Coordinates(Float x, Integer y) {
        this.x = x;
        this.y = y;
    }

    private Float x; //Поле не может быть null
    private Integer y; //Максимальное значение поля: 998, Поле не может быть null

    public Float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
class DragonCave {

    public DragonCave(double depth, Integer numberOfTreasures) {
        this.depth = depth;
        this.numberOfTreasures = numberOfTreasures;
    }

    private double depth;
    private Integer numberOfTreasures; //Поле может быть null, Значение поля должно быть больше 0

    public double getDepth() {
        return depth;
    }

    public Integer getNumberOfTreasures() {
        return numberOfTreasures;
    }
}
enum Color {
    BLACK("BLACK"),
    BLUE("BLUE"),
    WHITE("WHITE"),
    BROWN("BROWN");

    private final String description;

    Color(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
enum DragonType {
    UNDERGROUND("UNDERGROUND"),
    AIR("AIR"),
    FIRE("FIRE");

    private final String description;

    DragonType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
enum DragonCharacter {
    CUNNING("CUNNING"),
    GOOD("GOOD"),
    CHAOTIC("CHAOTIC"),
    CHAOTIC_EVIL("CHAOTIC_EVIL"),
    FICKLE("FICKLE");

    private final String description;

    DragonCharacter(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

