package collection;

import dragon.Dragon;
import io.request.Properties;

import java.util.List;
import javax.json.*;
/**
* Интерфейс для работы с коллекцией
*/

public interface DAO {
    int create(Properties properties);
    int update(int id, Properties properties);
    int delete(int id);
    Dragon get(int id);
    List<Dragon> getAll();
    int clear();
    JsonObject getJSONDescription();
    int sort();
}
