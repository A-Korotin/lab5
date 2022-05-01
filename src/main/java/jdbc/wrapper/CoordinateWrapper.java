package jdbc.wrapper;

import dragon.Coordinates;
import jdbc.StatementProperty;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class CoordinateWrapper implements SqlObjectWrapper<Coordinates> {

    private final String TABLE_NAME = "coordinate";

    @Override
    public int insertObj(Coordinates obj) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("x", "y")
                .valuesSetter(s -> {
                    s.setFloat(1, obj.getX());
                    s.setInt(2, obj.getY());
                })
                .build();
        var set = StatementFactory.getStatement(StatementType.INSERT).composePreparedStatement(property).executeQuery();
        set.next();

        return set.getInt("id");
    }

    @Override
    public List<Coordinates> getObj(StatementProperty property) throws SQLException {
        List<Coordinates> coordinates = new ArrayList<>();

        ResultSet set = StatementFactory.getStatement(StatementType.SELECT).composePreparedStatement(property).executeQuery();

        while(set.next())
            coordinates.add(parse(set));

        return coordinates;
    }

    private Coordinates parse(ResultSet row) throws SQLException {
        return new Coordinates(row.getFloat("x"), row.getInt("y"));
    }
}
