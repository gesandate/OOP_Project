import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Log class that records user's actions with timestamps  
 */
public class Log {
    private String name_of_user;
    private String action;
    private String timeStamp;

    /**
     * Instantiates a new Log.
     */
    public Log(){
        this.name_of_user = null;
        this.action = null;
        this.timeStamp = null;
    }

    /**
     * Constructor for the log class
     * 
     * @param name_of_user  the name of the user being logged
     * @param action        the actions the user is performing  
     * @param timeStamp     the time the action is performed
     */
    public Log(String name_of_user, String action, String timeStamp) {
        this.name_of_user = name_of_user;
        this.action = action;
        this.timeStamp = timeStamp;
    }

    /**
     * Method that logs the action performed
     * 
     * @param user      the user performing the action
     * @param action    the action the user is performing 
     * @param filename  the file where the action is logged onto
     *  -_-
     */
    public void logAction(User user, String action, String filename) {
        String timestamp = new SimpleDateFormat("HH:mm:ss MM/dd/yy").format(new Date());
        String logMessage = timestamp + " - " + user.getUsername() + " " + action + "\n";
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename, true));
            out.append(logMessage);
            out.close();
            //System.out.println("Action logged successfully.");
        } catch (IOException e) {
            System.err.println("Error logging action: " + e.getMessage());
        }
    }
}
