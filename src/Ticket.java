import java.util.ArrayList;
import java.util.List;

public abstract class Ticket {

    private double baseTicketPrice;
    List<Option> ticketOptions = new ArrayList<>();

    public abstract double getBaseTicketPrice();

    public double getFinalTicketPrice(){
        return baseTicketPrice + totalOptionsPrice() + totalMandatoryFees();
    }

    public abstract List<Option> getTicketOptions();
    public abstract void addOption(Option option);

    public abstract double totalOptionsPrice();

    public double totalMandatoryFees(){
        double boardingFee = 500;
        double oxygenFee = 1000;
        double valueAddedTax = 1500;

        return (boardingFee + oxygenFee + valueAddedTax);
    }
}
