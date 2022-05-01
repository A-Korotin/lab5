package jdbc.statement;

import jdbc.StatementProperty;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class SelectTest {

    @Test
    void composeStatement() {
        StatementProperty p = new StatementProperty.Builder().
                tableName("dragons").
                criteria("a").
                build();
        try {
            PreparedStatement s = new Select(null).composePreparedStatement(p);
        } catch (NullPointerException | SQLException ignored) {}
    }
}