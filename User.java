package Project;
/**
 * This is a user person in the system 
 * The user class extends the person class
 */
public class User extends Person{
    
    private String  firstName;
    private String  lastName;
    private double  budget;
    private int     carsPurchased;
    private boolean minerCarsMember;
    private String  username;
    private String  password;

    /**
     * 
     * @param firstName       first name of the user
     * @param lastName        last name of the user
     * @param budget          user's budget
     * @param carsPurchased   amount of cars user previously purchased
     * @param minerCarsMember is the user a miner cars member
     * @param username        the user's username
     * @param password        the user's password  
     */
    public User(){
        this.firstName       = "NULL";
        this.lastName        = "NULL";
        this.budget          = 0.0;
        this.carsPurchased   = 0;
        this.minerCarsMember = false;
        this.username        = "NULL";
        this.password        = "NULL";
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
     * @return the user's first name as a string
     */
    public String getFirstName(){
        return this.firstName;
    }

    
    /** 
     * Sets the first name for the user
     * 
     * @param lastName the last name to set for the user
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    
    
    /**
     * Gets the user's last name
     * 
     * @return the user's last name String
     */
    public String getLastName(){
        return this.lastName;
    }

    
    /** 
     * Sets the user's budget
     * 
     * @param budget the budget to set for the user
     */
    public void setBudget(int budget){
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
     * Sets the amount of cars purchased by the user
     * 
     * @param carsPurchased the amount of cars previously purchased by the user
     */
    public void setCarsPurchased(int carsPurchased){
        this.carsPurchased = carsPurchased;
    }

    
    /** 
     * Gets the amount of cars previously purchased
     * 
     * @return the amount of cars previously purchased as an int 
     */
    public int getCarsPurchased(){
        return this.carsPurchased;
    }

    
    /** 
     * Sets the mambership status of the user
     * 
     * @param member the membership status of the user
     */
    public void setMembership(boolean member){
        this.minerCarsMember = member;
    }

    
    /** 
     * Gets the membership status of the user
     * 
     * @return the membership status of the user as a boolean
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
