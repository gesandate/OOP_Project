import java.io.*;
import java.util.*;

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
                //System.out.println("New user created: " + newUser.getFirstName() + " " + newUser.getLastName());
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
        Log main_log = new Log();
        if (is_user){
            User curr = users_list.get(username);
            main_log.logAction(curr, "login", logFile);
            while (true) {
                System.out.println("Menu");//change this in order of options
                System.out.println("1. Display all cars? (y/n)");
                String input_dis = scanner.nextLine();
                if (input_dis.equalsIgnoreCase("Exit")) {
                    System.out.println("Exiting...");
                    break; // Exit
                }
                boolean displayAllCars = input_dis.equalsIgnoreCase("y");

                if (displayAllCars) {
                    //call add cars before display cars
                    add_cars();
                    System.out.println("2. Filter cars by new or used? (new/used/n)");
                    String input_fil = scanner.nextLine();
                    if (input_fil.equalsIgnoreCase("Exit")) {
                        break; // Exit the program
                    }

                    String new_used = "null";
                    if (input_fil.equalsIgnoreCase("new") || input_fil.equalsIgnoreCase("used")) {
                        new_used = input_fil.toLowerCase();
                    }else if (input_fil.equalsIgnoreCase("n")) {
                        System.out.println("Display call cars");
                        displayCars(new_used, -1);
                        //break;
                    }

                    System.out.println("3. Filter cars by budget? (y/n)");
                    String input_bud = scanner.nextLine();
                    if (input_bud.equalsIgnoreCase("Exit")) {
                        break;
                    }
                    boolean filterByBudget = input_bud.equalsIgnoreCase("y");

                    Double budget = null;
                    if (filterByBudget) {
                        try {
                            budget = curr.getBudget();
                            //here need to -6.25% and if member +10%
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
                //
                System.out.println("3. Purchase Car (y/n)");
                String input = scanner.nextLine();
                boolean purchase_input = input.equalsIgnoreCase("y");
                if (purchase_input) {
                    System.out.println("Enter the ID of the car");
                    try {
                        int input_ID = scanner.nextInt();
                        boolean check = purchase_car_check(input_ID, curr);
                        if (check) {
                            //puchase if possible
                            purchase_remove(input_ID);
                            //make a tick for car purchase
                            add_Ticket(curr.getUsername(), input_ID);
                            int cars_pur = curr.getCarsPurchased();
                            //increase the number of cars purchased by user in users_list
                            curr.setCarsPurchased(cars_pur + 1);
                            users_list.put(curr.getUsername(), curr);
                            //get price of car purchased
                            double car_price_paid = (car_list.get(input_ID)).getPrice();//tax here?
                            //set new budget of user
                            curr.setBudget(curr.getBudget() - car_price_paid);
                            users_list.put(curr.getUsername(), curr);
                        } else {
                            System.out.println("ID does not exist");
                        }
                        System.out.println();
                    }catch (InputMismatchException e) {
                        System.out.println("Did not enter a valid numerical ID");
                        scanner.next();
                    }
                }
                System.out.println("4. View Tickets (y/n)");
                String input_tic = scanner.nextLine();
                boolean show_tics = input_tic.equalsIgnoreCase("y");
                if(show_tics) {
                    print_Tickets(curr.getUsername());
                }else{
                    System.out.println("\n");
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


    /**
     * This method creates a new User object by prompting the user to input their information.
     *
     * @return The newly created User object with the user's information.
     */
    public static User createUser() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        // Prompt for and set first name
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        user.setFirstName(firstName);

        // Prompt for and set last name
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        user.setLastName(lastName);

        // Prompt for and set budget
        System.out.print("Enter budget: ");
        double budget = scanner.nextDouble();
        user.setBudget(budget);

        // Prompt for and set number of cars purchased
        System.out.print("Enter number of cars purchased: ");
        int carsPurchased = scanner.nextInt();
        user.setCarsPurchased(carsPurchased);

        // Prompt for and set Miner Cars membership
        System.out.print("Are you a Miner Cars member? (y/n): ");
        boolean member = scanner.nextLine().equalsIgnoreCase("y");
        user.setMembership(member);

        scanner.nextLine(); // Consume newline
        // Prompt for and set username
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        user.setUsername(username);

        // Prompt for and set password
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        user.setPassword(password);

        // Return the created User object
        return user;
    }

    /**
     * Attempts to log in a user with the given username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return true if the login attempt is successful, false otherwise.
     */
    public static boolean login(String username, String password) {
        // Check if the entered username exist and if the password matches
        if (users_list.containsKey(username)) {
            User currentUser = users_list.get(username);
            if (currentUser.getPassword().equals(password)) {
                System.out.println(STR."Welcome \{username}");
                return true;
            } else {
                System.out.println("Invalid password. Try again.");
            }
        } else {
            System.out.println("User not found. Contact help.");
        }
        return false;
    }

    /**
     * Reads car information from a file and adds cars to the car list.
     */
    public static void add_cars(){
        try{
            // Create BufferedReader object
            BufferedReader reader = new BufferedReader(new FileReader(car_file));
            reader.readLine();
            // Read each lin of the file
            String line;

            //if parts[i].eqals( "id".equalsIgnoreCase()){
            //    int id_place =  i;
            //}
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

    /**
     * Displays cars based on the given condition and budget criteria.
     *
     * @param condition The condition of the car to display. Pass "null" to display all cars.
     * @param budget    The maximum budget for the car. Pass 0 to ignore budget criteria.
     */
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


    /**
     * Checks if a car with the given ID can be purchased by the current user.
     *
     * @param id   The ID of the car to be purchased.
     * @param curr The current user attempting to purchase the car.
     * @return true if the car can be purchased, false otherwise.
     */
    public static boolean purchase_car_check(int id, User curr){
        for (Car car : car_list.values()) {
            if (car.getID() == id){
                //new price depending on tax and membership
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

    /**
     * Removes a purchased car from the car data file.
     *
     * @param id The ID of the car to be removed.
     */
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

    /**
     * Adds a ticket (ID) to the specified user's entry in the tickets map.
     *
     * @param username The username of the user to whom the ticket will be added.
     * @param input_ID The ID of the ticket to be added.
     */
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



