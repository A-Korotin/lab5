package collection;

import dragon.Color;
import dragon.Dragon;
import dragon.DragonCharacter;
import dragon.DragonType;
import io.Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLDragonDAOTest {
    private SQLDragonDAO dragonDAO = new SQLDragonDAO();
    private Properties properties = new Properties();
    {
        properties.name = "ad";
        properties.character = DragonCharacter.GOOD;
        properties.yCoord = 1;
        properties.xCoord = 1F;
        properties.numberOfTreasures=null;
        properties.type = DragonType.AIR;
        properties.depth = 21;
        properties.age = 90L;
        properties.color = Color.BLACK;
    }

    @Test
    void test() {

        dragonDAO.create(properties);
        dragonDAO.getAll().forEach(System.out::println);
    }

}