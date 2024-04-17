import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class RunShop {
    static Map<String, User> users_list = new HashMap<>();
    //path //TO BE CHANGED FOR WHEN RUNNING CODE
    //this will be used to get car info and when editing car_data
    //CSV_helper run_csv = new CSV_helper();

    private static HashMap<Integer, Car> car_list = new HashMap<>();
    private static final String car_file = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/Part_2/car_data.csv";
    private static String car_outputFile = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/new_car_data.csv";
    private static final String user_file = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/Part_2/user_data.csv";
    private static String new_user_data = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/new_user_data.csv";
    //This will be the log file of all users actions
    private static final String logFile = "log.txt";

    private static HashMap<String, int[]> tickets = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Make user with create user
        // User user1 = createUser(); //when enter information by hand
        users_list =CSV_helper.user_map_from_csv(user_file, (HashMap<String, User>) users_list);
        //Test user
        User user1 = new User("Seb", "Lev", 17293.00, 0, true, "Seb1", "123");
        users_list.put(user1.getUsername(), user1);

        car_list = CSV_helper.cars_map_from_csv(car_file, car_list);

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
                System.out.println("\nMenu");//change this in order of options
                System.out.println("1. Display all cars");
                System.out.println("2. Filter cars ");
                System.out.println("3. Purchase Car");
                System.out.println("4. View Tickets");
                System.out.println("5. Sign out");
                String menu_input = scanner.nextLine();
                switch (menu_input) {
                    case "1":
                        // Display all cars
                        displayCars("null", 0);
                        break;

                    case "2":
                        // Filter cars by new or used

                        System.out.println("Filter by new or used? (new/used/go back)");
                        String new_used = scanner.nextLine();
                        if (new_used.equalsIgnoreCase("new") || new_used.equalsIgnoreCase("old")) {
                            //displayCars(new_used, -1);

                            System.out.println("3. Filter cars by budget? (y/n)");
                            String input_bud = scanner.nextLine();
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
                        }else if(new_used.equalsIgnoreCase("go back")) {
                            break;
                        }else {
                            System.out.println("Not a valid input (new/old/go back) returning to menu.");
                            break;
                        }
                        break;
                    case "3":
                        //purchase car logic
                        System.out.println("3. Purchase Car (y/n)");
                        String input = scanner.nextLine();
                        boolean purchase_input = input.equalsIgnoreCase("y");
                        if (purchase_input) {
                            System.out.println("Enter the ID of the car");
                            try {
                                int input_ID = scanner.nextInt();
                                boolean check = purchase_car_check(input_ID, curr);
                                if (check) {

                                    //purchase_remove(input_ID);
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
                        break;
                    case "4":
                        // View Tickets
                        print_Tickets(curr.getUsername());
                        break;
                    case "5":
                        // Sign out
                        CSV_helper.purchase_remove(car_outputFile,car_file,tickets);
                        CSV_helper.create_new_user_data(new_user_data, (HashMap<String, User>) users_list);
                        scanner.close(); // Close the scanner when done
                        System.out.println("Signing out...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again. (1,2,3,4,5)");
                }
            }

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



}





//-_-



