import java.util.ArrayList;
import java.util.List;

// Higher-tier seated class ($30). Gets non-middle seats (A, C, D, F columns)
// by default. The single option lets the passenger pay extra to pick a middle
// seat instead - SeatMap looks for this option when validating Premium seat picks.
public class PremiumTicket extends Ticket{

    // Premium's available-options menu - just the middle-seat option.
    private List<Option> premiumTicketOptions = new ArrayList<Option>();

    // Build the menu when a PremiumTicket is created.
    public PremiumTicket(){
        Option middleSeat = new Option("Middle-seat", 100);
        premiumTicketOptions.add(middleSeat);
    }

    // Premium base fare per the spec.
    @Override
    public double getBaseTicketPrice() {
        return 30;
    }

    @Override
    public List<Option> getTicketOptions() {
        return premiumTicketOptions;
    }

    // Premium inherits needsSeatSelection() = true. Seat validation in SeatMap
    // checks for the middle-seat option to decide whether middle picks are allowed.

    @Override
    public String toString(){
        return "Premium Ticket";
    }
}