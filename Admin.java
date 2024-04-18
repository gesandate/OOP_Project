import java.io.IOException;
import java.io.FileWriter;
//import java.io.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Date;

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
    public Admin (String fName, String lName, int age, String gender, Log log){

        super(fName, lName, age, gender);
        String currTime = new SimpleDateFormat("HH:mm:ss MM/dd/yy").format(new Date());
        this.log = new Log(fName+"_"+lName, "Admin user Created", currTime);
        
    }

/**
 * Adds a car to the car_data csv
 * 
 * @param car the car to be added
 */
public void adminAdd(Car car){

    try {
        FileReader     fileReader     = new FileReader("car_data.csv");
        BufferedReader bReader = new BufferedReader(fileReader);
        String line = bReader.readLine();

        while(line != null) {
            line = bReader.readLine();
        }

        
        bReader.close();
    } catch (IOException err) {
        // TODO: handle exception
        System.out.println("Error Reading File");
    }
}

/** 
 * Removes a car using ID and logs it.
 * 
 * @param ID the car that is getting removed.
 */
public void adminRemoveID(int ID){

    try{

        //intitializes variables for reading and changing the csv
        String line;
        FileReader     fileReader     = new FileReader("car_data.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        line = bufferedReader.readLine();
        //String [] splitLine = line.split(line);
        /*int index;
        
        for (int i = 0; i < splitLine.length; i++){

            if (splitLine[i].equals("ID")){

                index = i;

            }

        }
        */

        //traversing the file looking for the car id to remove
        for (int i = 0; i < ID; i++){

            line = bufferedReader.readLine();
        
        }

        //once there, remove the car

        //have to make a temp file?
        //rewrite everything without that one car/line.
        bufferedReader.close();
    }catch (IOException err){

        System.out.println("Error reading file lowkey");

    }

}
}
