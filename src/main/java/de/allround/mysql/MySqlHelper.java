package de.allround.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class MySqlHelper {

    private final HikariConfig hikariConfig;
    private HikariDataSource hikariDataSource;

    public MySqlHelper(String host, int port, String user, String database, String password) {
        this.hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean query(String SQL_QUERY, Object... parameters) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(SQL_QUERY)) {
            for (int i = 1; i <= parameters.length; i++) {
                preparedStatement.setObject(i, parameters[i - 1]);
            }
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void connect() {
        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public abstract void createTables();
}
