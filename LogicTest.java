import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;


/**
 * The type Logic test.
 */
public class LogicTest {

    /**
     * Test login.
     */
    @Test
    public void testLogin() {
        // Create a test HashMap with sample user data
        HashMap<String, User> usersList = new HashMap<>();
        User user = new User("testUser", "password", 0.0, 0, false, "Test", "123");
        usersList.put("testUser", user);

        // Test login with valid credentials
        assertTrue(Logic.login("testUser", "123", usersList));

        // Test login with invalid password
        assertFalse(Logic.login("testUser", "wrongPassword", usersList));

    }

    /**
     * Test purchase car.
     */
    @Test
    public void testPurchaseCar() {
        // Create a test HashMap with sample car data
        HashMap<Integer, Car> carList = new HashMap<>();
        Car car = new Car("Sedan", "Model", 1, 1, 10000.0, "Fuel", 4, "Auto", 0, "Color", "Condition", "VIN", false);
        carList.put(1, car);

        // Create a test HashMap with sample user data
        HashMap<String, User> usersList = new HashMap<>();
        User user = new User("testUser", "password", 20000.0, 0, false, "Test", "123");
        usersList.put("testUser", user);

        // Test purchasing car
        assertTrue(Logic.purchase_car(1, user, usersList, carList, new HashMap<>()));

        // Test purchasing a car with insufficient budget
        assertFalse(Logic.purchase_car(1, user, usersList, carList, new HashMap<>()));

        // Test purchasing a non-existent car
        assertFalse(Logic.purchase_car(2, user, usersList, carList, new HashMap<>()));
    }

    /**
     * Test add ticket when user exists.
     */
    @Test
    public void testAddTicketWhenUserExists() {
        HashMap<String, int[]> tickets = new HashMap<>();
        String username = "user1";
        int input_ID = 123;

        // Initial ticket setup
        int[] initialTickets = {456, 789};
        tickets.put(username, initialTickets);

        // Call the method
        Logic.add_Ticket(username, input_ID, tickets);

        // Check if the ticket is added
        int[] updatedTickets = tickets.get(username);
        assertArrayEquals(new int[]{456, 789, 123}, updatedTickets);
    }



}

