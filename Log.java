import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private String name_of_user;
    private String action;
    private String timeStamp;

    public Log() {
        this.name_of_user = "";
        this.action = "";
        this.timeStamp = "";
    }

    public void logAction(User user, String action) {
        String filename = user.getUsername() + "_actions.log";
        String timestamp = new SimpleDateFormat("HH:mm:ss MM/dd/yy").format(new Date());
        String logMessage = timestamp + " - " + user.getUsername() + " " + action + "\n";
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename, true));
            out.append(logMessage);
            out.close();
            System.out.println("Action logged successfully.");
        } catch (IOException e) {
            System.err.println("Error logging action: " + e.getMessage());
        }
    }
}
