package jdbc;

import exceptions.UserLoginAlreadyExistsException;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;
import net.auth.User;

import java.sql.SQLException;

public final class UserManager {
    private static final String TABLE_NAME = "users";

    public static boolean isValid(User user) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .fields("COUNT(*)")
                .tableName(TABLE_NAME)
                .criteria("login", "password")
                .valuesSetter(s-> {
                    s.setString(1, user.login);
                    s.setString(2, user.password);
                })
                .build();
        var set = StatementFactory.getStatement(StatementType.SELECT).composePreparedStatement(property).executeQuery();
        set.next();
        return 1 == set.getInt(1);
    }

    public static boolean registerUser(User user) throws SQLException {
        if (loginExists(user))
            throw new UserLoginAlreadyExistsException(user.login);

        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("login", "password")
                .valuesSetter(s-> {
                    s.setString(1, user.login);
                    s.setString(2, user.password);
                })
                .build();

        int nRowsAffected = StatementFactory.getStatement(StatementType.INSERT).composePreparedStatement(property).executeUpdate();
        return nRowsAffected == 1;
    }


    private static boolean loginExists(User user) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("COUNT(*)")
                .criteria("login")
                .valuesSetter(s->s.setString(1, user.login))
                .build();

        var set = StatementFactory.getStatement(StatementType.SELECT).composePreparedStatement(property).executeQuery();
        set.next();
        return set.getInt(1) != 0;
    }
}
