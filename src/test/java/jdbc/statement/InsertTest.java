package jdbc.statement;

import jdbc.StatementProperty;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class InsertTest {

    @Test
    void composeStatement() {
        StatementProperty p = new StatementProperty.Builder().tableName("dragons").fields("name", "character").build();
        try {
            PreparedStatement s = new Insert(null).composePreparedStatement(p);
        } catch (NullPointerException | SQLException ignored) {}
    }
}