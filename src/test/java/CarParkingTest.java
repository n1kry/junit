import exception.CarParkingCapacityReached;
import model.Car;
import model.CarParking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("model.Car parking")
class CarParkingTest {

    private CarParking carParking;
    private Car hondaCivicOld;
    private Car hondaCivicNew;
    private Car bmwM5New;
    private Car bmwM5Old;
    private Car mercedesE220Old;
    private Car mercedesE220New;

    @BeforeEach
    void init() {
        carParking = new CarParking();
        hondaCivicOld = new Car("Civic", "Honda", "ADD231",2006);
        hondaCivicNew = new Car("Civic", "Honda", "ASW001",2020);
        bmwM5New = new Car("M5", "BMW", "EPP1",2016);
        bmwM5Old = new Car("M5", "BMW", "EPJ112",2006);
        mercedesE220Old = new Car("E220", "Mercedes", "GTF241", 2008);
        mercedesE220New = new Car("E220", "Mercedes", "GHR098", 2015);
    }
    @Nested
    @DisplayName("Is empty")
    class IsEmpty {
        @Test
        @DisplayName("when no cars is added to it")
        public void emptyCarParkingWhenNoCarAdded() {
            List<Car> cars = carParking.cars();
            assertTrue(cars.isEmpty(), "model.CarParking should be empty");
        }

        @Test
        @DisplayName("when add is called without cars")
        public void emptyCarParkingWhenAddIsCalledWithoutCars() {
            carParking.add();
            List<Car> cars = carParking.cars();
            assertTrue(cars.isEmpty(), "model.CarParking should be empty");
        }
    }

    @Nested
    @DisplayName("after adding cars")
    class CarsAreAdded {
        @Test
        @DisplayName("contains two cars")
        void carParkingContainsTwoCarsWhenTwoCarsAdded() {
            carParking.add(hondaCivicNew, bmwM5New);
            List<Car> cars = carParking.cars();
            assertEquals(2, cars.size(), "model.CarParking should have two cars");
        }

        @Test
        @DisplayName("returns an immutable cars collection to client")
        void carsParkingIsImmutableForClients() {
            carParking.add(hondaCivicNew,hondaCivicOld);
            List<Car> cars = carParking.cars();
            try {
                cars.add(bmwM5New);
                fail("Should not be able to add book to books");
            } catch (Exception e) {
                assertTrue(e instanceof UnsupportedOperationException, "CarsParking should throw UnsupportedOperationException");
            }
        }

        @Test
        @DisplayName("throws exception when out of capacity")
        void throwsExceptionWhenCarsAreAddedAfterCapacityIsReached() {
            CarParking parking = new CarParking(3);
            parking.add(hondaCivicOld, hondaCivicNew, mercedesE220New);
            CarParkingCapacityReached throwException = assertThrows(CarParkingCapacityReached.class, () -> parking.add(bmwM5New));
            assertEquals("Out of capacity", throwException.getMessage());
        }
    }

    @Nested
    @DisplayName("Is arranged")
    class WhewArranged {
        @Test
        @DisplayName("lexicographically by model")
        void carParkingArrangedByCarMaker() {
            carParking.add(hondaCivicNew, bmwM5New, mercedesE220New);
            List<Car> cars = carParking.arrange();
            assertEquals(Arrays.asList(hondaCivicNew,mercedesE220New, bmwM5New), cars, "Cars in carsParking should be arranged by car model");
        }

        @Test
        @DisplayName("by user provided option (lexicographically by model DESC)")
        void carParkingArrangedByCarMakerByUserProvidedOption() {
            carParking.add(hondaCivicNew, bmwM5New, mercedesE220New);
            List<Car> cars = carParking.arrange(Comparator.<Car>naturalOrder().reversed());
            assertEquals(Arrays.asList(bmwM5New,mercedesE220New,hondaCivicNew),cars,"Cars in carsParking should be arranged in descending order by car model");
        }

        @Test
        @DisplayName("by user provided option (made year in ASC order)")
        void carParkingArrangedByCarMadeYearByUserProvidedOption() {
            carParking.add(hondaCivicNew, bmwM5New, mercedesE220New, hondaCivicOld, mercedesE220Old);
            List<Car> cars = carParking.arrange(Comparator.comparing(Car::getMadeYear));
            assertEquals(Arrays.asList(hondaCivicOld, mercedesE220Old, mercedesE220New, bmwM5New, hondaCivicNew), cars, "Cars in carParking are arranged by car made year in ascending order");
        }
    }

    @Nested
    @DisplayName("cars are grouped by")
    class GroupBy {
        @Test
        @DisplayName("maker")
        void groupCarsInCarParkingByMaker() {
            carParking.add(hondaCivicNew, bmwM5New, mercedesE220New, hondaCivicOld, mercedesE220Old, bmwM5Old);
            Map<String, List<Car>> cars = carParking.groupByMaker();
            assertTrue(cars.get("Honda").containsAll(Arrays.asList(hondaCivicNew, hondaCivicOld)), "Cars grouped by Honda");
            assertTrue(cars.get("BMW").containsAll(Arrays.asList(bmwM5Old, bmwM5New)), "Cars grouped by BMW");
            assertTrue(cars.get("Mercedes").containsAll(Arrays.asList(mercedesE220New, mercedesE220Old)), "Cars grouped by Mercedes");
        }

        @Test
        @DisplayName("year")
        void groupCarsInCarParkByMadeYear() {
            carParking.add(hondaCivicNew, bmwM5New, mercedesE220New, hondaCivicOld, mercedesE220Old, bmwM5Old);
            Map<Integer, List<Car>> cars = carParking.groupBy((Car::getMadeYear));
            assertTrue(cars.get(2006).containsAll(Arrays.asList(bmwM5Old, hondaCivicOld)), "Cars grouped by 2006");
            assertTrue(cars.get(2020).contains(hondaCivicNew), "Cars grouped by 2020");
            assertTrue(cars.get(2016).contains(bmwM5New), "Cars grouped by 2016");
            assertTrue(cars.get(2015).contains(mercedesE220New), "Cars grouped by 2015");
        }
    }

    @Nested
    @DisplayName("Find by")
    class FindBy {
        @Test
        @DisplayName("maker")
        void findCarsInCarParkByMaker() {
            carParking.add(hondaCivicNew, bmwM5New, mercedesE220New, hondaCivicOld, mercedesE220Old, bmwM5Old);
            List<Car> cars = carParking.findCarsByMaker("Honda".toLowerCase());
            assertEquals(Arrays.asList(hondaCivicNew, hondaCivicOld), cars, "Cars in carParking should be made by Honda");
        }
    }

    @Nested
    @DisplayName("statistic")
    class Stats {
        @Test
        @DisplayName("how many cars in rent now")
        void printCurrentStatisticWithAddedCars() {
            Car car = new Car("Passat","VolksWagen", "SVGT245", 1992);
            car.setStartedRentOn(LocalDate.now());
            carParking.add(hondaCivicNew, bmwM5New, mercedesE220New, bmwM5Old, car);
            assertEquals(20,carParking.statistic(), "Percentage of rented cars");
        }
        @Test
        @DisplayName("how many cars in rent now without cars")
        void printCurrentStatisticWithoutCars() {
            assertEquals(0,carParking.statistic(), "Percentage of rented cars");
        }
        @Test
        @DisplayName("how many cars in rent now when rent is over")
        void printCurrentStatisticWhenRentOver() {
            Car car = new Car("Passat","VolksWagen", "SVGT245", 1992);
            car.setFinishedRentOn(LocalDate.now());
            carParking.add(hondaCivicNew, bmwM5New, mercedesE220New, bmwM5Old, car);
            assertEquals(0,carParking.statistic(), "Percentage of rented cars");
        }
    }
}