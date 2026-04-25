public class Option {
    private String optionTitle;
    private double cost;

    public Option(String optionTitle, double cost){
        this.optionTitle = optionTitle;
        this.cost = cost;
    }

    // Getters

    public String getOptionTitle() {
        return optionTitle;
    }

    public double getCost(){
        return cost;
    }

}
