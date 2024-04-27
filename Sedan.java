/**
 * The type Sedan.
 */
public class Sedan extends Car{
    /**
     * Instantiates a new Sedan.
     *
     * @param model        the model
     * @param ID           the id
     * @param availability the availability
     * @param price        the price
     * @param fuelType     the fuel type
     * @param capacity     the capacity
     * @param transmission the transmission
     * @param mileage      the mileage
     * @param color        the color
     * @param condition    the condition
     * @param VIN          the vin
     * @param hasTurbo     the has turbo
     */
    public Sedan(String model, int ID, int availability, double price,
    String fuelType, int capacity, String transmission, int mileage, String color,
    String condition, String VIN, Boolean hasTurbo) {

        super("Sedan", model, ID, availability, price, fuelType, capacity,
        transmission, mileage, color, condition, VIN, hasTurbo);

    }
}
