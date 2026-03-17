import java.util.*;

/**
 * ==========================================================
 * MAIN CLASS - BookMyStayApp
 * ==========================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * This class demonstrates how optional services can be
 * attached to a confirmed reservation without affecting
 * booking or inventory logic.
 *
 * @author Parv
 * @version 7.0
 */

// ---------- ADD-ON SERVICE ----------
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// ---------- SERVICE MANAGER ----------
class AddOnServiceManager {

    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        List<AddOnService> services = servicesByReservation.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// ---------- MAIN ----------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("========== Add-On Service Selection ==========\n");

        // Example reservation ID (from UC6)
        String reservationId = "Single-1";

        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservationId, new AddOnService("Breakfast", 500));
        manager.addService(reservationId, new AddOnService("Spa", 1000));

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}