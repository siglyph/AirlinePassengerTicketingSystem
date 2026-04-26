import java.util.ArrayList;
import java.util.List;

// Second-cheapest ticket class ($10). Passengers also stand, but at least
// they get a handrail at no extra charge. Has one option: bring a carry-on bag.
public class CoachTicket extends Ticket {

    // Coach offers one option: a carry-on bag (which is normally allowed for
    // free, but Poltergeist makes you pay for it).
    private List<Option> coachTicketOptions = new ArrayList<>();

    // Build the available-options list when a CoachTicket is created.
    public CoachTicket() {
        Option carryOnBag = new Option("Carry-on bag", 100);
        coachTicketOptions.add(carryOnBag);
    }

    // Coach base fare per the spec.
    @Override
    public double getBaseTicketPrice() {
        return 10;
    }

    @Override
    public List<Option> getTicketOptions() {
        return coachTicketOptions;
    }

    // Coach class has no seats either - passengers stand and use a handrail.
    @Override
    public boolean needsSeatSelection() {
        return false;
    }

    @Override
    public String toString(){
        return "Coach Ticket";
    }
}