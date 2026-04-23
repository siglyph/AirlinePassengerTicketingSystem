public abstract class Ticket {

    String seatID;
    int bagsAmt;
    double ticketPrice;

    public void mandatoryFees(){
        double boardingFee = 500;
        double oxygenFee = 1000;
        double valueAddedTax = 1500;
    }
}
