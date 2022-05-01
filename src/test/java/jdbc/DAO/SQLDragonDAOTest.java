package jdbc.DAO;

import dragon.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SQLDragonDAOTest {

    private DAO<Dragon> dragonDAO = new SQLDragonDAO();

    @Test
    void test() throws SQLException {
        Dragon dragon = new Dragon();
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

        int id = dragonDAO.create(dragon);

        System.out.println(dragonDAO.get(id));
    }

}