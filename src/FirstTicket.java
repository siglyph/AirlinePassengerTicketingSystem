import java.util.ArrayList;
import java.util.List;

public class FirstTicket extends Ticket {

    private List<Option> firstTicketOptions = new ArrayList<>();

    public FirstTicket() {
        Option yokeAndRudderPedals = new Option("Yoke and Rudder Pedals", 100);

        firstTicketOptions.add(yokeAndRudderPedals);
    }

    @Override
    public double getBaseTicketPrice() {
        return 50;
    }

    @Override
    public List<Option> getTicketOptions() {
        return firstTicketOptions;
    }

    @Override
    public String toString(){
        return "First Ticket";
    }
}
