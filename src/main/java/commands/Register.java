package commands;

import commands.dependencies.Instances;
import exceptions.UserLoginAlreadyExistsException;
import io.OutPutter;
import jdbc.UserManager;
import net.auth.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Register extends Command {

    public Register() {
        super(new ArrayList<>(List.of("register")),0);
    }

    @Override
    public int execute(Instances instances, OutPutter outPutter) {
        try {
            outPutter.output(UserManager.registerUser(user) ?
                    " valid. Пользователь успешно зарегистрирован":
                    " invalid");
        } catch (UserLoginAlreadyExistsException e) {
            outPutter.output(" invalid. Пользователь с таким логином уже существует");
        }
        catch (SQLException e) {
            outPutter.output(" invalid. Ошибка сервера");
        }

        return 0;
    }
}
