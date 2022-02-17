import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.json.*;


interface DAO {
    int create(Dragon dragon);
    int update(int id, DragonProperties properties);
    int delete(int id);
    Dragon get(int id);
    List<Dragon> getAll();
    int clear();
    JsonObject getJSONDescription();
    void sort();
}

class DragonDAO implements DAO {
    private LocalDateTime initDateTime;
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

    }
    @Override
    public int create(Dragon dragon) {
        collection.add(dragon);
        return 0;
    }

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

    @Override
    public int delete(int id) {
        if (collection.removeIf(dragon -> dragon.getId() == id))
            return 0;
        return -1;
    }

    @Override
    public Dragon get(int id) {
        for(Dragon dragon : collection){
            if (dragon.getId() == id) {
                return dragon;
            }
        }
        return null;
    }

    // TODO не работает
    @Override
    public List<Dragon> getAll(){
        List<Dragon> outputCollection = new LinkedList<>();
        outputCollection.addAll(collection);
        //Collections.copy(outputCollection, collection);
        return outputCollection;
    }

    @Override
    public int clear() {
        collection.clear();
        return 0;
    }

    @Override
    public JsonObject getJSONDescription() {

        JsonArrayBuilder dragons = Json.createArrayBuilder();
        for (Dragon d: collection)
            dragons.add(d.getJSONDescription());

        JsonObject output = Json.createObjectBuilder().
                add("type", collection.getClass().getSimpleName()).
                add("size", collection.size()).
                add("init date", initDateTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu: HH:mm:ss"))).
                add("elements", dragons.build()).build();

        return output;
    }

    @Override
    public void sort() {
        Collections.sort(collection);
    }
}