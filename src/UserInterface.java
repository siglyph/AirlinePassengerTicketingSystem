import java.util.Scanner;

public class UserInterface {

    public UserInterface(){

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
        String TicketUI = "Please select your desired ticket: "
                + "\n1. Steerage: $5"
                + "\n2. Coach:    $10"
                + "\n3. Business: $20"
                + "\n4. Premium:  $30"
                + "\n5. First:    $50"
                + "\n6. Captain:  $70\n";

        int userSelection = scanner.nextInt();
    }

    public void displaySeatMapUI(){

    }

    public void exitProgram(){

    }

}
