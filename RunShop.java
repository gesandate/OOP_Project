import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RunShop implements Printable{
    static Map<String, User> users_list = new HashMap<String ,User>();
    //path //TO BE CHANGED FOR WHEN RUNNING CODE
    //this will be used to get car info and when editing car_data


    private static HashMap<Integer, Car> car_list = new HashMap<>();
    private static final String car_file = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/Part_2/car_data.csv";
    private static String car_outputFile = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/new_car_data.csv";
    private static final String user_file = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/Part_2/user_data.csv";
    private static String new_user_data = "C:/Users/sebas/OneDrive/notes/CS 3331 Adv. Object-Oriented Proframming/Project 1/new_user_data.csv";
    //This will be the log file of all users actions
    private static final String logFile = "log.txt";

    private static HashMap<String, int[]> tickets = new HashMap<>();

    public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Make user with create user
        // User user1 = createUser(); //when enter information by hand
        users_list =CSV_helper.user_map_from_csv(user_file, (HashMap<String, User>) users_list);

        //Test user
        //User.createUser();
        User user1 = new User("Seb", "Lev", 50000.00, 0, true, "Seb1", "123");
        users_list.put(user1.getUsername(), user1);

        car_list = CSV_helper.cars_map_from_csv(car_file, car_list);

        // Get user input for username and password
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        //if (username.equalsIgnoreCase("admin"){
            //call admin_method
        //}

        boolean is_user = login(username,password);
        Log main_log = new Log();
        if (is_user){
            User curr = users_list.get(username);
            main_log.logAction(curr, "login", logFile);
            while (true) {
                print_menu();
                String menu_input = scanner.nextLine();
                switch (menu_input) {
                    case "1":
                        // Display all cars
                        Logic.displayCars("null", 0,car_list);
                        break;

                    case "2":
                        // Filter cars by new or used

                        System.out.println("Filter by new or used? (new/used/go back)");
                        String new_used = scanner.nextLine();
                        if (new_used.equalsIgnoreCase("new") || new_used.equalsIgnoreCase("used")) {
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
                                    Logic.displayCars(new_used, budget, car_list);
                                    //break; //for testing
                                } catch (NumberFormatException e) {
                                    System.out.println("Something wrong with budget contact admin");
                                    continue; // loop
                                }

                            }else{
                                Logic.displayCars(new_used, 0, car_list);
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
                        boolean check = false;
                        String input = scanner.nextLine();
                        boolean purchase_input = input.equalsIgnoreCase("y");
                        if (purchase_input) {
                            int input_ID = -1;
                            boolean isValid_Input = false;
                            while (!isValid_Input) {
                                System.out.println("Enter the ID of the car");
                                // Check if the next input is an integer
                                if (scanner.hasNextInt()) {
                                    input_ID = scanner.nextInt();
                                    isValid_Input = true;
                                } else {
                                    scanner.next();
                                    System.out.println("Did not enter a valid numerical ID");
                                }
                            }
                            if (input_ID == -1){
                                break;
                            }
                            check = Logic.purchase_car(input_ID, curr, (HashMap<String, User>) users_list,car_list);
                            if (check) {
                                //make a tick for car purchase
                                tickets = Logic.add_Ticket(curr.getUsername(), input_ID,tickets);
                            } else {
                                System.out.println("ID does not exist");
                            }
                            System.out.println();
                        }
                        break;
                    case "4":
                        // View Tickets
                        Logic.print_Tickets(curr.getUsername(),car_list,tickets);
                        break;
                    case "5":
                        //car return
                        int input_ID = -1;
                        boolean isValid_Input = false;

                        while (!isValid_Input) {
                            System.out.println("Enter ID of the car you wish to return:");
                            // Check if the next input is an integer
                            if (scanner.hasNextInt()) {
                                input_ID = scanner.nextInt();
                                isValid_Input = true;
                            } else {
                                scanner.next();
                                System.out.println("Invalid input. Please enter a valid integer ID.");
                            }
                        }
                        Logic.car_return(curr, input_ID, (HashMap<String, User>) users_list,car_list,tickets);
                        break;
                    case "6":
                        // Sign out
                        CSV_helper.purchase_remove(car_outputFile,car_file,tickets);
                        CSV_helper.create_new_user_data(new_user_data, (HashMap<String, User>) users_list);
                        scanner.close(); // Close the scanner when done
                        System.out.println("Signing out...");
                        return;
                    default:
                        System.out.println("(1,2,3,4,5,6)");
                }
            }

        }

    }

    public void print_menu(){
        System.out.println("\nMenu");//change this in order of options
        System.out.println("1. Display all cars");
        System.out.println("2. Filter cars ");
        System.out.println("3. Purchase Car");
        System.out.println("4. View Tickets");
        System.out.println("5. Car Return");
        System.out.println("6. Sign out");
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
                System.out.println("Welcome "+username);
                return true;
            } else {
                System.out.println("Invalid password. Try again.");
            }
        } else {
            System.out.println("User not found. Contact help.");
        }
        return false;
    }



}





//-_-



