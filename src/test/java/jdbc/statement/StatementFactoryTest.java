package jdbc.statement;

import jdbc.StatementProperty;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class StatementFactoryTest {

    @Test
    void getStatement() {
        try(Statement s = StatementFactory.getStatement(StatementType.SELECT)) {

            StatementProperty property = new StatementProperty.Builder()
                    .tableName("users", "users_new")
                    .fields()
                    .build();

            PreparedStatement statement = s.composePreparedStatement(property);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                System.out.printf("%d %s %n", set.getInt(1), set.getString(2));
            }

        } catch (SQLException e) {
           e.printStackTrace();
        }
    }
}