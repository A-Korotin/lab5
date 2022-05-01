package jdbc.wrapper;

import jdbc.StatementProperty;
import jdbc.statement.Statement;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public interface SqlObjectWrapper<T> {

    int insertObj(T obj) throws SQLException;

    List<T> getObj(StatementProperty property) throws SQLException;

}
