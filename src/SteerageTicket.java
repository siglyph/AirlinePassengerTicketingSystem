import java.util.ArrayList;
import java.util.List;

public class SteerageTicket extends Ticket {

    List<Option> ticketOptions = new ArrayList<>();


    @Override
    public double getBaseTicketPrice() {
        return 5;
    }

    @Override
    public List<Option> getTicketOptions() {
        Option holdCarryOn = new Option("Hold carry-on bag", 100);

        ticketOptions.add(holdCarryOn);

        return ticketOptions;
    }

    @Override
    public double totalOptionsPrice() {
        return 0;
    }

}
