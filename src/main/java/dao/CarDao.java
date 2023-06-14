package dao;

import consts.DBConsts;
import util.CarsDBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CarDao implements ICarDao {
    private static final CarsDBConnection DB_CONNECTION = new CarsDBConnection();
    @Override
    public ResultSet findAll() {
        try (Connection connection = DB_CONNECTION.getConnection()) {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(DBConsts.SELECT_ALL_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
