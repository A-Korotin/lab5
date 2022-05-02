package jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ConnectionPool {
    private final static BasicDataSource pool = new BasicDataSource();

    static {
        pool.setUrl("jdbc:postgresql://localhost:2222/postgres");
        pool.setUsername("postgres");
        pool.setPassword(getPass());
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(10);
        pool.setTimeBetweenEvictionRunsMillis(5 * 1000);
    }

    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    private static String getPass() {
        System.out.println("Введите пароль подключения к БД");
        return new String(System.console().readPassword());
    }

    private static String getPassDEBUG() {
        return "pass";
    }
}
