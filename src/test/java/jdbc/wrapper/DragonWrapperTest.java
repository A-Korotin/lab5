package jdbc.wrapper;

import dragon.*;
import jdbc.ConnectionPool;
import jdbc.StatementProperty;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DragonWrapperTest {

    DragonWrapper wrapper = new DragonWrapper();
    Dragon dragon = new Dragon();
    int id;
    {
        dragon.setId(1);
        dragon.setName("ha");
        dragon.setAge(12L);
        dragon.setColor(null);
        dragon.setType(DragonType.AIR);
        dragon.setCharacter(DragonCharacter.GOOD);
        dragon.setCave(new DragonCave(1, null));
        dragon.setCoordinates(new Coordinates(1F, 2));
        dragon.setCreatorId(12);
        dragon.setCreationDate(LocalDate.now());
    }

    @Test
    void insertObj() throws SQLException{
        id = wrapper.insertObj(dragon);
        System.out.println(id);
    }

    @Test
    void getObj() throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName("dragon")
                .build();
        var dragons = wrapper.getObj(property);
        System.out.println(dragons.size());
        dragons.forEach(System.out::println);
    }

}