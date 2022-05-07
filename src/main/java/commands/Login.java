package commands;

import commands.dependencies.Instances;
import exceptions.UserLoginAlreadyExistsException;
import jdbc.UserManager;
import net.auth.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Login extends Command {


    public Login(){
        super(new ArrayList<>(List.of("login")), 0);
    }

    @Override
    public int execute(Instances instances) {

        try {
            instances.outPutter.output(UserManager.isValid(user) ?
                    " valid. Добро пожаловать в систему"
                    : " invalid. Неверный логин или пароль");
        } catch (SQLException e) {
            instances.outPutter.output(" invalid. Ошибка сервера, повторите попытку позже");
            return -1;
        }
        return 0;
    }
}
