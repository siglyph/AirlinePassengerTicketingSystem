import java.util.ArrayList;

public abstract class Ticket {

    double baseTicketPrice;
    ArrayList<Option> optionsList = new ArrayList<>();

    public abstract double getBaseTicketPrice();

    public double getFinalTicketPrice(){
        return baseTicketPrice + addOptionsTotal() + addMandatoryFees();
    }

    // Prompt the user for each option
    public abstract String getTicketOptions();
    public abstract void addOption();

    public abstract double addOptionsTotal();

    public double addMandatoryFees(){
        double boardingFee = 500;
        double oxygenFee = 1000;
        double valueAddedTax = 1500;

        return (boardingFee + oxygenFee + valueAddedTax);
    }
}
