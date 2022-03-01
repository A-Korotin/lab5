package collection;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Describable {
    String description() throws JsonProcessingException;
}
