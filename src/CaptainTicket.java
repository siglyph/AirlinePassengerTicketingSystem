import java.util.ArrayList;
import java.util.List;

// Top-tier class ($70) - 1 dedicated seat where the passenger flies the plane
// (no actual pilot is hired). Identified by the seat ID "C". One option:
// YouTube access for in-flight tutorials on flying a 737 MAX 9.
public class CaptainTicket extends Ticket {

    // The available-options menu for Captain class.
    private List<Option> captainTicketOptions = new ArrayList<>();

    // Build the menu when a CaptainTicket is created.
    public CaptainTicket() {
        Option youtubeFlyingTutorial = new Option("Youtube Access For Boeing 737 MAX 9 Flying Tutorials", 100);
        captainTicketOptions.add(youtubeFlyingTutorial);
    }

    // Captain class base fare per the spec.
    @Override
    public double getBaseTicketPrice() {
        return 70;
    }

    @Override
    public List<Option> getTicketOptions() {
        return captainTicketOptions;
    }

    // Captain inherits needsSeatSelection() = true. SeatMap.isValidForTicket()
    // restricts Captain to the single "C" seat.

    @Override
    public String toString(){
        return "Captain Ticket";
    }
}