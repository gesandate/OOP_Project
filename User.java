/**
 * 
 * This class represents a user that inherits from the Person class
 */
public class User extends Person{

    private String  firstName;
    private String  lastName;
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
     * @param firstName         the user's first name
     * @param lastName          the user's last name
     * @param budget            the user's budget
     * @param carsPurchased     the amount of cars the user previously purchased
     * @param minerCarsMember   the membership status of the user
     * @param username          the user's username
     * @param password          the user's password
     */
    public User(String firstName, String lastName, Double budget, Integer carsPurchased, Boolean minerCarsMember, String username, String password){
        //super(firstName, lastName);
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.budget          = budget;
        this.carsPurchased   = carsPurchased;
        this.minerCarsMember = minerCarsMember;
        this.username        = username;
        this.password        = password;
    }

    /**
     * Sets the first name for the user.
     * 
     * @param firstName the first name to set for the user
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Gets the user's first name
     * 
     * @return the user's first name as a String
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Sets the last name for the user
     * 
     * @param lastName the last name to set for the user.
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Gets the user's last name
     * 
     * @return the user's last name as a String
     */
    public String getLastName(){
        return this.lastName;
    }

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
