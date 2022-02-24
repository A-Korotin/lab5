package dragon;


import io.request.Properties;
import io.request.Properties;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.json.*;

/**
Класс элементов коллекции
 */
public class Dragon implements Comparable<Dragon> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле не может быть null
    private Color color; //Поле может быть null
    private DragonType type; //Поле не может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonCave cave; //Поле не может быть null

    public Dragon(int id, String name, Coordinates coordinates, LocalDate creationDate, Long age, Color color, DragonType type, DragonCharacter character, DragonCave cave) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.cave = cave;
    }

    public Dragon(int id, Properties properties) {
        this.id = id;
        name = properties.name;
        coordinates = new Coordinates(properties.xCoord, properties.yCoord);
        creationDate = LocalDate.now();
        age = properties.age;
        color = properties.color;
        type = properties.type;
        character = properties.character;
        cave = new DragonCave(properties.depth, properties.numberOfTreasures);
    }

    public Dragon(JsonObject description) {
        id = description.getInt("id");
        name = description.getString("name");
        JsonObject coord = description.getJsonObject("coordinates");
        coordinates = new Coordinates((float)coord.getJsonNumber("x").doubleValue(), coord.getInt("y"));
        creationDate = LocalDate.parse(description.getString("creationDate"), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        age = description.getJsonNumber("age").longValue();
        color = description.getString("color").equals("null") ? null :Color.valueOf(description.getString("color"));
        type = DragonType.valueOf(description.getString("type"));
        character = description.getString("character").equals("null") ? null : DragonCharacter.valueOf(description.getString("character"));
        JsonObject cave = description.getJsonObject("cave");

        this.cave = new DragonCave(cave.getJsonNumber("depth").doubleValue(), cave.getString("numberOfTreasures").equals("null") ? null :Integer.valueOf(cave.getString("numberOfTreasures")));
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
                        add("y", coordinates.getY()).build()).
                add("creationDate", creationDate.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"))).
                add("age", age).
                add("color", color == null ? "null" :color.getDescription()).
                add("type", type.getDescription()).
                add("character", character == null ? "null" : character.getDescription()).
                add("cave", Json.createObjectBuilder().
                        add("depth", cave.getDepth()).
                        add("numberOfTreasures", cave.getNumberOfTreasures() == null ? "null": String.valueOf(cave.getNumberOfTreasures()))).build();
        return dragon;
    }

    @Override
    public String toString() {
        return "dragon.Dragon{" + System.lineSeparator() +
                "id=" + id + System.lineSeparator() +
                "name='" + name + '\'' + System.lineSeparator() +
                "coordinates=" + coordinates + System.lineSeparator() +
                "creationDate=" + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.uuuu")) + System.lineSeparator() +
                "age=" + age + System.lineSeparator() +
                "color=" + color + System.lineSeparator() +
                "type=" + type + System.lineSeparator() +
                "character=" + character + System.lineSeparator() +
                "cave=" + cave + System.lineSeparator() +
                '}';
    }

    @Override
    public int compareTo(Dragon dragon) {
        return Long.compare(age, dragon.age);
    }

    public void update(Properties properties){
        name = properties.name;
        coordinates = new Coordinates(properties.xCoord, properties.yCoord);
        age = properties.age;
        color = properties.color;
        type = properties.type;
        character = properties.character;
        cave = new DragonCave(properties.depth, properties.numberOfTreasures);

    }

}



