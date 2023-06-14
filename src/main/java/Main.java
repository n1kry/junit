import model.Car;
import model.CarParking;
import service.CarService;
import util.CSVToDBConverter;

import javax.sound.midi.Soundbank;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {
    private static CarService service = new CarService();
    public static void main(String[] args) throws SQLException {
        CSVToDBConverter converter = new CSVToDBConverter();
        converter.convert();
        CarParking parking = new CarParking();
        parking.add(service.findAll().toArray(new Car[0]));
        parking.groupByMaker().forEach((m,e) -> {
            System.out.println(m);
            System.out.println(e);
        });
    }
}
