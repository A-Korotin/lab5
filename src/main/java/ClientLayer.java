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
import net.Request;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.util.*;

public final class ClientLayer {

    private final Client client;
    private final Instances instances = new Instances();
    Scanner scanner = new Scanner(System.in);
    List<String> logins = new ArrayList<>();
    String login;
    String password;

    public ClientLayer() throws IOException {
        client = new Client("localhost", 4444);
        instances.outPutter = new ConsoleOutput();
    }



    public void run() throws IOException {
        //Отправка запроса на логины и получение их
        String stringLogins = client.sendAndReceiveResponse("send me logins", 20);
        //Парсинг логинов
        logins = parseLogins(stringLogins);
        //Аутентификация полностью
        authentication();
        //Если доступ получен, то начинаем главный цикл
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

    private void registration() throws IOException {
        instances.outPutter.output("Придумайте логин. Он должен состоять не менее, чем из 5 символов");
        String login = "";
        String password = "";
        while(true){
            login = scanner.nextLine().trim();
            if (login.length() < 5){
                instances.outPutter.output("Логин должен состоять не менее, чем из 5 символов");
            }
            else if(logins.contains(login)){
                instances.outPutter.output("Такой логин уже существует! Попробуйте снова!)");
            }
            else{
                instances.outPutter.output("Успешно! Теперь придумайте пароль");
                logins.add(login);
                break;
            }
        }
        while (true){
            password = scanner.nextLine().trim();
            if (password.length() < 5){
                instances.outPutter.output("Пароль должен состоять не менее, чем из 5 символов");
            }
            else if (!unreliablePassword(password)){
                instances.outPutter.output("Пароль ненадёжный. Попробуйте снова!)");
            }
            else{
                //instances.outPutter.output("Успешно! Вы зарегистрированы!");
                String answer = client.sendAndReceiveResponse("N" + Client.toMD5(password) + '\t' + login, 20);
                instances.outPutter.output(answer);
                break;
            }
        }
    }

    private boolean unreliablePassword(String password){
        int count = 0;
        boolean reliable = true;
        for(int i = 1; i < password.length(); i++){
            if (password.charAt(i) == password.charAt(i - 1)){
                count++;
            }
        }
        if (count == password.length() - 1){
            reliable = false;
        }
        return reliable;
    }


    private List<String> parseLogins(String response){
        char sign = '\t';
        StringBuilder login = new StringBuilder();
        ArrayList<String> logins = new ArrayList<>();
        for(int i = 0; i < response.length(); i++){
             if (response.charAt(i) == sign){
                 logins.add(login.toString());
                 login = new StringBuilder();
             }
             else{
                 login.append(response.charAt(i));
             }

         }
         return logins;
    }


    private void alreadyHaveAccount(List<String> logins) throws IOException {
        String login = "";
        String password;
        instances.outPutter.output("Введите логин:  ");

        while(true){
            login = scanner.nextLine().trim();
            if (login.length() < 5){
                instances.outPutter.output("Логин должен состоять не менее, чем из 5 символов");
            }

            else{
                if (logins.contains(login)) {
                    instances.outPutter.output("Введите пароль:   ");
                    break;
                }


                instances.outPutter.output("Такого логина не существует! Попробуйте снова!)");
            }
        }

        while(true){
            password = scanner.nextLine().trim();
            if(password.length() >= 5){
                String responseAboutPassword = client.sendAndReceiveResponse("H" + Client.toMD5(password) + '\t' + login, 20);
                if (Objects.equals(responseAboutPassword, "YES")){
                    instances.outPutter.output("Пароль введён успешно!" + System.lineSeparator());
                    this.login = login;
                    this.password = Client.toMD5(password);
                    break;
                }
                else{
                    instances.outPutter.output("Пароль введён неверно! Попробуйте снова!");
                }
            }
            else{
                instances.outPutter.output("Пароль должен состоять не менее чем из 5 символов! Попробуйте снова!)");
            }
        }
    }

    private void authentication() throws IOException {
        instances.outPutter.output("Привет! Спасибо, что пришёл!)" + System.lineSeparator() +  System.lineSeparator() +
                "Введите NEW , если Вы хотите зарегистрироваться" + System.lineSeparator() +
                "Введите MYPROFILE , если у Вас уже есть профиль");
        while(true){
            String answer1 = scanner.nextLine().trim();
            if (answer1.equals("NEW")){
                registration();
                instances.outPutter.output("Введите свои данные для входа");
                alreadyHaveAccount(logins);
                break;
            }
            if (answer1.equals("MYPROFILE")){
                alreadyHaveAccount(logins);
                break;
            }
            else{
                instances.outPutter.output("Попробуйте снова");
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
                r.properties = p;
                r.login = login;
                r.password = password;
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



    public static void main(String[] args) {
        try {
            ClientLayer layer = new ClientLayer();
            layer.run();
        } catch (IOException e) {
            System.out.println("не удалось создать клиент");
        }

    }

}
