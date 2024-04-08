import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RunShop {
    private static Map<String, User> users_list = new HashMap<>();

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
        // User user1 = createUser();
        // store the created user
        //Test user
        User user1 = new User("Seb", "Lev", 500.25, 0, true, "Seb1", "123");
        users_list.put(user1.getUsername(), user1);

        boolean is_user = login();
        if (is_user){
            System.out.println("Options:");
            System.out.println("1. Display all cars? (y/n)");
            System.out.println("2. Filter cars? (y/n)");

            // Take user input for options
            System.out.print("Enter option 1: ");
            boolean displayAllCars = scanner.nextLine().equalsIgnoreCase("y");

            System.out.print("Enter option 2: ");
            boolean filterCars = scanner.nextLine().equalsIgnoreCase("y");

            // Need to add display cars and filter methods
            System.out.println("Display all cars: " + displayAllCars);
            System.out.println("Filter cars: " + filterCars);
        }


    }

    public static boolean login() {
        // Get user input for username and password
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

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
}

