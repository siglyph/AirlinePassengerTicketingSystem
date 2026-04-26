import java.util.ArrayList;
import java.util.List;

// Premium "First" class ($50) - 1 dedicated seat at the front of the plane
// next to the captain, identified by the seat ID "F". Comes with flight
// instruments to watch. One option: use the yoke and rudder pedals.
public class FirstTicket extends Ticket {

    // The available-options menu for First class.
    private List<Option> firstTicketOptions = new ArrayList<>();

    // Build the menu when a FirstTicket is created.
    public FirstTicket() {
        Option yokeAndRudderPedals = new Option("Yoke and Rudder Pedals", 100);
        firstTicketOptions.add(yokeAndRudderPedals);
    }

    // First class base fare per the spec.
    @Override
    public double getBaseTicketPrice() {
        return 50;
    }

    @Override
    public List<Option> getTicketOptions() {
        return firstTicketOptions;
    }

    // First inherits needsSeatSelection() = true. SeatMap.isValidForTicket()
    // restricts First to the single "F" seat.

    @Override
    public String toString(){
        return "First Ticket";
    }
}