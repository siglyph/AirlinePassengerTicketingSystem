import java.util.ArrayList;
import java.util.List;

public class Plane {

    private List<Ticket> tickets = new ArrayList<>();

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

}
