import java.util.Scanner;

public class UserInterface {
    private Plane plane;

    public UserInterface(Plane plane){
        this.plane = plane;
    }

    public void runUI(){
        Scanner scanner = new Scanner(System.in);

        String mainMenu = "Welcome to Poltergeist Airlines! What would you like to do today?" +
                "\n1. Add a new ticket" +
                "\n2. Display seat map" +
                "\n3. Exit";
        System.out.println(mainMenu);

        int userSelection = scanner.nextInt();

        switch (userSelection){
            case 1:
                addTicketUI(scanner);
                break;
            case 2:
                displaySeatMapUI();
                break;
            case 3:
                exitProgram();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }

    }

    public void addTicketUI(Scanner scanner){
        int userSelection = getTicketSelection(scanner);
        Ticket ticket = createTicket(userSelection);

        if (ticket == null) {return;}

        ticketOptionsUI(ticket, scanner);
        getPriceBreakdown(ticket);
        plane.addTicket(ticket);
    }

    private Ticket createTicket(int choice){
        switch (choice){
            case 1: // Steerage Ticket
                return new SteerageTicket();
            case 2: // Coach Ticket
                return new CoachTicket();
            case 3: // Business Ticket
                return new BusinessTicket();
            case 4: // Premium Ticket
                return new PremiumTicket();
            case 5: // First Ticket
                return new FirstTicket();
            case 6: // Captain Ticket
                return new CaptainTicket();
            default:
                return null;
        }
    }

    public void ticketOptionsUI(Ticket ticket, Scanner scanner){
        System.out.println(

                ticket + " has been added to your cart.\n\n" +

                "Would you like to add any add-ons to your ticket?\n" +
                "Available Ticket Options For " + ticket + "\n" +
                "-----------------------------"

        );

        for (Option option : ticket.getTicketOptions()){
            System.out.print(option + " - Add? (y/n): ");
            char yesOrNo = scanner.next().charAt(0);

            if (yesOrNo == 'y'){
                ticket.addOption(option);
            }
        }

    }

    public void getPriceBreakdown(Ticket ticket){
        System.out.println(
            "\nBase fare: $" + ticket.getBaseTicketPrice() +
            "\nTicket optionals: $" + ticket.totalOptionsPrice() +
            "\nBoarding fee: $" + ticket.getBoardingFee() +
            "\nOxygen fee: $" + ticket.getOxygenFee() +
            "\nValue added tax: $" + ticket.getValueAddedTax() +
            "\n-----------" +
            "\nTotal: " + ticket.getFinalTicketPrice()
        );
    }

    public int getTicketSelection(Scanner scanner){
        String TicketUI = "Please select your desired ticket: "
                + "\n1. Steerage: $5"
                + "\n2. Coach:    $10"
                + "\n3. Business: $20"
                + "\n4. Premium:  $30"
                + "\n5. First:    $50"
                + "\n6. Captain:  $70";
        System.out.println(TicketUI);
        return scanner.nextInt();
    }

    public void displaySeatMapUI(){

    }

    public void exitProgram(){

    }

}
