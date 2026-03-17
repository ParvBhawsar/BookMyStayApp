/**
 * ==========================================================
 * MAIN CLASS - BookMyStayApp
 * ==========================================================
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Description:
 * This class demonstrates room initialization
 * using object-oriented concepts like abstraction
 * and inheritance.
 *
 * Room types are created and their availability
 * is stored using simple variables.
 *
 * @author Parv
 * @version 2.1
 */

abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

public class BookMyStayApp {

    /**
     * Application entry point for UC2
     */
    public static void main(String[] args) {

        System.out.println("========== Hotel Room Initialization ==========\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doub = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Single Room
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        // Display Double Room
        System.out.println("Double Room:");
        doub.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        // Display Suite Room
        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}