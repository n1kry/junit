import java.time.LocalDate;
import java.util.*;

public class Car implements Comparable<Car> {
    private final String model;
    private final String maker;
    private final String carNumber;
    private final Integer madeYear;
    private LocalDate startedRentOn;
    private LocalDate finishedRentOn;

    public Car(String model, String maker, String carNumber, int madeYear) {
        this.model = model;
        this.maker = maker;
        this.carNumber = carNumber;
        this.madeYear = madeYear;
    }

    public String getModel() {
        return model;
    }

    public String getMaker() {
        return maker;
    }

    public Integer getMadeYear() {
        return madeYear;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public boolean isInRent() {
        return startedRentOn != null && finishedRentOn == null;
    }

    public void setStartedRentOn(LocalDate startedRentOn) {
        this.startedRentOn = startedRentOn;
    }

    public void setFinishedRentOn(LocalDate finishedRentOn) {
        this.finishedRentOn = finishedRentOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return madeYear.equals(car.madeYear) && Objects.equals(model, car.model) && Objects.equals(maker, car.maker) && Objects.equals(carNumber, car.carNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, maker, carNumber, madeYear);
    }

    @Override
    public int compareTo(Car o) {
        return this.model.compareTo(o.model);
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", maker='" + maker + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", madeYear=" + madeYear +
                '}';
    }
}
