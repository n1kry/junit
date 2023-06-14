package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CarsDBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/cars";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1212";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
