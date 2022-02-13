import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.json.*;


interface DAO {
    void create(Dragon dragon);
    void update(Dragon dragon);
    void delete(int id);
    Dragon get(int id);
    List<Dragon> getAll();
    void clear();
    JsonObject getJSONDescription();
}

class DragonDAO implements DAO {
    private LocalDateTime initDateTime;
    private final List<Dragon> collection = new LinkedList<>();

    public DragonDAO() {
        initDateTime = LocalDateTime.now();
    }
    public DragonDAO(JsonObject description) {

    }
    @Override
    public void create(Dragon dragon) {
        collection.add(dragon);
    }

    @Override
    public void update(Dragon dragon) {
        for(Dragon dragon1 : collection){
            if (dragon.getId() == dragon1.getId()) {
                dragon1.setName(dragon.getName());
                dragon1.setCoordinates(dragon.getCoordinates());
                dragon1.setCreationDate(dragon.getCreationDate());
                dragon1.setAge(dragon.getAge());
                dragon1.setColor(dragon.getColor());
                dragon1.setType(dragon.getType());
                dragon1.setCharacter(dragon.getCharacter());
                dragon1.setCave(dragon.getCave());

            }
        }
    }

    @Override
    public void delete(int id) {
        collection.removeIf(dragon -> dragon.getId() == id);
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

    @Override
    public List<Dragon> getAll(){
        List<Dragon> outputCollection = new LinkedList<>();
        Collections.copy(outputCollection,collection);
        return outputCollection;
    }

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public JsonObject getJSONDescription() {

        JsonArrayBuilder dragons = Json.createArrayBuilder();
        for (Dragon d: collection)
            dragons.add(d.getJSONDescription());

        JsonObject output = Json.createObjectBuilder().
                add("type", "LinkedList").
                add("size", collection.size()).
                add("init date", initDateTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu: HH:mm:ss"))).
                add("elements", dragons.build()).build();

        return output;
    }
}