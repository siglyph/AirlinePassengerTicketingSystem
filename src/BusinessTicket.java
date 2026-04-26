import java.util.ArrayList;
import java.util.List;

// Mid-tier seated class ($20). Locked to the middle seats (columns B and E)
// of the 42-seat grid. Has one option: a second carry-on bag (the first is free).
public class BusinessTicket extends Ticket {

    // The available-options menu for Business class.
    private List<Option> businessTicketOptions = new ArrayList<>();

    // Build the menu when a BusinessTicket is created.
    public BusinessTicket() {
        Option secondCarryOn = new Option("Second carry-on bag", 100);
        businessTicketOptions.add(secondCarryOn);
    }

    // Business base fare per the spec.
    @Override
    public double getBaseTicketPrice() {
        return 20;
    }

    @Override
    public List<Option> getTicketOptions() {
        return businessTicketOptions;
    }

    // Business inherits needsSeatSelection() = true from Ticket (no override needed).
    // SeatMap.isValidForTicket() restricts Business to middle seats only.

    @Override
    public String toString(){
        return "Business Ticket";
    }
}