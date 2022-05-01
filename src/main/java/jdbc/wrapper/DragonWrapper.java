package jdbc.wrapper;

import dragon.*;
import jdbc.StatementProperty;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DragonWrapper implements SqlObjectWrapper<Dragon> {
    private final SqlObjectWrapper<DragonCave> caveWrapper = new DragonCaveWrapper();
    private final SqlObjectWrapper<Coordinates> coordinateWrapper = new CoordinateWrapper();

    @Override
    public int insertObj(Dragon obj) throws SQLException {
        int coordId = coordinateWrapper.insertObj(obj.getCoordinates());
        int caveId = caveWrapper.insertObj(obj.getCave());

        StatementProperty property = new StatementProperty.Builder()
                .tableName("dragon")
                .fields("name", "coordinates_id", "creation_date", "age", "color", "type", "character", "cave_id", "creator_id")
                .valuesSetter(s -> {
                    s.setString(1, obj.getName());
                    s.setInt(2, coordId);
                    s.setDate(3, java.sql.Date.valueOf(obj.getCreationDate()));
                    s.setLong(4, obj.getAge());

                    s.setString(5, obj.getColor() == null ? null: obj.getColor().getDescription());

                    s.setString(6, obj.getType().getDescription());

                    s.setString(7, obj.getCharacter() == null ? null : obj.getCharacter().getDescription());

                    s.setInt(8, caveId);
                    s.setInt(9, obj.getCreatorId());
                }).build();

        var set = StatementFactory.getStatement(StatementType.INSERT).composePreparedStatement(property).executeQuery();
        set.next();
        return set.getInt("id");
    }

    @Override
    public List<Dragon> getObj(StatementProperty property) throws SQLException {
        List<Dragon> out = new ArrayList<>();

        var set = StatementFactory.getStatement(StatementType.SELECT).composePreparedStatement(property).executeQuery();

        while(set.next())
            out.add(parse(set));

        return out;
    }

    private Dragon parse(ResultSet set) throws SQLException {
        Dragon dragon = new Dragon();

        dragon.setId(set.getInt("id"));
        dragon.setName(set.getString("name"));

        StatementProperty property = new StatementProperty.Builder()
                .tableName("coordinate")
                .criteria("id")
                .valuesSetter(s -> s.setInt(1, set.getInt("coordinates_id")))
                .build();

        dragon.setCoordinates(coordinateWrapper.getObj(property).get(0));

        dragon.setCreationDate(set.getDate("creation_date").toLocalDate());

        dragon.setAge(set.getLong("age"));

        String color = set.getString("color");
        dragon.setColor(color == null ? null: Color.valueOf(color));

        dragon.setType(DragonType.valueOf(set.getString("type")));

        String character = set.getString("character");
        dragon.setCharacter(character == null ? null :DragonCharacter.valueOf(character));

        property = new StatementProperty.Builder()
                .tableName("cave")
                .criteria("id")
                .valuesSetter(s -> s.setInt(1, set.getInt("cave_id")))
                .build();

        dragon.setCave(caveWrapper.getObj(property).get(0));

        dragon.setCreatorId(set.getInt("creator_id"));

        return dragon;
    }
}
