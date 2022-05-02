package io.request;

import commands.dependencies.Instances;
import exceptions.ProgramExitException;
import net.auth.User;

import java.util.NoSuchElementException;
import java.util.Scanner;

public final class UserDataRequester {

    private static final Scanner scanner = new Scanner(System.in);


    public static User requestUser(Instances instances) {
        try {
            instances.outPutter.output("Введите логин");
            String login = scanner.nextLine();
            instances.outPutter.output("Введите пароль");
            String pass = scanner.nextLine();
            return new User(login, pass);

        } catch (NoSuchElementException e) {
            throw new ProgramExitException("Завершение работы");
        }
    }
}
