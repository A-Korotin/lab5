package collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.json.*;
/**
* Интерфейс для работы с коллекцией
*/

interface DAO<T extends DaoElement> {
    int create(DragonProperties properties);
    int update(int id, DragonProperties properties);
    int delete(int id);
    T get(int id);
    List<T> getAll();
    int clear();
    JsonObject getJSONDescription();
    int sort();
}
