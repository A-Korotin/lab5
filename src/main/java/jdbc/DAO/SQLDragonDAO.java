package jdbc.DAO;

import dragon.*;
import jdbc.StatementProperty;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SQLDragonDAO implements DAO<Dragon> {
    private final String TABLE_NAME = "dragon";
    private final DAO<Coordinates> coordinatesDAO = new SQLCoordinateDAO();
    private final DAO<DragonCave> caveDAO = new SQLCaveDAO();

    @Override
    public int create(Dragon element) throws SQLException {
        int coordId = coordinatesDAO.create(element.getCoordinates());
        int caveId = caveDAO.create(element.getCave());

        StatementProperty property = new StatementProperty.Builder()
                .tableName("dragon")
                .fields("name", "coordinates_id", "creation_date", "age", "color", "type", "character", "cave_id", "creator_id")
                .valuesSetter(s -> {
                    s.setString(1, element.getName());
                    s.setInt(2, coordId);
                    s.setDate(3, java.sql.Date.valueOf(element.getCreationDate()));
                    s.setLong(4, element.getAge());

                    s.setString(5, element.getColor() == null ? null: element.getColor().getDescription());

                    s.setString(6, element.getType().getDescription());

                    s.setString(7, element.getCharacter() == null ? null : element.getCharacter().getDescription());

                    s.setInt(8, caveId);
                    s.setInt(9, element.getCreatorId());
                }).build();

        var set = StatementFactory.getStatement(StatementType.INSERT).composePreparedStatement(property).executeQuery();
        set.next();
        return set.getInt("id");

    }

    @Override
    public int update(int id, Dragon element) throws SQLException {
        int coordId = coordinatesDAO.create(element.getCoordinates());
        int caveId = caveDAO.create(element.getCave());

        StatementProperty property = new StatementProperty.Builder()
                .tableName("dragon")
                .fields("name", "coordinates_id", "creation_date", "age", "color", "type", "character", "cave_id", "creator_id")
                .criteria("id")
                .valuesSetter(s -> {
                    s.setString(1, element.getName());
                    s.setInt(2, coordId);
                    s.setDate(3, java.sql.Date.valueOf(element.getCreationDate()));
                    s.setLong(4, element.getAge());

                    s.setString(5, element.getColor() == null ? null: element.getColor().getDescription());

                    s.setString(6, element.getType().getDescription());

                    s.setString(7, element.getCharacter() == null ? null : element.getCharacter().getDescription());

                    s.setInt(8, caveId);
                    s.setInt(9, element.getCreatorId());

                    s.setInt(10, id);
                })
                .build();

        return StatementFactory.getStatement(StatementType.UPDATE).composePreparedStatement(property).executeUpdate();
    }

    @Override
    public int delete(int id) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .criteria("id")
                .valuesSetter(s -> s.setInt(1, id))
                .build();

        return StatementFactory.getStatement(StatementType.DELETE).composePreparedStatement(property).executeUpdate();
    }

    @Override
    public Dragon get(int id) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .criteria("id")
                .valuesSetter(s -> s.setInt(1, id))
                .build();

        var set = StatementFactory.getStatement(StatementType.SELECT).composePreparedStatement(property).executeQuery();
        set.next();
        return parse(set);
    }

    @Override
    public List<Dragon> getAll() throws SQLException {
        List<Dragon> output = new ArrayList<>();

        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .build();

        var set = StatementFactory.getStatement(StatementType.SELECT).composePreparedStatement(property).executeQuery();

        while(set.next())
            output.add(parse(set));

        return output;
    }

    @Override
    public int clear() throws SQLException {

        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .build();

        return StatementFactory.getStatement(StatementType.DELETE).composePreparedStatement(property).executeUpdate();
    }

    private Dragon parse(ResultSet set) throws SQLException {
        Dragon dragon = new Dragon();
        dragon.setId(set.getInt("id"));
        dragon.setName(set.getString("name"));

        dragon.setCoordinates(coordinatesDAO.get(set.getInt("coordinates_id")));

        dragon.setCreationDate(set.getDate("creation_date").toLocalDate());

        dragon.setAge(set.getLong("age"));

        String color = set.getString("color");
        dragon.setColor(color == null ? null: Color.valueOf(color));

        dragon.setType(DragonType.valueOf(set.getString("type")));

        String character = set.getString("character");
        dragon.setCharacter(character == null ? null :DragonCharacter.valueOf(character));

        dragon.setCave(caveDAO.get(set.getInt("cave_id")));

        dragon.setCreatorId(set.getInt("creator_id"));

        return dragon;
    }
}
