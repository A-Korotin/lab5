package exceptions;

import java.util.Arrays;

public class InvalidArgsSizeException extends RuntimeException {
    public InvalidArgsSizeException(String commandName, int received, Integer ... expected) {
        super("Неверное количество параметров для команды %s. Ожидалось (одно из перечисленного): %s, получено %d".formatted(commandName, Arrays.toString(expected), received));
    }
}
