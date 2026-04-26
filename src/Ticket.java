import java.util.ArrayList;
import java.util.List;

public abstract class Ticket {
    private List<Option> chosenTicketOptions = new ArrayList<>();

    private double boardingFee = 500;
    private double oxygenFee = 1000;
    private double valueAddedTax = 1500;

    public abstract double getBaseTicketPrice();

    public double getBoardingFee(){
        return boardingFee;
    }

    public double getOxygenFee(){
        return oxygenFee;
    }

    public double getValueAddedTax(){
        return valueAddedTax;
    }

    public double getFinalTicketPrice(){
        return getBaseTicketPrice() + totalOptionsPrice() + totalMandatoryFees();
    }

    public abstract List<Option> getTicketOptions();

    public void addOption(Option option){
        chosenTicketOptions.add(option);
    }

    public double totalOptionsPrice(){
        return chosenTicketOptions.size() * 100;
    }

    public double totalMandatoryFees(){
        return (boardingFee + oxygenFee + valueAddedTax);
    }



}
