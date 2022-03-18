package commands;

import io.ConsoleOutput;
import io.ConsoleReader;
import io.FileReader;
import io.request.ConsoleRequester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExecuteScriptTest {

    @Test
    void execute() {
        Instances instances = new Instances();
        instances.outPutter = new ConsoleOutput();
        instances.fileReader = new FileReader();
        instances.consoleRequester = new ConsoleRequester();
        instances.consoleReader = new ConsoleReader();

        // recursion

        Command recursion = new ExecuteScript(new ArrayList<>(List.of("execute_script", "D:\\recursion.txt")));
        Assertions.assertNotEquals(0, recursion.execute(instances));

        // bad input
        Instances.filePathChain.clear();
        Command badInput = new ExecuteScript(new ArrayList<>(List.of("execute_script", "D:\\bad_input.txt")));
        Assertions.assertNotEquals(0, badInput.execute(instances));
    }
}