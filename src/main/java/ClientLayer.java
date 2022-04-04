import com.fasterxml.jackson.core.JsonProcessingException;
import commands.Command;
import commands.CommandCreator;
import commands.ExecuteScript;
import commands.dependencies.CommandProperties;
import commands.dependencies.Instances;
import exceptions.InvalidArgsSizeException;
import exceptions.InvalidValueException;
import exceptions.ProgramExitException;
import io.ConsoleOutput;
import json.Json;
import net.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class ClientLayer {

    private final Client client;
    private final Instances instances = new Instances();

    public ClientLayer() throws IOException {
        client = new Client("localhost", 4444);
        instances.outPutter = new ConsoleOutput();
    }

    public void run() {
        instances.outPutter.output("Введите команду");
        for(;;) {
            try {
                loopBody();
            } catch (ProgramExitException e) {
                instances.outPutter.output(e.getMessage());
                break;
            } catch (InvalidValueException | IOException e) {
                instances.outPutter.output(e.getMessage());
            }
        }
    }

    private void loopBody() throws InvalidValueException, IOException {
        List<Command> commands;
        try {
            commands = CommandCreator.getCommands(instances.consoleReader);
        } catch (InvalidArgsSizeException e) {
            instances.outPutter.output(e.getMessage());
            return;
        } catch (NoSuchElementException e) {
            throw new ProgramExitException("Завершение программы...");
        } catch (NullPointerException e) {
            instances.outPutter.output("Такой команды не существует. Введите help для подробной информации");
            return;
        }

        List<CommandProperties> commandProperties = new ArrayList<>();

        for(Command c: commands) {
            if (c instanceof ExecuteScript)
                Instances.filePathChain.clear();

            if (c instanceof ExecuteScript)
                commandProperties.addAll(handleExecuteScript((ExecuteScript) c));
            else
                commandProperties.add(c.getProperties(instances));

        }

        for (CommandProperties p: commandProperties) {
            String request;
            try {
                request = serialize(p);
            } catch (JsonProcessingException e) {
                instances.outPutter.output("Ошибка сериализации json");
                return;
            }

            String response = client.sendAndReceiveResponse(request, 10);

            instances.outPutter.output(response);
        }

    }

    private List<CommandProperties> handleExecuteScript(ExecuteScript script) throws InvalidValueException {
        List<CommandProperties> properties = new ArrayList<>();

        for(Command c: script.getNestedCommands(instances))
            properties.add(c.getProperties(instances));

        return properties;
    }

    private String serialize(CommandProperties p) throws JsonProcessingException {
        return Json.stringRepresentation(Json.toJson(p), false);
    }



    public static void main(String[] args) {
        try {
            ClientLayer layer = new ClientLayer();
            layer.run();
        } catch (IOException e) {
            System.out.println("не удалось создать клиент");
        }

    }

}
