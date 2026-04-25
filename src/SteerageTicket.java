import java.util.ArrayList;
import java.util.List;

public class SteerageTicket extends Ticket {
    private final int MAX_CAPACITY = 493;

    private List<Option> ticketOptions = new ArrayList<>();

    public SteerageTicket() {
        Option holdCarryOn = new Option("Hold carry-on bag", 100);
        ticketOptions.add(holdCarryOn);
    }

    @Override
    public double getBaseTicketPrice() {
        return 5;
    }

    @Override
    public List<Option> getTicketOptions() {
        return ticketOptions;
    }

}
