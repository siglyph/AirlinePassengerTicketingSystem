import java.util.ArrayList;
import java.util.List;

public class CoachTicket extends Ticket {
    private final int MAX_CAPACITY = 200;

    private List<Option> coachTicketOptions = new ArrayList<>();

    public CoachTicket() {

        Option carryOnBag = new Option("Carry-on bag", 100);

        coachTicketOptions.add(carryOnBag);
    }

    @Override
    public double getBaseTicketPrice() {
        return 10;
    }

    @Override
    public List<Option> getTicketOptions() {
        return coachTicketOptions;
    }
}
