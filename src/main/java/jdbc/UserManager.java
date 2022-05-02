package jdbc;

import jdbc.statement.Statement;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class UserManager {
    private static final String TABLE_NAME = "users";


    public static List<String> getLogins() throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("login")
                .build();

        try(Statement s = StatementFactory.getStatement(StatementType.SELECT)) {
            var set = s.composePreparedStatement(property).executeQuery();
            List<String> out = new ArrayList<>();
            while (set.next())
                out.add(set.getString("login"));

            return out;
        }
    }

    public static void registerUser(String login, String password) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("login", "password")
                .valuesSetter(s -> {
                    s.setString(1, login);
                    s.setString(2, password);
                })
                .build();

        try(Statement s = StatementFactory.getStatement(StatementType.INSERT)) {
            var set = s.composePreparedStatement(property).executeQuery();
            set.next();
            int id = set.getInt("id");
        }
    }

    public static String getPassword(String login) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("password")
                .criteria("login")
                .valuesSetter(s->s.setString(1, login))
                .build();

        try(Statement s = StatementFactory.getStatement(StatementType.SELECT)) {
            var set = s.composePreparedStatement(property).executeQuery();
            set.next();
            return set.getString("password");
        }
    }
}
