import java.util.ArrayList;
import java.util.List;

public class CaptainTicket extends Ticket {
    private List<Option> captainTicketOptions = new ArrayList<>();

    public CaptainTicket() {
        Option youtubeFlyingTutorial =  new Option("Youtube Access For Boeing 737 MAX 9 Flying Tutorials", 100);
        captainTicketOptions.add(youtubeFlyingTutorial);
    }


    @Override
    public double getBaseTicketPrice() {
        return 70;
    }

    @Override
    public List<Option> getTicketOptions() {
        return captainTicketOptions;
    }


    @Override
    public String toString(){
        return "Captain Ticket";
    }
}
