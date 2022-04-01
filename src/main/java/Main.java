import com.fasterxml.jackson.core.JsonProcessingException;
import json.Json;
import net.CommandProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        MainLayer layer = new MainLayer();
        layer.run();

    }
}
