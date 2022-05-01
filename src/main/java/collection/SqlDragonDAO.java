package collection;

import dragon.Dragon;
import io.Properties;

import java.util.LinkedList;
import java.util.List;

public final class SqlDragonDAO implements DAO {

    private final List<Dragon> collection = new LinkedList<>();

    private DragonDAO dragonDAO;


    public SqlDragonDAO() {

    }

    @Override
    public int create(Properties properties) {
        return 0;
    }

    @Override
    public int update(int id, Properties properties) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Dragon get(int id) {
        return null;
    }

    @Override
    public List<Dragon> getAll() {
        return null;
    }

    @Override
    public int clear() {
        return 0;
    }
}
