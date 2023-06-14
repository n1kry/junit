package exception;

public class CarParkingCapacityReached extends RuntimeException{
    public CarParkingCapacityReached(String message) {
        super(message);
    }
}
