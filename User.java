package Project;

public class User{
    
    private String  firstName;
    private String  lastName;
    private double  budget;
    private int     carsPurchased;
    private boolean minerCarsMember;
    private String  username;
    private String  password;

    public User(){
        this.firstName       = "NULL";
        this.lastName        = "NULL";
        this.budget          = 0.0;
        this.carsPurchased   = 0;
        this.minerCarsMember = false;
        this.username        = "NULL";
        this.password        = "NULL";
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getLastName(){
        return this.lastName;
    }

    public void setBudget(int budget){
        this.budget = budget;
    }

    public Double getBudget(){
        return this.budget;
    }

    public void setCarsPurchased(int carsPurchased){
        this.carsPurchased = carsPurchased;
    }

    public int getCarsPurchased(){
        return this.carsPurchased;
    }

    public void setMembership(boolean member){
        this.minerCarsMember = member;
    }

    public boolean getMembership(){
        return this.minerCarsMember;
    }
    
    public void setUsername(String user){
        this.username = user;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }
}
