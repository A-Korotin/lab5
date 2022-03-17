package commands;

import io.request.Properties;

import java.util.List;

public class GetProperties {
    public static Properties getProperties(boolean askForInput, List<String> args, Instances instances, int indexShift){
        Properties properties;
        if (askForInput) {
            if (args.size() > 0) {
                throw new RuntimeException("Неверное количество параметров");
            }
            properties = instances.consoleRequester.requestProperties();
        }
        else {
            try {
                properties = Properties.parseProperties(args, indexShift);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return properties;
    }

}
