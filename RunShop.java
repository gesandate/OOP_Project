import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class RunShop {
    private static Map<String, User> users_list = new HashMap<>();
    //path //TO BE CHANGED FOR WHEN RUNNING CODE
    //private static final String user_file= "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/user_data.csv";
    //this will be used to get car info and when editing car_data
    private static HashMap<Integer, Car> car_list = new HashMap<>();
    private static final String car_file = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/car_data.csv";
    private static final String user_file = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/user_data.csv";
    //This will be the log file of all users actions
    private static final String logFile = "log.txt";

    private static HashMap<String, int[]> tickets = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Make user with create user
        // User user1 = createUser(); //when enter information by hand

        //Test user
        User user1 = new User("Seb", "Lev", 17293.00, 0, true, "Seb1", "123");
        users_list.put(user1.getUsername(), user1);

        //String user_file = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/user_data.csv";
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
            User curr = users_list.get(username);
            while (true) {
                System.out.println("Menu");//change this in order of options
                System.out.println("1. Display all cars? (y/n)");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    System.out.println("Exiting...");
                    break; // Exit
                }
                boolean displayAllCars = input.equalsIgnoreCase("y");

                if (displayAllCars) {
                    //call add cars before display cars
                    add_cars();
                    System.out.println("2. Filter cars by new or used? (new/used/n)");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("Exit")) {
                        break; // Exit the program
                    }

                    String new_used = "null";
                    if (input.equalsIgnoreCase("new") || input.equalsIgnoreCase("used")) {
                        new_used = input.toLowerCase();
                    }else if (input.equalsIgnoreCase("n")) {
                        System.out.println("Display call cars");
                        displayCars(new_used, -1);
                        //break;
                    }

                    System.out.println("3. Filter cars by budget? (y/n)");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("Exit")) {
                        break;
                    }
                    boolean filterByBudget = input.equalsIgnoreCase("y");

                    Double budget = null;
                    if (filterByBudget) {
                        try {
                            budget = curr.getBudget();
                            System.out.println(curr.getUsername()+" current budget: "+budget);
                            displayCars(new_used, budget);
                            //break; //for testing
                        } catch (NumberFormatException e) {
                            System.out.println("Something wrong with budget contact admin");
                            continue; // loop
                        }

                    }else{
                        displayCars(new_used, 0);
                        //break;
                    }
                    //System.out.println("END OF IF display section");
                    //displayCars(new_used, budget);
                } else { //***need to add a go back input
                    // Do nothing if the user chose not to display all cars
                    System.out.println("No cars to display.");
                }
                //add puchase part
                System.out.println("3. Purchase Car");
                System.out.println("Enter the ID of the car");
                int input_ID = scanner.nextInt();
                boolean check = purchase_car_check(input_ID,curr);
                if (check){
                    purchase_remove(input_ID);
                    add_Ticket(curr.getUsername(), input_ID);
                    int cars_pur =curr.getCarsPurchased();
                    curr.setCarsPurchased(cars_pur+1);
                    users_list.put(curr.getUsername(), curr);
                }
                System.out.println("4. View Tickets (y/n)");
                String input_tic = scanner.nextLine();
                if (input.equalsIgnoreCase("Exit")) {
                    System.out.println("Exiting...");
                    break; // Exit
                } else if (input.equalsIgnoreCase("y")) {
                    print_Tickets(curr.getUsername());
                }
                System.out.println("5. Sign out (exit/n)");
                String input_e = scanner.nextLine();
                if (input_e.equalsIgnoreCase("exit")) {
                    System.out.println("Signing out...");
                    break;
                }
            }

            //purchase_remove(input_ID);
            create_new_user_data();
            scanner.close(); // Close the scanner when done
        }
    }

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
        System.out.println("ID\tCar Type\tModel\tCondition\tColor\tFuel Type\tCapacity\tTransmission\tMileage\tVIN\tPrice");
        // Iterate over cars
        for (Car car : car_list.values()) {
            //shows all cars
            if (condition.equalsIgnoreCase("null")){
                System.out.println(car.getID() + "\t" + car.getCarType() + "\t" + car.getModel() + "\t" +
                        car.getCondition() + "\t" + car.getColor() + "\t" + car.getFuelType() + "\t" +
                        car.getCapacity()+ "\t" +car.getTransmission()+ "\t" +car.getMileage()+ "\t" +
                        car.getVIN()+ "\t" +car.getPrice());
            }
            // Check if the condition and price match the criteria
            if (car.getCondition().equalsIgnoreCase(condition) && car.getPrice() <= budget) { //maybe add a with in range of a couple 100s
                // Display car information
                System.out.println(car.getID() + "\t" + car.getCarType() + "\t" + car.getModel() + "\t" +
                        car.getCondition() + "\t" + car.getColor() + "\t" + car.getFuelType() + "\t" +
                        car.getCapacity()+ "\t" +car.getTransmission()+ "\t" +car.getMileage()+ "\t" +
                        car.getVIN()+ "\t" +car.getPrice());
            }

            if (car.getCondition().equalsIgnoreCase(condition) && budget == 0){ //display cars with condition no budget
                System.out.println(car.getID() + "\t" + car.getCarType() + "\t" + car.getModel() + "\t" +
                        car.getCondition() + "\t" + car.getColor() + "\t" + car.getFuelType() + "\t" +
                        car.getCapacity()+ "\t" +car.getTransmission()+ "\t" +car.getMileage()+ "\t" +
                        car.getVIN()+ "\t" +car.getPrice());
            }
        }
    } //need to add avalibility to display

    //checks if requirnments for purchase are met
    public static boolean purchase_car_check(int id, User curr){
        for (Car car : car_list.values()) {
            if (car.getID() == id){
                if (curr.getBudget() >= car.getPrice()){
                    if (car.getAvailability()>=1){
                        return true;
                    }else{

                        int old_a = car.getAvailability();
                        car.setAvailability(old_a-1);
                        car_list.put(id,car);
                        System.out.println("Car selected is not in stock");
                    }
                }else{
                    System.out.println("Car selected is outside of budget");
                }
            }
        }
        return false;
    }

    public static void purchase_remove(int id){
        //**CHANGE WHERE OUTPUT FILE IS MADE**//
        String outputFile = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/new_car_data.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(car_file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String first_line = reader.readLine();

            String line;
            List<String[]> updatedLines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id_file = Integer.parseInt(parts[0]);
                if (id_file ==id) { // Found the id
                    int carsAvailable = Integer.parseInt(parts[parts.length - 1]);
                    carsAvailable--;
                    parts[parts.length - 1] = String.valueOf(carsAvailable);
                }
                updatedLines.add(parts);
            }
            for (String[] parts : updatedLines) {
                writer.write(String.join(",", parts));
                writer.newLine();
            }
            System.out.println("New CSV file created: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add_Ticket(String username, int input_ID) {
        if (tickets.containsKey(username)) {
            //  append id to array
            int[] old_IDs = tickets.get(username);
            int[] new_IDs = new int[old_IDs.length + 1];
            System.arraycopy(old_IDs, 0, new_IDs, 0, old_IDs.length);
            new_IDs[old_IDs.length] = input_ID;
            tickets.put(username, new_IDs);
        } else {
            // not in Tickets so create new
            tickets.put(username, new int[]{input_ID});
        }
    }

    public static void print_Tickets(String username) {
        if (tickets.containsKey(username)) {
            System.out.print(STR."\{username} ticket(s): ");
            for (int id : tickets.get(username)) {
                Car car = car_list.get(id);
                if(car != null){ //check if car was added/removed from car_list
                    System.out.println("Car ID: "+ id);
                    System.out.println("Car Type: " + car.getCarType() );
                    System.out.println("Model: " + car.getModel());
                    System.out.println("Color: " + car.getColor());
                }
            }
            System.out.println("\n");
        } else {
            System.out.println("No tickets for user " + username);
        }//lol
    }

    public static void create_new_user_data() {
        //**this should be changed for use
        String new_user_data = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/new_user_data.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new_user_data))) {
            // Write CSV header
            writer.write("ID,First Name,Last Name,Money Available,Cars Purchased,MinerCars Membership,Username,Password");
            writer.newLine();
            int id = 1;
            // iterate for all users
            for (User user : users_list.values()) {
                // format for csv
                String userData = String.format("%d,%s,%s,%.2f,%d,%b,%s,%s",
                        id,user.getFirstName(), user.getLastName(), user.getBudget(),
                        user.getCarsPurchased(), user.getMembership(), user.getUsername(), user.getPassword());
                id = 1+id;
                writer.write(userData);
                writer.newLine();
            }
            System.out.println("New user data file created: " + new_user_data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}





//-_-



