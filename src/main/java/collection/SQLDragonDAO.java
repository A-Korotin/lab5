package collection;

import dragon.Dragon;
import exceptions.DatabaseConnectionNotEstablishedException;
import io.Properties;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class SQLDragonDAO implements DAO {

    private final List<Dragon> collection = new LinkedList<>();

    private final jdbc.DAO.DAO<Dragon> dragonDAO = new jdbc.DAO.SQLDragonDAO();


    public SQLDragonDAO() {
        try {
            var dragons = dragonDAO.getAll();
            collection.addAll(dragons);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int create(Properties properties) {
        Dragon dragon = new Dragon(-1, properties);
        collection.add(dragon);

        try {
            int databaseId = dragonDAO.create(dragon);
            dragon.setId(databaseId);
        } catch (DatabaseConnectionNotEstablishedException e) {
            return -1;
        } catch (SQLException e) {
            return -2;
        }

        return 0;
    }

    @Override
    public int update(int id, Properties properties) {
        Dragon dragon = new Dragon(-1, properties);

        collection.forEach(d -> {
            if (d.getId() == id)
                d.update(properties);
        });

        int nRowsAffected;

        try {
            nRowsAffected = dragonDAO.update(id, dragon);
        } catch (DatabaseConnectionNotEstablishedException e) {
            return -1;
        } catch (SQLException e) {
            return -2;
        }

        return 1 - nRowsAffected;
    }

    @Override
    public int delete(int id) {
        collection.removeIf(d -> d.getId() == id);
        int nRowsAffected;
        try {
            nRowsAffected = dragonDAO.delete(id);
        }catch (DatabaseConnectionNotEstablishedException e) {
            return -1;
        } catch (SQLException e) {
            return -2;
        }

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
            collection.clear();
            dragonDAO.clear();
        } catch (DatabaseConnectionNotEstablishedException e) {
            return -1;
        } catch (SQLException e) {
            return -2;
        }
        return 0;
    }
}
