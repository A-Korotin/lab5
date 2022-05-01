package jdbc.wrapper;

import dragon.Coordinates;
import jdbc.StatementProperty;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateWrapperTest {

    SqlObjectWrapper<Coordinates> wrapper = new CoordinateWrapper();

    Coordinates coordinates = new Coordinates(1F, 1);

    @Test
    void insertObj() throws SQLException {
        assertEquals(wrapper.insertObj(coordinates), 1);
    }

    @Test
    void getObj() throws SQLException {
        StatementProperty p = new StatementProperty.Builder().tableName("coordinate").build();
        List<Coordinates> coordinates = wrapper.getObj(p);
    }
}