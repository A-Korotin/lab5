package jdbc.wrapper;

import dragon.DragonCave;
import jdbc.StatementProperty;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DragonCaveWrapperTest {

    SqlObjectWrapper<DragonCave> wrapper = new DragonCaveWrapper();

    DragonCave cave = new DragonCave(12, null);

    @Test
    void insertObj() throws SQLException {

        StatementFactory.getStatement(StatementType.DELETE).composePreparedStatement(new StatementProperty.Builder().tableName("cave").build()).executeUpdate();
        wrapper.insertObj(cave);
    }

    @Test
    void getObj() throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName("cave")
                .build();
        List<DragonCave> caves = wrapper.getObj(property);
        System.out.println(caves.get(0));
    }
}