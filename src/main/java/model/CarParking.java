package model;

import exception.CarParkingCapacityReached;
import model.Car;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CarParking {

    private final List<Car> cars = new ArrayList<>();
    private final int capacity;

    public CarParking() {
        capacity = Integer.MAX_VALUE;
    }

    public CarParking(int capacity) {
        this.capacity = capacity;
    }

    public List<Car> cars() {
        return Collections.unmodifiableList(cars);
    }

    public void add(Car... carsToAdd) {
        Arrays.stream(carsToAdd).forEach(
                car -> {
                    if (cars.size() == capacity) {
                        throw new CarParkingCapacityReached("Out of capacity");
                    }
                    cars.add(car);
                }
        );
    }

    public List<Car> arrange() {
        return arrange(Comparator.naturalOrder());
    }

    public List<Car> arrange(Comparator<Car> comparator) {
        return cars.stream()
                .sorted(comparator)
                .toList();
    }

    public Map<String, List<Car>> groupByMaker() {
        return groupBy(Car::getMaker);
    }

    public <K> Map<K, List<Car>> groupBy(Function<Car, K> function) {
        return cars.stream()
                .collect(Collectors.groupingBy(function));
    }

    public int statistic() {
        if (cars.isEmpty()) {
            return 0;
        }
        int carsInProgress = Long.valueOf(cars.stream().filter(Car::isInRent).count()).intValue();

        return carsInProgress * 100 / cars.size();
    }

    public List<Car> findCarsByMaker(String maker) {
        return cars.stream()
                .filter(c -> c.getMaker().toLowerCase().contains(maker))
                .toList();
    }
}
