import java.util.ArrayList;
import java.util.List;

// Cheapest ticket class ($5). Passengers stand in a designated seat-less area
// at the back of the plane on top of their carry-on bag. Has one option:
// "Hold carry-on bag" instead of standing on it.
public class SteerageTicket extends Ticket {

    // The available-options menu for Steerage. Populated in the constructor.
    private List<Option> steerageTicketOptions = new ArrayList<>();

    // Constructor: build the available-options list. Each Steerage instance
    // gets its own copy of the option, even though the contents are the same.
    public SteerageTicket() {
        Option holdCarryOn = new Option("Hold carry-on bag", 100);
        steerageTicketOptions.add(holdCarryOn);
    }

    // Steerage base fare per the spec.
    @Override
    public double getBaseTicketPrice() {
        return 5;
    }

    // Hand back the menu so the UI can prompt for each option.
    @Override
    public List<Option> getTicketOptions() {
        return steerageTicketOptions;
    }

    // Steerage class has no seats - passengers stand in a designated area.
    // Overriding this tells the UI to skip the seat-selection step entirely.
    @Override
    public boolean needsSeatSelection() {
        return false;
    }

    // Used when printing the ticket (e.g., "Steerage Ticket has been added to your cart").
    @Override
    public String toString(){
        return "Steerage Ticket";
    }

}