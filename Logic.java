import java.util.HashMap;

public class Logic {


    /**
     * Displays cars based on the given condition and budget criteria.
     *
     * @param condition The condition of the car to display. Pass "null" to display all cars.
     * @param budget    The maximum budget for the car. Pass 0 to ignore budget criteria.
     */
    public static void displayCars(String condition, double budget, HashMap<Integer, Car> car_list) {
        // Display header
        System.out.println("ID\tCar Type\tModel\tCondition\tColor\tFuel Type\tCapacity\tTransmission\tMileage\tVIN\tTurbo\tPrice\tStock");
        // Iterate over cars
        for (Car car : car_list.values()) {
            //shows all cars
            if (condition.equalsIgnoreCase("null")){
                System.out.println(car.getID() + "\t" + car.getCarType() + "\t" + car.getModel() + "\t" +
                        car.getCondition() + "\t" + car.getColor() + "\t" + car.getFuelType() + "\t" +
                        car.getCapacity()+ "\t" +car.getTransmission()+ "\t" +car.getMileage()+ "\t" +
                        car.getVIN()+ "\t"+ car.gethasTurbo() +"\t" +car.getPrice() + "\t" +car.getAvailability());
            }
            // Check if the condition and price match the criteria
            if (car.getCondition().equalsIgnoreCase(condition) && car.getPrice() <= budget) { //maybe add a with in range of a couple 100s
                // Display car information
                System.out.println(car.getID() + "\t" + car.getCarType() + "\t" + car.getModel() + "\t" +
                        car.getCondition() + "\t" + car.getColor() + "\t" + car.getFuelType() + "\t" +
                        car.getCapacity()+ "\t" +car.getTransmission()+ "\t" +car.getMileage()+ "\t" +
                        car.getVIN()+ "\t" +car.getPrice());
            }

            if (car.getCondition().equalsIgnoreCase(condition) && budget == 0){ //display cars with condition no budget
                System.out.println(car.getID() + "\t" + car.getCarType() + "\t" + car.getModel() + "\t" +
                        car.getCondition() + "\t" + car.getColor() + "\t" + car.getFuelType() + "\t" +
                        car.getCapacity()+ "\t" +car.getTransmission()+ "\t" +car.getMileage()+ "\t" +
                        car.getVIN()+ "\t" +car.getPrice());
            }
        }
    } //need to add avalibility to display



    /**
     * Checks if a car with the given ID can be purchased by the current user.
     *
     * @param id   The ID of the car to be purchased.
     * @param curr The current user attempting to purchase the car.
     * @return true if the car can be purchased, false otherwise.
     */
    public static boolean purchase_car(int id, User curr, HashMap<String,User> users_list, HashMap<Integer, Car> car_list, HashMap<Integer, Double> revenue_list){
        //System.out.println("***Entered car purchase***");
        for (Car car : car_list.values()) {
            if (car.getID() == id){
                System.out.println("1");
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
     */
    public static void car_return(User curr, int id_remove,HashMap<String,User> users_list, HashMap<Integer, Car> car_list ,HashMap<String,int[]> tickets){
        if(tickets.containsKey(curr.getUsername())){
            int[] ids = tickets.get(curr.getUsername());
            //int[] new_ids;
            int index_remove = -1;
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
                int[] new_ids = new int[ids.length-1];
                System.arraycopy(ids, 0, new_ids, 0, index_remove);
                for( int i=index_remove;i< ids.length;i++){
                    new_ids[i] = ids[i];
                }
                //updated tickets
                tickets.put(curr.getUsername(),new_ids);
                Car car = car_list.get(id_remove);
                int old_a = car.getAvailability();
                car.setAvailability(old_a+1);
                car_list.put(id_remove,car);

                int cars_pur = curr.getCarsPurchased();
                //increase the number of cars purchased by user in users_list
                curr.setCarsPurchased(cars_pur - 1);
                users_list.put(curr.getUsername(), curr);
                //set new budget of user
                double car_price = car.getPrice();
                if(curr.getMembership()){
                    car_price = car_price * .9;//10% 1
                }
                double car_price_w_tax =car_price* 1.0625;
                curr.setBudget(curr.getBudget() - car_price_w_tax);
                users_list.put(curr.getUsername(), curr);
            }
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
