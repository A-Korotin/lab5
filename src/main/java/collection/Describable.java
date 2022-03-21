package collection;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Интерфейс объектов, для которых можно получить описание в формате JSON
 */
public interface Describable {
    String description() throws JsonProcessingException;
}
