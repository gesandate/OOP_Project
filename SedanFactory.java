/**
 * The type Sedan factory.
 */
public class SedanFactory implements CarFactory {
    @Override
    public Car createCar(String carType, String model, int ID, int availability, double price, String fuelType,
                         int capacity, String transmission, int mileage, String color, String condition, String VIN,
                         Boolean hasTurbo) {
        return new Sedan( model, ID, availability, price, fuelType, capacity, transmission, mileage, color, condition, VIN, hasTurbo);
    }
}
