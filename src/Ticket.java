import java.util.ArrayList;
import java.util.List;

public abstract class Ticket {



    public abstract double getBaseTicketPrice();

    public double getFinalTicketPrice(){
        return getBaseTicketPrice() + totalOptionsPrice() + totalMandatoryFees();
    }

    public abstract List<Option> getTicketOptions();


    public void addOption(Option option){
        ticketOptions.add(option);
    }
    public double totalOptionsPrice(){
        return ticketOptions.size() * 100;
    }

    public double totalMandatoryFees(){
        double boardingFee = 500;
        double oxygenFee = 1000;
        double valueAddedTax = 1500;

        return (boardingFee + oxygenFee + valueAddedTax);
    }
}
