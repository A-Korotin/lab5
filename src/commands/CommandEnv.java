package commands;

import java.util.*;
import java.util.List;

/**
 * Класс командной среды. Имеет только статические методы и поля
 * Содержит в себе поля, общие для всех команд. Инициализация этих полей необходима перед запуском основного цикла программы
 */
public class CommandEnv {
    /**
     * Основной цикл программы. Чтение пользовательского ввода, создание команд и их выполнение
     */
    public static void mainLoop() {
        if (outPuter == null || manipulator == null || requester == null) {
            new ConsoleOutPut().outPut("Командная среда не инициализирована.");
            return;
        }
        InputReader reader = new ConsoleReader();
        DAO dao;
        try {
            dao = manipulator.get();
        } catch (RuntimeException e) {
            outPuter.outPut(e.getMessage());
            dao = new DragonDAO();
        }

        int exitCode;
        List<Command> commands;
        outPuter.outPut("Введите help для вывода списка доступных команд.");
        while (true) {
            try {
                commands = CommandCreator.getCommands(reader);
            } catch (RuntimeException e) {
                outPuter.outPut("Команда не найдена. Для подробной информации введите help.");
                continue;
            }
            for(Command c: commands) {
                if (c.name.equals("execute_script"))
                    filePathChain.clear();
                exitCode = c.execute(dao);

                if (exitCode != 0)
                    outPuter.outPut(String.format("Команда %s не была успешно выполнена. Код выхода: %d",c.name,exitCode));

            }
        }
    }

    private CommandEnv() {}

    private static OutPuter outPuter;
    private static CollectionManipulator manipulator;
    private static PropertiesRequester requester;
    private static List<String> filePathChain = new ArrayList<>();


    public static void setOutputDestination(Destination destination) {
        switch (destination) {
            case console -> outPuter = new ConsoleOutPut();
        }
    }

    public static void setSaveAndLoadDestination(Destination destination) {
        switch (destination) {
            case file -> manipulator = new FileManipulator();
        }
    }

    public static void setRequestDestination(Destination destination) {
        switch (destination) {
            case console -> requester = new ConsoleRequester();
        }
    }

    public static void defaultInit() {
        setOutputDestination(Destination.console);
        setRequestDestination(Destination.console);
        setSaveAndLoadDestination(Destination.file);
    }






}