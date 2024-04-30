import java.util.Arrays;
import java.util.HashMap;
import java.lang.Math;

/**
 * The Logic for so methods.
 */
public class Logic {

    private static final double tax = 1.0625;
    private  static final double discount = .9;


    /**
     * Attempts to log in a user with the given username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return true if the login attempt is successful, false otherwise.
     */
    public static boolean login(String username, String password, HashMap<String,User> users_list) {
        // Check if the entered username exist and if the password matches
        if (users_list.containsKey(username)) {
            User currentUser = users_list.get(username);
            if (currentUser.getPassword().equals(password)) {
                System.out.println("Welcome "+username);
                return true;
            } else {
                System.out.println("Invalid password. Try again.");
            }
        } else {
            System.out.println("User not found. Contact help.");
        }
        return false;
    }


    /**
     * Checks if a car with the given ID can be purchased by the current user.
     *
     * @param id           The ID of the car to be purchased.
     * @param curr         The current user attempting to purchase the car.
     * @param users_list   the users list
     * @param car_list     the car list
     * @param revenue_list the revenue list
     * @return true if the car can be purchased, false otherwise.
     */
    public static boolean purchase_car(int id, User curr, HashMap<String,User> users_list, HashMap<Integer, Car> car_list, HashMap<Integer, Double> revenue_list){
        //System.out.println("***Entered car purchase***");
        for (Car car : car_list.values()) {
            if (car.getID() == id){

                //new price depending on tax and membership
                double car_price = car.getPrice();
                if(curr.getMembership()){
                    car_price = car_price * .9;//10% 1
                }
                double car_price_w_tax =car_price* 1.0625;
                if (curr.getBudget() >= car_price_w_tax){

                    if (car.getAvailability()>=1){

                        int old_a = car.getAvailability();
                        car.setAvailability(old_a-1);
                        car_list.put(id,car);

                        int cars_pur = curr.getCarsPurchased();
                        //increase the number of cars purchased by user in users_list
                        curr.setCarsPurchased(cars_pur + 1);
                        users_list.put(curr.getUsername(), curr);
                        //set new budget of user
                        curr.setBudget(curr.getBudget() - car_price_w_tax);
                        double rev = car_price_w_tax;
                        if (revenue_list.containsKey(id)) {
                            rev = car_price_w_tax + revenue_list.get(id);
                            revenue_list.put(id, rev);
                        }else{
                            revenue_list.put(id, rev);
                        }
                        users_list.put(curr.getUsername(), curr);
                        System.out.println("Car "+car.getID()+ " purchased.");
                        return true;
                    }else{
                        System.out.println("Car selected is not in stock");
                    }
                }else{
                    System.out.println("Car selected is outside of budget");
                }
            }
        }
        return false;
    }


    /**
     * Adds a ticket (ID) to the specified user's entry in the tickets map.
     *
     * @param username The username of the user to whom the ticket will be added.
     * @param input_ID The ID of the ticket to be added.
     * @param tickets  the tickets
     * @return the hash map
     */
    public static HashMap<String, int[]> add_Ticket(String username, int input_ID, HashMap<String,int[]> tickets) {
        if (tickets.containsKey(username)) {
            //  append id to array
            int[] old_IDs = tickets.get(username);
            int[] new_IDs = new int[old_IDs.length + 1];
            System.arraycopy(old_IDs, 0, new_IDs, 0, old_IDs.length);
            new_IDs[old_IDs.length] = input_ID;
            tickets.put(username, new_IDs);
        } else {
            // not in Tickets so create new
            tickets.put(username, new int[]{input_ID});
        }
        return tickets;
    }

    /**
     * Handles the return of a car by a deignated user.
     *
     * @param curr       The current user returning the car.
     * @param id_remove  The ID of the car that is being returned.
     * @param users_list the users list
     * @param car_list   A HashMap containing car objects, with car IDs as keys.
     * @param tickets    A HashMap containing ticket information, with usernames as keys and arrays of ticket IDs as values.
     */
    public static void car_return(User curr, int id_remove,HashMap<String,User> users_list, HashMap<Integer, Car> car_list ,HashMap<String,int[]> tickets){
        //DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if(tickets.containsKey(curr.getUsername())){
            int[] ids = tickets.get(curr.getUsername());
            System.out.println(Arrays.toString(ids));
            //int[] new_ids;
            int index_remove = -1;
            boolean removed = false;
            for (int i =0; i< ids.length; i++){
                if(ids[i] == id_remove){
                    index_remove = i;
                    break;
                }
            }
            //"Sedan" == (car_list.get(id)).getCarType();
            if( index_remove == -1){
                System.out.println("User did not purchase car with ID");
            }else {
                //System.out.println("Ids len "+ ids.length);
                //System.out.println("Index to remove "+ index_remove);

                if (ids.length==1) {
                    removed = true;
                }
                int[] new_ids;
                //System.out.println("New ids len "+ new_ids.length);
                //System.arraycopy(ids, 0, new_ids, 0, index_remove);
                if(!removed){
                    new_ids = new int[ids.length - 1];
                    int index_new = 0;
                    for( int i=0;i< ids.length;i++){
                        if(i != index_remove) {
                            new_ids[index_new] = ids[i];
                            index_new++;
                        }
                    }
                    //System.out.println("New array "+ Arrays.toString(new_ids));
                    //updated tickets
                    tickets.put(curr.getUsername(), new_ids);
                }else{
                    tickets.remove(curr.getUsername());

                }
            }
            Car car = car_list.get(id_remove);
            int old_a = car.getAvailability();
            car.setAvailability(old_a+1);
            car_list.put(id_remove,car);

            int cars_pur = curr.getCarsPurchased();
            //increase the number of cars purchased by user in users_list
            curr.setCarsPurchased(cars_pur - 1);
            users_list.put(curr.getUsername(), curr);
            System.out.println("Before return budget: "+ curr.getBudget());
            //set new budget of user
            double car_price = car.getPrice();
            if(curr.getMembership()){
                car_price = car_price * discount;//10%
            }
            double car_price_w_tax =car_price* tax;
            curr.setBudget((double) Math.round(curr.getBudget() + car_price_w_tax));
            users_list.put(curr.getUsername(), curr);
            System.out.println(Math.round(curr.getBudget())+" "+curr.getCarsPurchased());
        }else{
            System.out.println("User had no tickets");
        }
    }



    /**
     * Prints the tickets associated with the specified user.
     *
     * @param username The username of the user whose tickets are to be printed.
     * @param car_list A HashMap containing car objects, with car IDs as keys.
     * @param tickets  A HashMap containing ticket information, with usernames as keys and arrays of ticket IDs as values.
     */
    public static void print_Tickets(String username, HashMap<Integer, Car> car_list ,HashMap<String,int[]> tickets) {
        if (tickets.containsKey(username)) {
            System.out.print(username+ " ticket(s): ");
            for (int id : tickets.get(username)) {
                Car car = car_list.get(id);
                if(car != null){ //check if car was added/removed from car_list
                    System.out.println("Car ID: "+ id);
                    System.out.println("Car Type: " + car.getCarType() );
                    System.out.println("Model: " + car.getModel());
                    System.out.println("Color: " + car.getColor());
                }
            }
            System.out.println("\n");
        } else {
            System.out.println("No tickets for user " + username);
        }//lol
    }
}
