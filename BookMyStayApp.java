import java.util.*;

/**
 * ==========================================================
 * MAIN CLASS - BookMyStayApp
 * ==========================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Description:
 * Demonstrates thread-safe booking using synchronization.
 *
 * @author Parv
 * @version 11.0
 */

// ---------- INVENTORY ----------
class RoomInventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();

    public RoomInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

// ---------- RESERVATION ----------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// ---------- QUEUE ----------
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

// ---------- ALLOCATION ----------
class RoomAllocationService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Integer> counters = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();
        int available = inventory.getRoomAvailability().get(type);

        if (available > 0) {

            int count = counters.getOrDefault(type, 0) + 1;
            counters.put(type, count);

            String roomId = type + "-" + count;

            allocatedRoomIds.add(roomId);
            inventory.updateAvailability(type, available - 1);

            System.out.println("Booking confirmed for Guest: "
                    + r.getGuestName()
                    + ", Room ID: " + roomId);
        }
    }
}

// ---------- UC11 NEW CLASS ----------
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            // Critical section 1: Queue access
            synchronized (bookingQueue) {
                if (!bookingQueue.hasPendingRequests()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            // Critical section 2: Inventory + Allocation
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

// ---------- MAIN ----------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("========== Concurrent Booking Simulation ==========\n");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Add requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        // Threads
        Thread t1 = new Thread(new ConcurrentBookingProcessor(
                bookingQueue, inventory, allocationService));

        Thread t2 = new Thread(new ConcurrentBookingProcessor(
                bookingQueue, inventory, allocationService));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // Final inventory
        System.out.println("\nRemaining Inventory:");
        for (String type : inventory.getRoomAvailability().keySet()) {
            System.out.println(type + ": " + inventory.getRoomAvailability().get(type));
        }
    }
}