import java.util.ArrayList;
import java.util.List;

public class Plane {

    private List<Ticket> tickets = new ArrayList<>();
    private int steerageCount;
    private int coachCount;

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

}
