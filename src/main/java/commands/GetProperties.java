package commands;

import exceptions.InvalidValueException;
import io.Properties;

import java.util.List;

public final class GetProperties {
    public static Properties getProperties(boolean askForInput, List<String> args, Instances instances, int indexShift) throws InvalidValueException {
        Properties properties;
        if (askForInput) {
            properties = instances.consoleRequester.requestProperties();
        }
        else {
            properties = Properties.parseProperties(args, indexShift);
        }
        return properties;
    }

}
