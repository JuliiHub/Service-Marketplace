package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static Properties props;

    private static Properties load() throws SQLException {
        if (props == null) {
            props = new Properties();
            try (FileInputStream in = new FileInputStream("config/config.properties")) {
                props.load(in);
            } catch (IOException e) {
                throw new SQLException("Cannot read config/config.properties", e);
            }
        }
        return props;
    }

    public static Connection getConnection() throws SQLException {
        Properties p = load();
        return DriverManager.getConnection(
                p.getProperty("db.url"), p.getProperty("db.user"), p.getProperty("db.password"));
    }
}