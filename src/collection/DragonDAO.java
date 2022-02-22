package collection;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, который имплементируется от collection.DAO. В нём мы реализуем методы для работы с коллекцией и инициализируем саму коллекцию
 */
class DragonDAO implements DAO<Dragon> {
    private LocalDateTime initDateTime;
    private int availableId = 1;
    private final List<Dragon> collection = new LinkedList<>();

    public DragonDAO() {
        initDateTime = LocalDateTime.now();
    }

    public DragonDAO(JsonObject description) {
        String initTime = description.getString("init date");

        if (initTime == null)
            initDateTime = LocalDateTime.now();
        else
            initDateTime = LocalDateTime.parse(initTime, DateTimeFormatter.ofPattern("dd.MM.uuuu: HH:mm:ss"));

        JsonArray dragons = description.getJsonArray("elements");

        for (int i = 0; i < description.getInt("size"); ++i)
            collection.add(new Dragon(dragons.getJsonObject(i)));

        int maxId = -1;
        for(Dragon d: collection)
            maxId = d.getId() > maxId?d.getId():maxId;

        availableId = maxId > description.getInt("availableId")? maxId + 1: description.getInt("availableId");
    }
    /**
     * Метод добавления элемента в коллекцию
     * @param properties - свойства элемента
     * */
    @Override
    public int create(DragonProperties properties) {
        collection.add(new Dragon(availableId++, properties));
        return 0;
    }
    /**
     * Метод обновления элемента в коллекции по его id
     * @param properties - свойства элемента
     * @param id - id элемента, который пользователь хочет обновить
     * */
    @Override
    public int update(int id, DragonProperties properties) {
        for(Dragon dragon1 : collection){
            if (id == dragon1.getId()) {
                dragon1.update(properties);
                return 0;
            }
        }
        return -1;
    }
    /**
     * Метод удаления элемента из коллекции по его id
     * @param id - id элемента, который пользователь хочет удалить
     * */
    @Override
    public int delete(int id) {
        if (collection.removeIf(dragon -> dragon.getId() == id))
            return 0;
        return -1;
    }
    /**
     * Метод получения элемента из коллекции по его id
     * @param id - id элемента, который пользователь хочет получить
     * @return dragon - элемент коллекции
     * */
    @Override
    public Dragon get(int id) {
        for(Dragon dragon : collection){
            if (dragon.getId() == id) {
                return dragon;
            }
        }
        return null;
    }
    /**
     * Метод получения всей коллекции
     * @return outputCollection - копия коллекции
     * */
    @Override
    public List<Dragon> getAll(){
        List<Dragon> outputCollection = new LinkedList<>();
        outputCollection.addAll(collection);
        return outputCollection;
    }
    /**
     * Метод очистки всей коллекции
     * */
    @Override
    public int clear() {
        collection.clear();
        return 0;
    }
    /**
     * Метод возвращения информации о коллекции
     * @return output - информация о коллекции
     * */
    @Override
    public JsonObject getJSONDescription() {

        JsonArrayBuilder dragons = Json.createArrayBuilder();
        for (Dragon d: collection)
            dragons.add(d.getJSONDescription());

        JsonObject output = Json.createObjectBuilder().
                add("type", collection.getClass().getSimpleName()).
                add("size", collection.size()).
                add("init date", initDateTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu: HH:mm:ss"))).
                add("availableId", availableId).
                add("elements", dragons.build()).build();

        return output;
    }
    /**
     * Метод сортировки коллекции
     * */
    @Override
    public void sort() {
        Collections.sort(collection);
    }
}
