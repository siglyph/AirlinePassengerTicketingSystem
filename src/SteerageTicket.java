import java.util.List;

public class SteerageTicket extends Ticket {


    Option holdCarryOn = new Option("Hold carry-on bag", 100);
    addOption(holdCarryOn);

    @Override
    public double getBaseTicketPrice() {
        return 5;
    }

    @Override
    public List<Option> getTicketOptions() {
        return ticketOptions;
    }

    @Override
    public void addOption(Option option) {
        ticketOptions.add(option);
    }

    @Override
    public double totalOptionsPrice() {
        return 0;
    }

}
