package jdbc.wrapper;

import dragon.DragonCave;
import jdbc.StatementProperty;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

final class DragonCaveWrapper implements SqlObjectWrapper<DragonCave>{

    private final String TABLE_NAME = "cave";

    @Override
    public int insertObj(DragonCave obj) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("depth", "n_treasures")
                .valuesSetter(s -> {
                            s.setDouble(1, obj.getDepth());

                            s.setObject(2, obj.getNumberOfTreasures());
                        })
                .build();
        var set = StatementFactory.getStatement(StatementType.INSERT).composePreparedStatement(property).executeQuery();
        set.next();

        return set.getInt("id");
    }

    @Override
    public List<DragonCave> getObj(StatementProperty property) throws SQLException{
        List<DragonCave> out = new ArrayList<>();

        ResultSet set = StatementFactory.getStatement(StatementType.SELECT).composePreparedStatement(property).executeQuery();

        while(set.next())
            out.add(parse(set));

        return out;
    }

    private DragonCave parse(ResultSet row) throws SQLException {
        return new DragonCave(row.getDouble("depth"), (Integer) row.getObject("n_treasures"));
    }
}
