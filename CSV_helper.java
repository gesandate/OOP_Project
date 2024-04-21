import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSV_helper {

    public static HashMap<String, User> user_map_from_csv(String csv_File_Name, HashMap<String, User> usersMap) {
        try {
            // Create a FileReader object to read the CSV file
            FileReader fileReader = new FileReader(csv_File_Name);
            BufferedReader bufferedReader = new BufferedReader(fileReader);


            //get the header line to match to the correct variable name
            String labels_line = bufferedReader.readLine();
            String[] labels = labels_line.split(",");

            String line;
            // Read the CSV file line by line
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into fields using the comma as a delimiter
                String[] fields = line.split(",");

                // Extract information from the fields
                String firstName = "";
                String lastName = "";
                double budget = 0;
                int carsPurchased = 0;
                boolean minerCarsMember = false;
                String username = "";
                String password = "";

                for (int i = 0; i < labels.length; i++) {
                    switch (labels[i].trim()) {
                        case "First Name":
                            firstName = fields[i];
                            break;
                        case "Last Name":
                            lastName = fields[i];
                            break;
                        case "Money Available":
                            budget = Double.parseDouble(fields[i]);
                            break;
                        case "Cars Purchased":
                            carsPurchased = Integer.parseInt(fields[i]);
                            break;
                        case "MinerCars Membership":
                            minerCarsMember = Boolean.parseBoolean(fields[i]);
                            break;
                        case "Username":
                            username = fields[i];
                            break;
                        case "Password":
                            password = fields[i];
                            break;
                        default:
                            // Handle unknown columns or ignore them
                            break;
                    }
                }

                // Create a new User object using the extracted information
                User newUser = new User(firstName, lastName, budget, carsPurchased, minerCarsMember, username, password);

                //System.out.println(newUser.getFName()+newUser.getLName());

                // Add user to map
                usersMap.put(newUser.getUsername(), newUser);
            }
            // Close the BufferedReader
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersMap;
    }

    public static HashMap<Integer, Car> cars_map_from_csv(String car_file, HashMap<Integer,Car> car_list){
        try{
            // Create BufferedReader object
            FileReader fileReader = new FileReader(car_file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);


            String lable_line = bufferedReader.readLine();
            String[] labels = lable_line.split(",");
            // Read each lin of the file
            String line;

            //if parts[i].eqals( "id".equalsIgnoreCase()){
            //    int id_place =  i;
            //}
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");

                // Extract car information from the line
                int id = 0;
                String carType = "";
                String model = "";
                String condition = "";
                String color = "";
                int capacity = 0;
                int mileage = 0;
                String fuelType = "";
                String transmission = "";
                String vin = "";
                double price = 0.0;
                int carsAvailable = 0;
                boolean hasTurbo = false;

                for (int i = 0; i < parts.length; i++) {
                    switch (labels[i].trim()) {
                        case "ID":
                            id = Integer.parseInt(parts[i]);
                            break;
                        case "Car Type":
                            carType = parts[i];
                            break;
                        case "Model":
                            model = (parts[i]);
                            break;
                        case "Condition":
                            condition = parts[i];
                            break;
                        case "Color":
                            color = parts[i];
                            break;
                        case "Capacity":
                            capacity = Integer.parseInt(parts[i]);
                            break;
                        //case "Password":
                        //    mileage = Integer.parseInt(parts[i]);
                        //    break;
                        case "Fuel Type":
                            fuelType = parts[i];
                            break;
                        case "VIN":
                            vin = parts[i];
                            break;
                        case "Price":
                            price = Double.parseDouble(parts[i]);
                            break;
                        case "Cars Available":
                            carsAvailable = Integer.parseInt(parts[i]);
                            break;
                        case "hasTurbo":
                            //System.out.println(parts[i]);
                            hasTurbo = Boolean.parseBoolean(parts[i]);
                        default:
                            // Handle unknown columns or ignore them
                            //System.out.println("user_map_from_csv method read a lable that is unknown");
                            break;
                    }
                }

                // Create Car object
                Car car = new Car(carType, model, id, carsAvailable, price, fuelType, capacity, transmission, mileage, color, condition,vin, hasTurbo);

                // Add car to carList
                car_list.put(id, car);
                //System.out.println("new car added to car_list "+id +" "+car.getID());
            }
            // Close file
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return car_list;
    }

    //public static void
    public static void purchase_remove(String car_csv_out,String car_file, HashMap<String, int[]> tickets){
        //**CHANGE WHERE OUTPUT FILE IS MADE**//
        try (BufferedReader reader = new BufferedReader(new FileReader(car_file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(car_csv_out))) {

            String first_line = reader.readLine();
            String[] labels = first_line.split(",");

            String line;
            List<String[]> updatedLines = new ArrayList<>();

            //add loop to go through all users in ticket
            //then reomve cars availblity for each id in the id array
            //move this to the CSV helper
            int id_index = 0;
            for (int i = 0; i < labels.length; i++) {
                if (labels[i].equals("ID")){
                    id_index =i;
                }
            }
            int carAv_index = 0;
            for (int i = 0; i < labels.length; i++) {
                if (labels[i].equals("Car Available")){
                    id_index =i;
                }
            }
            for (Map.Entry<String, int[]> entry : tickets.entrySet()) {
                String user = entry.getKey();
                int[] ids = entry.getValue();
                for (int id : ids) {
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        int id_file = Integer.parseInt(parts[id_index]);
                        if (id_file == id) { // Found the id
                            int carsAvailable = Integer.parseInt(parts[carAv_index]);
                            carsAvailable--;
                            parts[carAv_index] = String.valueOf(carsAvailable);
                        }
                        updatedLines.add(parts);
                    }
                    for (String[] parts : updatedLines) {
                        writer.write(String.join(",", parts));
                        writer.newLine();
                    }
                }
            }
            System.out.println("New CSV file created: " + car_csv_out);
        } catch (IOException e) {
            System.out.println("purchase_remove error");
        }
    }

    public static void create_new_user_data(String user_newCSV, HashMap<String, User> users_list) {
        //**this should be changed for use

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(user_newCSV))) {
            // Write CSV header
            writer.write("ID,First Name,Last Name,Money Available,Cars Purchased,MinerCars Membership,Username,Password");
            writer.newLine();
            int id = 1;
            // iterate for all users
            for (User user : users_list.values()) {
                // format for csv
                String userData = String.format("%d,%s,%s,%.2f,%d,%b,%s,%s",
                        id,user.getFName(), user.getLName(), user.getBudget(),
                        user.getCarsPurchased(), user.getMembership(), user.getUsername(), user.getPassword());
                id = 1+id;
                writer.write(userData);
                writer.newLine();
            }
            System.out.println("New user data file created: " + user_newCSV);
        } catch (IOException e) {
            System.out.println("create_new_user_data error");
        }
    }

}
