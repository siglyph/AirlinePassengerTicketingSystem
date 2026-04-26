import java.util.ArrayList;
import java.util.List;

// Represents the single plane on which all tickets are sold. Owns:
//   - the master list of every purchased ticket
//   - the SeatMap (which seats are taken / class-specific seat rules / rendering)
//   - capacity rules for the seat-less classes (Steerage and Coach)
//
// Steerage and Coach counts are derived from the tickets list rather than stored
// as separate counter fields, so there is exactly one source of truth.
public class Plane {

    // The master list of every ticket sold on this flight. Steerage and Coach
    // counts are derived from this list (see getSteerageCount / getCoachCount).
    private List<Ticket> tickets = new ArrayList<>();

    // The seat map tracks occupancy of the 42-seat grid plus the First and
    // Captain single seats. Shared by reference - the UI gets it via getSeatMap().
    private SeatMap seatMap = new SeatMap();

    // Capacity limits for the seat-less classes. These are constants so they
    // appear in only one place. (The spec restricts static methods, not static
    // final fields - constants are the conventional Java idiom.)
    private static final int STEERAGE_CAPACITY = 493;
    private static final int COACH_CAPACITY = 200;

    // Add a purchased ticket to the plane. If the ticket has an assigned seat,
    // that seat is also marked as taken on the seat map - keeping the two in sync.
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        if (ticket.getAssignedSeat() != null) {
            seatMap.markTaken(ticket.getAssignedSeat());
        }
    }

    // Exposes the seat map so the UI can render it and validate seat picks.
    public SeatMap getSeatMap() {
        return seatMap;
    }

    // Counts Steerage tickets by walking the master list. Avoids keeping a
    // separate counter that could drift out of sync with the actual list.
    public int getSteerageCount() {
        int count = 0;
        for (Ticket t : tickets) {
            if (t instanceof SteerageTicket) count++;
        }
        return count;
    }

    // Same idea for Coach tickets.
    public int getCoachCount() {
        int count = 0;
        for (Ticket t : tickets) {
            if (t instanceof CoachTicket) count++;
        }
        return count;
    }

    // Capacity getters - exposed so the UI can show "X / 493" style indicators.
    public int getSteerageCapacity() {
        return STEERAGE_CAPACITY;
    }

    public int getCoachCapacity() {
        return COACH_CAPACITY;
    }

    // Capacity gates: returns true if there is room for one more ticket of
    // that class. Called by the UI before letting the user pick options/seats.
    public boolean canAddSteerage() {
        return getSteerageCount() < STEERAGE_CAPACITY;
    }

    public boolean canAddCoach() {
        return getCoachCount() < COACH_CAPACITY;
    }

    // First and Captain each have a single seat - they are full when their
    // single seat is taken on the seat map.
    public boolean canAddFirst() {
        return !seatMap.isTaken("F");
    }

    public boolean canAddCaptain() {
        return !seatMap.isTaken("C");
    }

}