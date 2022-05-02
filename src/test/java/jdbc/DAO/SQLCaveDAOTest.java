package jdbc.DAO;

import dragon.DragonCave;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLCaveDAOTest {
    private DAO<DragonCave> dao = new SQLCaveDAO();

    @Test
    void test() throws SQLException {
        DragonCave cave = new DragonCave(1, null);

        int id = dao.create(cave);

        DragonCave cave_new = dao.get(id);

        System.out.println(cave_new);
    }

}