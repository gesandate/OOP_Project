import java.util.Scanner;

/**
 * 
 * This class represents a user that inherits from the Person class
 */
public class User extends Person{

    //private String  firstName;
    //private String  lastName;
    private double  budget;
    private int     carsPurchased;
    private boolean minerCarsMember;
    private String  username;
    private String  password;


    public User(){
        super("NULL", "NULL", 0, "NULL");
        this.budget          = 0.0;
        this.carsPurchased   = 0;
        this.minerCarsMember = false;
        this.username        = "NULL";
        this.password        = "NULL";
    }

    /**
     * 
     * @param FName         the user's first name
     * @param LName          the user's last name
     * @param budget            the user's budget
     * @param carsPurchased     the amount of cars the user previously purchased
     * @param minerCarsMember   the membership status of the user
     * @param username          the user's username
     * @param password          the user's password
     */
    public User(String FName, String LName, Double budget, Integer carsPurchased, Boolean minerCarsMember, String username, String password){
        super(FName, LName);
        //this.firstName       = firstName;
        //this.lastName        = lastName;
        this.budget          = budget;
        this.carsPurchased   = carsPurchased;
        this.minerCarsMember = minerCarsMember;
        this.username        = username;
        this.password        = password;
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
        user.setFName(firstName);

        // Prompt for and set last name
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        user.setLName(lastName);

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
     * Sets the first name for the user.
     * 
     * @param FName the first name to set for the user
     */
    //public void setFName(String FName){
    //    this.FName = FName;
    //}

    /**
     * Gets the user's first name
     * 
     * @return the user's first name as a String
     */
    //public String getFName(){
    //    return this.FName;
    //}

    /**
     * Sets the last name for the user
     * 
     * @param lastName the last name to set for the user.
     */


    /**
     * Sets the budget for the user
     * 
     * @param budget the new budget to be set for the user.
     */
    public void setBudget(Double budget){
        this.budget = budget;
    }

    /**
     * Gets the user's budget
     * 
     * @return the user's budget as a Double
     */
    public Double getBudget(){
        return this.budget;
    }

    /**
     * Sets the amount of cars previously purchased by the user
     * 
     * @param carsPurchased the amount of cars the user previously purchased.
     */
    public void setCarsPurchased(int carsPurchased){
        this.carsPurchased = carsPurchased;
    }

    /**
     * Gets the amount of cars previously purchased
     * 
     * @return the amount of cars the user previously purchased as an int
     */
    public int getCarsPurchased(){
        return this.carsPurchased;
    }

    /**
     * Sets the membership status for the user
     * 
     * @param member the membership status
     */
    public void setMembership(boolean member){
        this.minerCarsMember = member;
    }

    /**
     * Gets the user's membership status
     * 
     * @return the user's membership status as a boolean
     */
    public boolean getMembership(){
        return this.minerCarsMember;
    }

    /**
     * Sets the user's username
     * 
     * @param user the user's username
     */
    public void setUsername(String user){
        this.username = user;
    }

    /**
     * Gets the user's username
     * 
     * @return the user's username as a String
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Sets the user's password
     * 
     * @param password the user's password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Gets the user's password
     * 
     * @return the user's password as a String
     */
    public String getPassword(){
        return this.password;
    }
}
