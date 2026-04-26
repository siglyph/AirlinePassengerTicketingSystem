import java.util.Scanner;

// The user-facing menu and prompts. This is the only class that reads from
// Scanner or writes to System.out - the domain classes (Ticket, SeatMap, Plane)
// are kept free of I/O so they can be reasoned about independently.
public class UserInterface {
    // The plane to operate on. Held as a field so every UI method can talk to it.
    private Plane plane;

    // Constructor: receive the plane from Main and store it. The plane lives as
    // long as the UserInterface does (i.e., the whole program run).
    public UserInterface(Plane plane){
        this.plane = plane;
    }

    // Main menu loop. Keeps prompting until the user picks Exit (option 3).
    // Each iteration shows the menu, reads a choice, and dispatches to the
    // appropriate sub-screen.
    public void runUI(){
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running){
            String mainMenu = "\nWelcome to Poltergeist Airlines! What would you like to do today?" +
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
                    // Flip the flag so the while loop exits cleanly.
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Drives the full purchase flow for one ticket: pick a class, check capacity,
    // pick options, pick a seat (if applicable), print the receipt, register
    // the ticket on the plane.
    public void addTicketUI(Scanner scanner){
        int userSelection = getTicketSelection(scanner);
        Ticket ticket = createTicket(userSelection);

        // createTicket() returns null for invalid menu choices - bail out.
        if (ticket == null) {return;}

        // Capacity gates: refuse the sale before bothering the passenger with
        // options/seat selection if their class is full. Steerage/Coach use
        // counts; First/Captain use their single dedicated seat's occupancy.
        if (ticket instanceof SteerageTicket && !plane.canAddSteerage()) {
            System.out.println("Sorry, Steerage class is full.");
            return;
        }
        if (ticket instanceof CoachTicket && !plane.canAddCoach()) {
            System.out.println("Sorry, Coach class is full.");
            return;
        }
        if (ticket instanceof FirstTicket && !plane.canAddFirst()) {
            System.out.println("Sorry, the First class seat is already taken.");
            return;
        }
        if (ticket instanceof CaptainTicket && !plane.canAddCaptain()) {
            System.out.println("Sorry, the Captain class seat is already taken.");
            return;
        }

        // Walk the passenger through their options menu.
        ticketOptionsUI(ticket, scanner);

        // Seat selection runs after option selection so that Premium's
        // middle-seat option (if chosen) is visible to the seat validator.
        // Steerage and Coach skip this step entirely.
        if (ticket.needsSeatSelection()) {
            boolean seated = seatSelectionUI(ticket, scanner);
            if (!seated) {
                // User typed "cancel" during seat selection - abort the purchase.
                System.out.println("Ticket purchase cancelled.");
                return;
            }
        }

        // Show the price breakdown and add the completed ticket to the plane.
        printPriceBreakdown(ticket);
        plane.addTicket(ticket);
    }

    // Maps a menu choice (1-6) to a freshly-constructed ticket of the matching
    // type. Returns null for invalid input. This is the one place in the UI
    // that has to know about specific subclasses - everything downstream uses
    // the polymorphic Ticket reference.
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

    // Walks the passenger through every option this ticket type offers,
    // prompting y/n for each. Polymorphic - works the same way for any
    // ticket subclass because it just calls ticket.getTicketOptions().
    public void ticketOptionsUI(Ticket ticket, Scanner scanner){
        System.out.println(

                ticket + " has been added to your cart.\n\n" +

                        "Would you like to add any add-ons to your ticket?\n" +
                        "Available Ticket Options For " + ticket + "\n" +
                        "-----------------------------"

        );

        // Loop through each available option and prompt the user. If they
        // say yes, add it to the cart via ticket.addOption().
        for (Option option : ticket.getTicketOptions()){
            System.out.print(option + " - Add? (y/n): ");
            char yesOrNo = scanner.next().charAt(0);

            if (yesOrNo == 'y' || yesOrNo == 'Y'){
                ticket.addOption(option);
            }
        }
    }

    // Prompts the user to pick a seat. Loops until they enter a valid choice
    // or type "cancel" to abort the purchase. Returns true if a seat was
    // successfully assigned.
    //
    // The seat map is shown once at the top of the prompt; the spec says it
    // should be displayed for all passenger classes that have seating,
    // regardless of which seats that class can actually pick.
    public boolean seatSelectionUI(Ticket ticket, Scanner scanner){
        SeatMap seatMap = plane.getSeatMap();

        System.out.println("\nSeat selection for " + ticket + ":");
        System.out.println(seatMap.render());
        System.out.println();
        System.out.println("(Type 'cancel' to abort the purchase.)");

        while (true) {
            System.out.print("Enter seat ID (e.g. A5, B3, F, C): ");
            String seatId = scanner.next();

            // Escape hatch: lets the user back out if they don't want any seat.
            if (seatId.equalsIgnoreCase("cancel")) {
                return false;
            }

            // Normalize so users can type "a5" or "A5" interchangeably.
            seatId = seatId.toUpperCase();

            // If the seat isn't valid for this ticket, give a specific reason
            // (rather than a generic "invalid") to help the user fix their input.
            if (!seatMap.isValidForTicket(ticket, seatId)) {
                if (!seatMap.isValidSeatId(seatId)) {
                    System.out.println("That isn't a valid seat ID. Try again.");
                } else if (seatMap.isTaken(seatId)) {
                    System.out.println("That seat is already taken. Try another.");
                } else {
                    System.out.println("That seat is not available for your ticket class. Try another.");
                }
                continue;
            }

            // Door-plug disclaimer for A5 and F5 per the spec. Just informational -
            // the seat is still selected.
            if (seatMap.isDoorPlugSeat(seatId)) {
                System.out.println();
                System.out.println("DISCLAIMER: Seat " + seatId + " is next to a door plug.");
                System.out.println("If the door plug blows out mid-flight, it is the fault");
                System.out.println("of the passenger for having bad vibes.");
                System.out.println();
            }

            // Record the seat on the ticket. Plane.addTicket() will later read
            // this and mark the seat taken on the seat map.
            ticket.setAssignedSeat(seatId);
            return true;
        }
    }

    // Prints the itemized receipt: base fare, options, mandatory fees, total,
    // and the assigned seat (if any). Called after option/seat selection finishes.
    public void printPriceBreakdown(Ticket ticket){
        System.out.println(
                "\nBase fare: $" + ticket.getBaseTicketPrice() +
                        "\nTicket optionals: $" + ticket.totalOptionsPrice() +
                        "\nBoarding fee: $" + ticket.getBoardingFee() +
                        "\nOxygen fee: $" + ticket.getOxygenFee() +
                        "\nValue added tax: $" + ticket.getValueAddedTax() +
                        "\n-----------" +
                        "\nTotal: $" + ticket.getFinalTicketPrice()
        );
        // Only show the seat line if there actually is a seat (Steerage/Coach skip this).
        if (ticket.getAssignedSeat() != null) {
            System.out.println("Assigned seat: " + ticket.getAssignedSeat());
        }
    }

    // Shows the ticket-class menu and reads the user's pick. Returns the
    // raw integer; createTicket() handles converting it to a Ticket subclass.
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

    // Display the seat map plus the seatless-class counters. The spec says we
    // are free to display the Steerage/Coach counts however we like, so I
    // print them below the map for context.
    public void displaySeatMapUI(){
        SeatMap seatMap = plane.getSeatMap();
        System.out.println();
        System.out.println(seatMap.render());
        System.out.println();
        System.out.println("Steerage tickets sold: " + plane.getSteerageCount() + " / " + plane.getSteerageCapacity());
        System.out.println("Coach tickets sold:    " + plane.getCoachCount() + " / " + plane.getCoachCapacity());
    }

}