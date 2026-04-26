import java.util.Scanner;

public class UserInterface {
    Plane plane;

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

        switch (userSelection){
            case 1: // Steerage Ticket
                SteerageTicket steerageTicket = new SteerageTicket();
                ticketOptionsUI(steerageTicket, scanner);
                plane.addTicket(steerageTicket);
                break;
            case 2: // Coach Ticket
                break;
            case 3: // Business Ticket
                break;
            case 4: // Premium Ticket
                break;
            case 5: // First Ticket
                break;
            case 6: // Captain Ticket
                break;
            default:
                System.out.println("Invalid choice. Please try again.");


        }
    }

    public void ticketOptionsUI(Ticket ticket, Scanner scanner){
        System.out.println(

                "1 " + ticket + " has been added to your cart.\n" +

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
