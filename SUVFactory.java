public class SUVFactory implements CarFactory {
    @Override
    public Car createCar(String carType, String model, int ID, int availability, double price, String fuelType,
                         int capacity, String transmission, int mileage, String color, String condition, String VIN,
                         Boolean hasTurbo) {
        return new SUV( model, ID, availability, price, fuelType, capacity, transmission, mileage, color, condition, VIN, hasTurbo);
    }
}
