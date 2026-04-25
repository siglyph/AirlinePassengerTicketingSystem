import java.util.ArrayList;
import java.util.List;

public class PremiumTicket extends Ticket{

    private List<Option> premiumTicketOptions = new ArrayList<Option>();

    public PremiumTicket(){
        Option middleSeat = new Option("Middle-seat", 100);

        premiumTicketOptions.add(middleSeat);
    }

    @Override
    public double getBaseTicketPrice() {
        return 30;
    }

    @Override
    public List<Option> getTicketOptions() {
        return premiumTicketOptions;
    }
}
