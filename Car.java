import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This is a Car class.
 * This makes the car objects that are in the CSV file.
 */
public class Car implements CarFactory,Printable{

    private String carType;
    private String model;
    private int    ID;
    private int    availability;
    private double price;
    private String fuelType;
    private int    capacity;
    private String transmission;
    private int    mileage;
    private String color;
    private String condition;
    private String VIN;
    private Boolean hasTurbo;


    /**
     * Instantiates a new Car.
     */
    public Car(){
        this.carType       = "Hatchback";
        this.model         = "Honda Fit";
        this.ID            = 0;
        this.availability  = 0;
        this.price         = 0.0;
        this.fuelType      = "Hybrid";
        this.capacity      = 0;
        this.transmission  = "Automatic";
        this.mileage       = 0;
        this.color         = "White";
        this.condition     = "New";
        this.VIN           = "NULL";
        this.hasTurbo      = false;
    }

    /**
     * Instantiates a new Car.
     *
     * @param carType      the type of car
     * @param model        the car model
     * @param ID           the car's ID
     * @param availability the amount of cars available
     * @param price        the car's price
     * @param fuelType     the type of fuel the car takes
     * @param capacity     the amount of available seats
     * @param transmission the car's transmission
     * @param mileage      the amount of miles the car currently has
     * @param color        the car's color
     * @param condition    the condition the car is in
     * @param VIN          the car's VIN number
     * @param hasTurbo     the indicator if car has a turbo
     */
    public Car(String carType, String model, int ID, int availability, double price, String fuelType, int capacity,
               String transmission, int mileage, String color, String condition, String VIN, Boolean hasTurbo) {
        this.carType = carType;
        this.model = model;
        this.ID = ID;
        this.availability = availability;
        this.price = price;
        this.fuelType = fuelType;
        this.capacity = capacity;
        this.transmission = transmission;
        this.mileage = mileage;
        this.color = color;
        this.condition = condition;
        this.VIN = VIN;
        this.hasTurbo = hasTurbo;
    }

    //
    @Override
    public Car createCar(String carType, String model, int ID, int availability, double price, String fuelType,
                         int capacity, String transmission, int mileage, String color, String condition, String VIN,
                         Boolean hasTurbo) {
        return new Car(carType, model, ID, availability, price, fuelType, capacity, transmission, mileage, color, condition, VIN, hasTurbo);
    }

    /**
     * Create car car.
     *
     * @return the car
     */
    public static Car input_createCar(Map<String, CarFactory> factory) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter car type:");
        String carType = scanner.nextLine();

        System.out.println("Enter model:");
        String model = scanner.nextLine();

        System.out.println("Enter ID:");
        int ID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter availability:");
        int availability = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter fuel type:");
        String fuelType = scanner.nextLine();

        System.out.println("Enter capacity:");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter transmission:");
        String transmission = scanner.nextLine();


        System.out.println("Enter color:");
        String color = scanner.nextLine();

        System.out.println("Enter condition:");
        String condition = scanner.nextLine();

        System.out.println("Enter VIN:");
        String VIN = scanner.nextLine();

        System.out.println("Enter if has turbo (true/false):");
        boolean hasTurbo = scanner.nextBoolean();

        scanner.close();
        CarFactory carFactory = factory.get(carType);
        // Create and return a new Car object with the user input
        return carFactory.createCar(carType, model, ID, availability, price, fuelType, capacity, transmission, 0, color, condition, VIN, hasTurbo);
    }

    //move display car??
    /**
     * Displays cars based on the given condition and budget criteria.
     *
     * @param condition The condition of the car to display. Pass "null" to display all cars.
     * @param budget    The maximum budget for the car. Pass 0 to ignore budget criteria.
     * @param car_list  the car list
     */
    public static void displayCars(String condition, double budget, HashMap<Integer, Car> car_list) {
        // Display header
        System.out.println("ID\tCar Type\tModel\tCondition\tColor\tFuel Type\tCapacity\tTransmission\tMileage\tVIN\tTurbo\tPrice\tStock");
        // Iterate over cars
        for (Car car : car_list.values()) {
            //shows all cars
            if (condition.equalsIgnoreCase("null")){
                car.print_menu();
            }
            // Check if the condition and price match the criteria
            if (car.getCondition().equalsIgnoreCase(condition) && car.getPrice() <= budget) { //maybe add a with in range of a couple 100s
                // Display car information
                car.print_menu();
            }
            if (car.getCondition().equalsIgnoreCase(condition) && budget == 0){ //display cars with condition no budget
                car.print_menu();
            }
        }
    }

    public void print_menu(){
        System.out.println(getID() + "\t" +getCarType() + "\t" + getModel() + "\t" +
                getCondition() + "\t" + getColor() + "\t" + getFuelType() + "\t" +
                getCapacity()+ "\t" +getTransmission()+ "\t" +getMileage()+ "\t" +
                getVIN()+ "\t" +getPrice()+ "\t" +getAvailability());
    }


    /**
     * Sethas turbo.
     *
     * @param hasTurbo the has turbo
     */
    public void sethasTurbo(boolean hasTurbo){
        this.hasTurbo = hasTurbo;
    }

    /**
     * Gethas turbo boolean.
     *
     * @return the boolean
     */
    public boolean gethasTurbo(){
        return this.hasTurbo;
    }

    /**
     * Sets the car's type
     *
     * @param carType the car's type
     */
    public void setCarType(String carType){
        this.carType = carType;
    }

    /**
     * Sets the car's model
     *
     * @param model teh car's model
     */
    public void setModel(String model){
        this.model = model;
    }

    /**
     * Sets the car's ID
     *
     * @param id the car's ID
     */
    public void setID(int id){
        this.ID = id;
    }

    /**
     * Sets the cars availability
     *
     * @param amnt the amount of cars available
     */
    public void setAvailability(int amnt){
        this.availability = amnt;
    }

    /**
     * Sets the car's price
     *
     * @param price the car's price
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Sets the car's fuel type
     *
     * @param fuelType the car's fuel type
     */
    public void setFuelType(String fuelType){
        this.fuelType = fuelType;
    }

    /**
     * Sets the car's capacity
     *
     * @param capacity the number of seats in the car
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * Sets the car's transmission
     *
     * @param transmission the car's transmission
     */
    public void setTransmission(String transmission){
        this.transmission = transmission;
    }

    /**
     * Sets the car's mileage
     *
     * @param miles the cars mileage
     */
    public void setMileage(int miles){
        this.mileage = miles;
    }

    /**
     * Sets the car's color
     *
     * @param color the car's color
     */
    public void setColor(String color){
        this.color = color;
    }

    /**
     * Sets the car's condition
     *
     * @param condition the car's condition
     */
    public void setCondition(String condition){
        this.condition = condition;
    }

    /**
     * Sets the car's VIN
     *
     * @param VIN the car's VIN
     */
    public void setVIN(String VIN){
        this.VIN = VIN;
    }

    /**
     * Gets the car's type
     *
     * @return the car's type as a String
     */
    public String getCarType(){
        return this.carType;
    }

    /**
     * Gets the car's model
     *
     * @return the car's model as a String
     */
    public String getModel(){
        return this.model;
    }

    /**
     * Gets the car's ID
     *
     * @return the car's ID as an int
     */
    public int getID(){
        return this.ID;
    }

    /**
     * Gets the car's availability
     *
     * @return the car's availability as an int
     */
    public int getAvailability(){
        return this.availability;
    }

    /**
     * Gets the car's price
     *
     * @return the car's price as a double
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Gets the car's fuel type
     *
     * @return the car's fuel type as a String
     */
    public String getFuelType(){
        return this.fuelType;
    }

    /**
     * Gets the car's capacity
     *
     * @return the car's capacity as an int
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * Gets the car's transmission
     *
     * @return the car's transmission as an int
     */
    public String getTransmission(){
        return this.transmission;
    }

    /**
     * Gets the car's mileage
     *
     * @return the cars mileage as an int
     */
    public int getMileage(){
        return this.mileage;
    }

    /**
     * Gets the car's color
     *
     * @return the car's color as a String
     */
    public String getColor(){
        return this.color;
    }

    /**
     * Gets the car's condition
     *
     * @return the car's condition as a String
     */
    public String getCondition(){
        return this.condition;
    }

    /**
     * Gets hte car's VIN
     *
     * @return the car's VIN as a String
     */
    public String getVIN(){
        return this.VIN;
    }
}
