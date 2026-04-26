// Tracks which seats on the plane are purchased and renders the ASCII seat map.
//
// Seat layout interpretation:
//   - Business / Premium share a 6 x 7 grid (6 letter rows A-F, 7 numbered rows 1-7 = 42 seats).
//     The spec calls these "6 abreast, 7 rows" - I represent them as letter (A-F) + number (1-7).
//     Middle seats are columns B and E. Non-middle are A, C, D, F.
//   - First class has 1 dedicated seat, identified by the ID "F" (single letter).
//   - Captain class has 1 dedicated seat, identified by the ID "C" (single letter).
//
// Naming-collision note: the letter F is used for both the F-row of the grid (F1..F7) and
// for the First class seat. They are disambiguated by length: a single-letter seat ID is the
// special seat; a two-character seat ID is a grid seat. Same idea for C.
public class SeatMap {

    // grid[letterIndex][numberIndex], where A=0..F=5 and 1=0..7=6.
    // True means the seat is purchased.
    private boolean[][] grid = new boolean[6][7];

    // Single-passenger seats outside the grid - tracked as simple booleans
    // since each is just one seat.
    private boolean firstTaken = false;
    private boolean captainTaken = false;

    // Seat IDs for the special single seats. Defined as constants so the magic
    // strings appear in exactly one place.
    private static final String FIRST_SEAT_ID = "F";
    private static final String CAPTAIN_SEAT_ID = "C";

    // Returns true if seatId names an actual seat on the plane.
    // Does NOT consider whether the seat is taken or whether it is allowed
    // for a particular class - just structural validity.
    public boolean isValidSeatId(String seatId) {
        if (seatId == null) return false;
        seatId = seatId.toUpperCase();

        // Single-letter special seats
        if (seatId.equals(FIRST_SEAT_ID) || seatId.equals(CAPTAIN_SEAT_ID)) {
            return true;
        }

        // Grid seats: exactly 2 chars, letter A-F followed by digit 1-7
        if (seatId.length() != 2) return false;
        char letter = seatId.charAt(0);
        char digit = seatId.charAt(1);
        return letter >= 'A' && letter <= 'F' && digit >= '1' && digit <= '7';
    }

    // Returns true if the seat is currently purchased. The caller should ensure
    // isValidSeatId() first; this method assumes the ID is well-formed.
    public boolean isTaken(String seatId) {
        seatId = seatId.toUpperCase();
        if (seatId.equals(FIRST_SEAT_ID)) return firstTaken;
        if (seatId.equals(CAPTAIN_SEAT_ID)) return captainTaken;

        // Convert letter/digit chars to grid array indices.
        int letterIdx = seatId.charAt(0) - 'A';
        int rowIdx = seatId.charAt(1) - '1';
        return grid[letterIdx][rowIdx];
    }

    // Marks a seat as purchased. Caller is responsible for validating
    // availability first (Plane.addTicket calls this after the UI confirms).
    public void markTaken(String seatId) {
        seatId = seatId.toUpperCase();
        if (seatId.equals(FIRST_SEAT_ID)) {
            firstTaken = true;
            return;
        }
        if (seatId.equals(CAPTAIN_SEAT_ID)) {
            captainTaken = true;
            return;
        }
        int letterIdx = seatId.charAt(0) - 'A';
        int rowIdx = seatId.charAt(1) - '1';
        grid[letterIdx][rowIdx] = true;
    }

    // Returns true if the given seat is a valid choice for the given ticket:
    //   - the seat ID is well-formed
    //   - the seat is not already taken
    //   - the seat is allowed for this passenger class (e.g. Business -> middle seats only)
    //
    // I chose to put class-specific seat rules here (using instanceof) rather
    // than as abstract methods on Ticket. This keeps all seat-related logic in
    // a single class, which I think reads more clearly than spreading it
    // across six subclasses.
    public boolean isValidForTicket(Ticket ticket, String seatId) {
        if (!isValidSeatId(seatId)) return false;
        if (isTaken(seatId)) return false;

        seatId = seatId.toUpperCase();

        // First / Captain may only pick their dedicated single seat.
        if (ticket instanceof FirstTicket) {
            return seatId.equals(FIRST_SEAT_ID);
        }
        if (ticket instanceof CaptainTicket) {
            return seatId.equals(CAPTAIN_SEAT_ID);
        }

        // Reject the special seats for Business / Premium - they pick from the grid only.
        if (seatId.equals(FIRST_SEAT_ID) || seatId.equals(CAPTAIN_SEAT_ID)) {
            return false;
        }

        // Grid seats: middle seats are columns B and E (the center of each 3+3 row).
        boolean isMiddle = (seatId.charAt(0) == 'B' || seatId.charAt(0) == 'E');

        if (ticket instanceof BusinessTicket) {
            // Business is locked to middle seats only.
            return isMiddle;
        }
        if (ticket instanceof PremiumTicket) {
            // Premium gets non-middle seats by default. Middle seats are an option.
            // Interpretation: I check whether the ticket has selected a chosen
            // option whose title contains "middle". This is the cleanest way to
            // detect the middle-seat option without hard-coding object equality,
            // since options are constructed fresh per ticket.
            if (!isMiddle) return true;
            return hasMiddleSeatOption(ticket);
        }

        // Steerage / Coach should never reach here (they don't have seats), but be safe.
        return false;
    }

    // Helper: did this ticket purchase the middle-seat option?
    // Identified by an option title containing "middle" (case-insensitive).
    private boolean hasMiddleSeatOption(Ticket ticket) {
        for (Option opt : ticket.getChosenTicketOptions()) {
            if (opt.getOptionTitle().toLowerCase().contains("middle")) {
                return true;
            }
        }
        return false;
    }

    // Returns true if this is one of the door-plug seats (A5 or F5).
    // The UI uses this to print the bad-vibes disclaimer.
    public boolean isDoorPlugSeat(String seatId) {
        if (seatId == null) return false;
        seatId = seatId.toUpperCase();
        return seatId.equals("A5") || seatId.equals("F5");
    }

    // Renders the seat map as a multi-line string, with [XX] for taken seats
    // and the [F]/[C] markers becoming [X] when those seats are taken.
    public String render() {
        StringBuilder sb = new StringBuilder();
        // The | wall sits at column 4 on every seat row. The top/bottom dashed
        // edges start at that same column so they "close" the cabin against the
        // wall instead of extending past it. The [F] and [C] markers represent
        // a separate front-of-plane compartment that sticks out to the LEFT of
        // the cabin wall.
        //
        // The slashes form the curving fuselage corners:
        //   - F and A rows (narrowest) have the slash flush against the | wall
        //     so the cabin closes to a point at top and bottom
        //   - E and B rows (wider) have a space between slash and | wall
        //   - D and C rows are the widest (where the [F] / [C] markers sit)
        //
        // The ** marks at columns 27-28 land directly above F5 (top) and
        // below A5 (bottom) - the door-plug seats.
        String edge = "    -----------------------**-----------";

        sb.append(edge).append("\n");
        sb.append("   /| ").append(rowString('F')).append("\n");
        sb.append("  / | ").append(rowString('E')).append("\n");
        // [F] becomes [X] when the First class seat has been purchased.
        sb.append("[").append(firstTaken ? "X" : "F").append("] | ")
                .append(rowString('D')).append("\n");
        // Aisle gap: six-abreast seating is 3+3 with the aisle between
        // rows D and C. The | wall continues through this line so the
        // cabin structure stays intact - just no seats here.
        sb.append("    |").append("\n");
        // [C] becomes [X] when the Captain class seat has been purchased.
        sb.append("[").append(captainTaken ? "X" : "C").append("] | ")
                .append(rowString('C')).append("\n");
        sb.append("  \\ | ").append(rowString('B')).append("\n");
        sb.append("   \\| ").append(rowString('A')).append("\n");
        sb.append(edge);
        return sb.toString();
    }

    // Builds one row of the grid for rendering, e.g.
    // "[F1] [F2] [F3] [F4] [F5] [F6] [F7]" with [XX] substituted for any
    // seats already taken in that letter-row.
    private String rowString(char letter) {
        StringBuilder sb = new StringBuilder();
        int letterIdx = letter - 'A';
        for (int col = 0; col < 7; col++) {
            // Insert a space between seat brackets so they're readable.
            if (col > 0) sb.append(" ");
            sb.append("[");
            if (grid[letterIdx][col]) {
                // Taken seats display as [XX] regardless of which seat they were.
                sb.append("XX");
            } else {
                // Open seats show their seat ID, e.g. [F1].
                sb.append(letter).append(col + 1);
            }
            sb.append("]");
        }
        return sb.toString();
    }
}