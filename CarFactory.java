/**
 * The interface Car factory.
 */
public interface CarFactory {
    Car createCar(String carType, String model, int ID, int availability, double price, String fuelType,
                  int capacity, String transmission, int mileage, String color, String condition, String VIN,
                  Boolean hasTurbo);
}

