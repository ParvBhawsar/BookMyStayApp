import java.util.*;

/**
 * ==========================================================
 * MAIN CLASS - BookMyStayApp
 * ==========================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * This class demonstrates how confirmed bookings are stored
 * and reported using a history system.
 *
 * @author Parv
 * @version 8.0
 */

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

// ---------- BOOKING HISTORY ----------
class BookingHistory {

    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

// ---------- REPORT SERVICE ----------
class BookingReportService {

    public void generateReport(BookingHistory history) {

        System.out.println("========== Booking History Report ==========\n");

        for (Reservation r : history.getConfirmedReservations()) {
            System.out.println("Guest: " + r.getGuestName()
                    + ", Room Type: " + r.getRoomType());
        }
    }
}

// ---------- MAIN ----------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("========== Booking History and Reporting ==========\n");

        BookingHistory history = new BookingHistory();

        // Simulating confirmed bookings (from UC6)
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        BookingReportService reportService = new BookingReportService();

        // Generate report
        reportService.generateReport(history);
    }
}