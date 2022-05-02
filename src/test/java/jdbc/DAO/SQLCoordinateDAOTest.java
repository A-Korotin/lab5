package jdbc.DAO;

import dragon.Coordinates;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLCoordinateDAOTest {

    private DAO<Coordinates> dao = new SQLCoordinateDAO();

    @Test
    void test() throws SQLException {
        Coordinates coordinates = new Coordinates(1F, 1);

        int id = dao.create(coordinates);

        System.out.println(dao.get(id));
    }

}