package service;

import dao.CarDao;
import dao.ICarDao;
import model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarService {
    private final ICarDao dao = new CarDao();

    public List<Car> findAll() throws SQLException {
        ResultSet resultSet = dao.findAll();
        List<Car> cars = new ArrayList<>();

        while (resultSet.next()) {
            Car car = new Car();
            car.setModel(resultSet.getString("model"));
            car.setMaker(resultSet.getString("maker"));
            car.setCarNumber(resultSet.getString("carnumber"));
            car.setMadeYear(resultSet.getInt("madeyear"));
            if (resultSet.getDate("startedrenton") != null) {
                car.setStartedRentOn(resultSet.getDate("startedrenton").toLocalDate());
            }
            if (resultSet.getDate("startedrenton") != null) {
                car.setStartedRentOn(resultSet.getDate("fineshedrenton").toLocalDate());
            }
            cars.add(car);
        }
        return cars;
    }
}
