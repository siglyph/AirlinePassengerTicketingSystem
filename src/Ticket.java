import java.util.ArrayList;
import java.util.List;

// Abstract base class for all ticket types. Each concrete subclass
// (SteerageTicket, CoachTicket, BusinessTicket, etc.) defines its own base
// price and available options, but they all share the same mandatory fees,
// pricing math, and seat/option storage - which live here in the parent.
//
// Marked abstract because there is no such thing as a generic "Ticket" -
// every real ticket is one of the six specific classes.
public abstract class Ticket {
    // The options this passenger has chosen (the "cart"). Starts empty; the UI
    // appends to it via addOption() for each option the passenger says yes to.
    private List<Option> chosenTicketOptions = new ArrayList<>();

    // Mandatory fees that apply to every ticket regardless of class. Stored as
    // fields so the UI can itemize them in the receipt via the getters below.
    private double boardingFee = 500;
    private double oxygenFee = 1000;
    private double valueAddedTax = 1500;

    // The seat assigned to this ticket (e.g., "B3", "F", "C"). Null for ticket
    // types with no seat (Steerage and Coach). Set by the UI after the user
    // picks a seat, and read by Plane to mark that seat taken on the seat map.
    private String assignedSeat;

    // Each subclass defines its own base fare (e.g., Steerage = 5, Captain = 70).
    // Abstract so the compiler forces every subclass to provide one.
    public abstract double getBaseTicketPrice();

    // Mandatory-fee getters so the UI can show each fee on its own line in
    // the receipt rather than just the lump-sum total.
    public double getBoardingFee(){
        return boardingFee;
    }

    public double getOxygenFee(){
        return oxygenFee;
    }

    public double getValueAddedTax(){
        return valueAddedTax;
    }

    // Computes the final ticket price as base fare + all options + mandatory fees.
    // Computed fresh each call so it always reflects the current state of options.
    public double getFinalTicketPrice(){
        return getBaseTicketPrice() + totalOptionsPrice() + totalMandatoryFees();
    }

    // Returns the list of options THIS ticket type offers (the "menu" of available
    // options). Each subclass builds its own list in its constructor and returns
    // it here. This is the polymorphic hook that lets the UI ask any ticket what
    // its options are without knowing the concrete subclass.
    public abstract List<Option> getTicketOptions();

    // Adds an option the passenger has chosen to the cart. Called by the UI
    // when the user answers "y" to an option prompt.
    public void addOption(Option option){
        chosenTicketOptions.add(option);
    }

    // Exposes the chosen-options list (read-only by convention) so SeatMap can
    // check whether a Premium passenger has selected the middle-seat option.
    public List<Option> getChosenTicketOptions(){
        return chosenTicketOptions;
    }

    // Each option costs a flat $100 per the spec, so the total is just count * 100.
    public double totalOptionsPrice(){
        return chosenTicketOptions.size() * 100;
    }

    // All three mandatory fees added together. Used by getFinalTicketPrice().
    public double totalMandatoryFees(){
        return (boardingFee + oxygenFee + valueAddedTax);
    }

    // Standard getter/setter for the assigned seat.
    public String getAssignedSeat(){
        return assignedSeat;
    }

    public void setAssignedSeat(String seat){
        this.assignedSeat = seat;
    }

    // Returns true if this ticket type has assigned seating. Default is true;
    // SteerageTicket and CoachTicket override to return false so the UI knows
    // to skip the seat-selection step for those classes.
    public boolean needsSeatSelection(){
        return true;
    }

}