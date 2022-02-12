import java.util.Collection;
import java.util.function.Predicate;

public interface DAO {
    // TODO info
    int create(Dragon dragon);
    void update(Dragon dragon);
    void delete(int id);
    Dragon get(int id);
    Collection<Dragon> getAll();
    Collection<Dragon> getFilter(Predicate<Dragon> condition);
}
