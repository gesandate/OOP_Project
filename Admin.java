/**
 * This is an admin person in the system
 * The admin class extends the person class adding the admin methods
 */
public class Admin extends Person{

    private Log log;

    /**
     * 
     * @param fName  first name of the admin
     * @param lName  last name of the admin
     * @param age    age of the admin
     * @param gender gender of the admin
     */
    public Admin (String fName, String lName, int age, String gender){

        super(fName, lName, age, gender);
        this.log = new Log();
        
    }


/** 
 * Does an admin action and logs it.
 * 
 * @param action the admin action that is done.
 */
public void adminActon(String action){

    log.logAction(this, action);

}
}
