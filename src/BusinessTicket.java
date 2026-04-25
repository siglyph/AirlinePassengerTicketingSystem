import java.util.ArrayList;
import java.util.List;

public class BusinessTicket extends Ticket {

    private List<Option> businessTicketOptions = new ArrayList<>();

    public BusinessTicket() {
        Option secondCarryOn = new Option("Second carry-on bag", 100);

        businessTicketOptions.add(secondCarryOn);
    }

    @Override
    public double getBaseTicketPrice() {
        return 20;
    }

    @Override
    public List<Option> getTicketOptions() {
        return businessTicketOptions;
    }
}
