import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;

public class RunShop {
    private static Map<String, User> users_list = new HashMap<>();
    //path //TO BE CHANGED FOR WHEN RUNNING CODE
    private static final String user_file= "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/user_data.csv";
    //this will be used to get car info and when editing car_data
    private static HashMap<Integer, Car> car_list = new HashMap<>();
    private static final String car_file = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/car_data.csv";
    //This will be the log file of all users actions
    private static final String logFile = "log.txt";
    public static User createUser() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        user.setFirstName(firstName);

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        user.setLastName(lastName);

        System.out.print("Enter budget: ");
        double budget = scanner.nextDouble();
        user.setBudget(budget);

        System.out.print("Enter number of cars purchased: ");
        int carsPurchased = scanner.nextInt();
        user.setCarsPurchased(carsPurchased);

        System.out.print("Are you a Miner Cars member? (y/n): ");
        boolean member = scanner.nextLine().equalsIgnoreCase("y");
        user.setMembership(member);

        scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        user.setUsername(username);

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        user.setPassword(password);

        return user;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Make user with create user
        // User user1 = createUser(); //when enter information by hand

        //Test user
        User user1 = new User("Seb", "Lev", 500.25, 0, true, "Seb1", "123");
        users_list.put(user1.getUsername(), user1);


        try {
            // Create a FileReader object to read the CSV file
            FileReader fileReader = new FileReader(user_file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            // Read the CSV file line by line
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into fields using the comma as a delimiter
                String[] fields = line.split(",");

                // Extract information from the fields
                String firstName = fields[1];
                //System.out.println(firstName);

                String lastName = fields[2];
                //System.out.println(lastName);

                double budget = Double.parseDouble(fields[3]);
                //System.out.println(budget);

                int carsPurchased = Integer.parseInt(fields[4]);

                boolean minerCarsMember = Boolean.parseBoolean(fields[5]);
                String username = fields[6];
                String password = fields[7];

                // Create a new User object using the extracted information
                User newUser = new User(firstName, lastName, budget, carsPurchased, minerCarsMember, username, password);

                //add user to map
                users_list.put(newUser.getUsername(), newUser);

                // print line for testing
                System.out.println("New user created: " + newUser.getFirstName() + " " + newUser.getLastName());
            }
            // Close the BufferedReader
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get user input for username and password
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        boolean is_user = login(username,password);

        if (is_user){
            while (true) {
                System.out.println("Menu");
                System.out.println("1. Display all cars? (y/n)");

                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    System.out.println("Exiting...");
                    break; // Exit
                }
                boolean displayAllCars = input.equalsIgnoreCase("y");

                if (displayAllCars) {
                    System.out.println("2. Filter cars by new or used? (new/used/n)");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("Exit")) {
                        break; // Exit the program
                    }

                    String new_used = null;
                    if (input.equalsIgnoreCase("new") || input.equalsIgnoreCase("used")) {
                        new_used = input.toLowerCase();
                    }

                    System.out.println("3. Filter cars by budget? (y/n)");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("Exit")) {
                        break;
                    }
                    boolean filterByBudget = input.equalsIgnoreCase("y");

                    Double budget = null;
                    if (filterByBudget) {
                        input = scanner.nextLine();
                        if (input.equalsIgnoreCase("Exit")) {
                            break; // Exit
                        }
                        try {
                            User curr = users_list.get(username);
                            budget = curr.getBudget();
                        } catch (NumberFormatException e) {
                            System.out.println("Something wrong with budget contact admin");
                            continue; // loop
                        }
                    }

                    // Call method to display and filter cars based on user input
                    //displayCars(new_used, budget);
                } else {
                    // Do nothing if the user chose not to display all cars
                    System.out.println("No cars to display.");
                }
            }

            scanner.close(); // Close the scanner when done
        }
    }


    public static boolean login(String username, String password) {
        // Check if the entered username exists and if the password matches
        if (users_list.containsKey(username)) {
            User currentUser = users_list.get(username);
            if (currentUser.getPassword().equals(password)) {
                System.out.println(STR."Welcome \{username}");
                return true;
                // You can perform further actions with the logged in
            } else {
                System.out.println("Invalid password. Try again.");
            }
        } else {
            System.out.println("User not found. Contact help.");
        }
        return false;
    }

    public static void add_cars(){
        try{
            // Create BufferedReader object
            BufferedReader reader = new BufferedReader(new FileReader(car_file));
            reader.readLine();
            // Read each lin of the file
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Extract car information from the line
                int id = Integer.parseInt(parts[0]);
                String carType = parts[1];
                String model = parts[2];
                String condition = parts[3];
                String color = parts[4];
                int capacity = Integer.parseInt(parts[5]);
                int mileage = Integer.parseInt(parts[6]);
                String fuelType = parts[7];
                String transmission = parts[8];
                String vin = parts[9];
                double price = Double.parseDouble(parts[10]);
                int carsAvailable = Integer.parseInt(parts[11]);

                // Create Car object
                Car car = new Car(carType, model, id, carsAvailable, price, fuelType, capacity, transmission, mileage, color, condition, vin);

                // Add car to carList
                car_list.put(id, car);
            }
            // Close file
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayCars(String condition, double budget) {
        // Display header
        System.out.println("ID\tCar Type\tModel\tCondition\tColor\tPrice");

        // Iterate over cars
        for (Car car : car_list.values()) {
            // Check if the condition and price match the criteria
            if (car.getCondition().equalsIgnoreCase(condition) && car.getPrice() <= budget) {
                // Display car information
                System.out.println(car.getID() + "\t" + car.getCarType() + "\t" + car.getModel() + "\t" +
                        car.getCondition() + "\t" + car.getColor() + "\t" + car.getPrice());
            }
        }
    }




}





