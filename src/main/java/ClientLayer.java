import com.fasterxml.jackson.core.JsonProcessingException;
import commands.*;
import commands.dependencies.CommandProperties;
import commands.dependencies.Instances;
import exceptions.InvalidArgsSizeException;
import exceptions.InvalidValueException;
import exceptions.ProgramExitException;
import io.ConsoleOutput;
import io.request.UserDataRequester;
import json.Json;
import net.Client;
import net.Request;
import net.auth.User;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class ClientLayer {

    private final Client client;
    private final Instances instances = new Instances();
    private User user;

    public ClientLayer() throws IOException {
        client = new Client("localhost", 4444);
        instances.outPutter = new ConsoleOutput();
    }

    public void run() {
        try {
            auth();
        } catch (ProgramExitException e) {
            instances.outPutter.output(e.getMessage());
            return;
        }
        instances.outPutter.output("Введите команду. Для полного списка команд введите help");
        for(;;) {
            try {
                loopBody();
            } catch (ProgramExitException e) {
                instances.outPutter.output(e.getMessage());
                break;
            } catch (PortUnreachableException e) {
                instances.outPutter.output("Сервер не работает в данный момент. Повторите попытку позже");
            }
            catch (InvalidValueException | IOException e) {
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
            try {
                if (c instanceof ExecuteScript)
                    commandProperties.addAll(handleExecuteScript((ExecuteScript) c));
                else
                    commandProperties.add(c.getProperties(instances));
            } catch (NullPointerException e) {
                instances.outPutter.output("Одна или несколько команд не были распознаны");
                return;
            } catch (RuntimeException e) {
                instances.outPutter.output(e.getMessage());
                return;
            }
        }

        for (CommandProperties p: commandProperties) {
            String request;

            // Закончить работу клиента, не отправлять команду на сервер
            if (p.args.get(0).equals("exit"))
                throw new ProgramExitException("Завершение программы...");

            try {
                Request r = new Request();
                if (p.properties != null)
                    p.properties.creator_name = user.login;
                r.properties = p;
                r.user = user;
                request = serialize(r);
            } catch (JsonProcessingException e) {
                instances.outPutter.output("Ошибка сериализации json");
                return;
            }

            String response = client.sendAndReceiveResponse(request, 20);

            instances.outPutter.output(response);
        }

    }

    private List<CommandProperties> handleExecuteScript(ExecuteScript script) throws InvalidValueException {
        List<CommandProperties> properties = new ArrayList<>();

        for(Command c: script.getNestedCommands(instances))
            properties.add(c.getProperties(instances));

        return properties;
    }

    private <T>String serialize(T object) throws JsonProcessingException {
        return Json.stringRepresentation(Json.toJson(object), false);
    }

    private void auth() {
        Scanner scanner = new Scanner(System.in);
                try {
            while (true) {
                instances.outPutter.output("Войти [l] или зарегистрироваться [r]? [exit] для выхода");
                User user;
                switch (scanner.nextLine()){
                    case "l":
                         user = UserDataRequester.requestUser(instances);
                        if(validateUser(user)) {
                            this.user = user;
                            return;
                        }
                        continue;
                    case "r":
                        user = UserDataRequester.requestUser(instances);
                        if (registerUser(user)) {
                            this.user = user;
                            return;
                        }
                        continue;
                    case "exit":
                        throw new ProgramExitException("Завершение работы");

                }

            }
        } catch (NoSuchElementException e) {
            throw new ProgramExitException("Завершение работы");
        }
    }

    private boolean validateUser(User user) {
        Request request = new Request();
        request.user = user;
        try {
            request.properties = new Login().getProperties(instances);
        } catch (InvalidValueException e) {
            return false;
        }

        try {
            String response = client.sendAndReceiveResponse(serialize(request), 20);
            System.out.println(response);
            return response.split("\\.")[0].trim().equals("valid");
        } catch (IOException e) {
            return false;
        }
    }

    private boolean registerUser(User user) {
        Request request = new Request();
        request.user = user;
        try {
            request.properties = new Register().getProperties(instances);
        } catch (InvalidValueException e) {
            return false;
        }

        try {
            String response = client.sendAndReceiveResponse(serialize(request), 20);
            System.out.println(response);
            return response.split("\\.")[0].trim().equals("valid");
        } catch (IOException e) {
            return false;
        }
    }



    public static void main(String[] args) {
        try {
            ClientLayer layer = new ClientLayer();
            layer.run();
        } catch (PortUnreachableException e ) {
            System.out.println("Сервер не запущен, попробуйте позже");
        }
        catch (IOException e) {
            System.out.printf("не удалось создать клиент(%s)%n", e.getCause());
        }
    }

}
