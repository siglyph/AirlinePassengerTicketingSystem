import java.util.ArrayList;
import java.util.List;

public class SteerageTicket extends Ticket {
    private static final int MAX_CAPACITY = 493;

    private List<Option> steerageTicketOptions = new ArrayList<>();

    public SteerageTicket() {
        Option holdCarryOn = new Option("Hold carry-on bag", 100);
        steerageTicketOptions.add(holdCarryOn);
    }

    @Override
    public double getBaseTicketPrice() {
        return 5;
    }

    @Override
    public List<Option> getTicketOptions() {
        return steerageTicketOptions;
    }

}
