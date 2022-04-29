package jdbc.statement;

import jdbc.StatementProperty;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class DeleteTest {

    @Test
    void composeStatement() {
        StatementProperty p = new StatementProperty.Builder().tableName("dragons").criteria("id").build();
        try {
            PreparedStatement s = new Delete(null).composePreparedStatement(p);
        } catch (NullPointerException | SQLException ignored) {}
    }
}
