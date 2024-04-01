package Project;

public class Car{

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
    }

    public void setCarType(String carType){
        this.carType = carType;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setID(int id){
        this.ID = id;
    }

    public void setAvailability(int amnt){
        this.availability = amnt;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setFuelType(String fuelType){
        this.fuelType = fuelType;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    
    public void setTransmission(String transmission){
        this.transmission = transmission;
    }

    public void setMileage(int miles){
        this.mileage = miles;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }

    public void setVIN(String VIN){
        this.VIN = VIN;
    }

    public String getCarType(){
        return this.carType;
    }

    public String getModel(){
        return this.model;
    }

    public int getID(){
        return this.ID;
    }

    public int getAvailability(){
        return this.availability;
    }

    public double getPrice(){
        return this.price;
    }

    public String getFuelType(){
        return this.fuelType;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public String getTransmission(){
        return this.transmission;
    }

    public int getMileage(){
        return this.mileage;
    }

    public String getColor(){
        return this.color;
    }

    public String getCondition(){
        return this.condition;
    }

    public String getVIN(){
        return this.VIN;
    }
}
