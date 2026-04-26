// Represents a single purchasable add-on for a ticket (e.g., "Hold carry-on bag").
// Each option has a display title and a cost. In this project all options happen
// to cost $100, but the class is general so the price is stored per-option.
public class Option {
    // The text shown to the user when this option is offered.
    private String optionTitle;
    // How much this option adds to the ticket total ($100 for everything in spec).
    private double cost;

    // Build an Option with the given title and cost. Used by each ticket subclass
    // when populating its available-options list.
    public Option(String optionTitle, double cost){
        this.optionTitle = optionTitle;
        this.cost = cost;
    }

    // Read-only access to the title (used by SeatMap to detect Premium's middle-seat option).
    public String getOptionTitle() {
        return optionTitle;
    }

    // Read-only access to the cost.
    public double getCost(){
        return cost;
    }

    // Controls how an Option appears when printed. Java automatically calls this
    // when an Option is concatenated with a string or passed to System.out.println,
    // so the option-prompt loop can do `System.out.print(option + " - Add? ")`.
    @Override
    public String toString(){
        return optionTitle + " (+$" + cost + ")";
    }

}