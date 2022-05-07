package collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import dragon.Dragon;
import exceptions.DatabaseConnectionNotEstablishedException;
import exceptions.DatabaseException;
import io.Properties;

import java.sql.SQLException;
import java.util.*;


public final class SQLDragonDAO implements DAO, Describable, Orderable {

    private final List<Dragon> collection = new LinkedList<>();

    private final jdbc.DAO.DAO<Dragon> dragonDAO = new jdbc.DAO.SQLDragonDAO();


    public SQLDragonDAO() {
        try {
            var dragons = dragonDAO.getAll();
            collection.addAll(dragons);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных");
        }
    }

    @Override
    public int create(Properties properties) {
        Dragon dragon = new Dragon(-1, properties);

        int databaseId;
        try {
            databaseId = dragonDAO.create(dragon);
            dragon.setId(databaseId);
        } catch (DatabaseConnectionNotEstablishedException e) {
            throw new DatabaseException("Ошибка. Нет подключения к базе данных");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных");
        }
        collection.add(dragon);

        return 0;
    }

    @Override
    public int update(int id, Properties properties) {
        Dragon dragon = new Dragon(-1, properties);

        int nRowsAffected;

        try {
            nRowsAffected = dragonDAO.update(id, dragon);
        } catch (DatabaseConnectionNotEstablishedException e) {
            throw new DatabaseException("Ошибка. Нет подключения к базе данных");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных");
        }

        collection.forEach(d -> {
            if (d.getId() == id)
                d.update(properties);
        });

        return 1 - nRowsAffected;
    }

    @Override
    public int delete(int id) {

        int nRowsAffected;
        try {
            nRowsAffected = dragonDAO.delete(id);
        } catch (DatabaseConnectionNotEstablishedException e) {
            throw new DatabaseException("Ошибка. Нет подключения к базе данных");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных");
        }

        collection.removeIf(d -> d.getId() == id);

        return 1 - nRowsAffected;
    }

    @Override
    public Dragon get(int id) {
        Optional<Dragon> dragon = collection.stream().filter(d->d.getId() == id).findFirst();
        return dragon.orElse(null);
    }

    @Override
    public List<Dragon> getAll() {
        return new ArrayList<>(collection);
    }

    @Override
    public int clear() {
        try {
            dragonDAO.clear();
            collection.clear();
        } catch (DatabaseConnectionNotEstablishedException e) {
            throw new DatabaseException("Ошибка. Нет подключения к базе данных");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных");
        }
        return 0;
    }

    @Override
    public String description() throws JsonProcessingException {
        return "SQL DAO";
    }

    @Override
    public String info() {
        return "SQL DAO {" + System.lineSeparator() +
                "type = postgreSQL," + System.lineSeparator() +
                "size = " + collection.size() + System.lineSeparator() +
                "}";
    }

    @Override
    public int sort() {
        Collections.sort(collection);
        return 0;
    }
}
