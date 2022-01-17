package cinema;

public class Theater {
    private static final int MIN_NUM_SEATS_LARGE = 60;
    private static final int STANDARD_TICKET_PRICE = 10;
    private static final int REDUCED_TICKET_PRICE = 8;
    private static final char EMPTY_SEAT = 'S';
    private static final char BOOKED_SEAT = 'B';

    private final int rows;
    private final int seatsPerRow;
    private final int potentialIncome;
    private final char[][] theaterLayout;

    private int ticketsPurchased = 0;
    private int currentIncome = 0;

    public Theater(int rows, int seatsPerRow) {
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.theaterLayout = new char[rows][seatsPerRow];
        this.potentialIncome = calculateTotalPotentialIncome(rows, seatsPerRow);

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < seatsPerRow; j++) {
                theaterLayout[i][j] = EMPTY_SEAT;
            }
        }
    }

    /**
     * Prints the current state of the theater.
     */
    public void showTheater() {
        // Print the cinema title
        System.out.println("Cinema:");

        // Print the seat marker row
        System.out.print(" ");
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.printf(" %d", i);
        }
        System.out.print("\n");

        // Print each row and seats
        for (int i = 1; i <= rows; i++) {
            System.out.print(i);
            for (int j = 1; j <= seatsPerRow; j++) {
                System.out.printf(" %c", theaterLayout[i - 1][j - 1]);
            }
            System.out.println();
        }
    }

    /**
     * Attempts to purchase a ticket in the theater.
     * Will continue to retry until a valid input is received.
     */
    public void tryToPurchaseTicket() {
        int requestedRow = 0;
        int requestedSeat = 0;
        boolean isValidSeat = false;
        boolean isEmptySeat = false;

        while(!isValidSeat || !isEmptySeat) {
            requestedRow = Cinema.readInt("Enter a row number:");
            requestedSeat = Cinema.readInt("Enter a seat number in that row:");

            isValidSeat = validateSeatChoice(requestedRow, requestedSeat);

            if (!isValidSeat) {
                System.out.println("Wrong input!");
                continue;
            }

            isEmptySeat = theaterLayout[requestedRow - 1][requestedSeat - 1] == EMPTY_SEAT;

            if (!isEmptySeat) {
                System.out.println("That ticket has already been purchased!");
            }
        }

        purchaseTicket(requestedRow, requestedSeat);
    }

    /**
     * Prints the current number of purchased tickets, percentage of tickets sold,
     * the current income, and the total income if all tickets are sold.
     */
    public void showStats() {
        int totalSeats = rows * seatsPerRow;
        double percentPurchased =  (double) ticketsPurchased / totalSeats * 100.0f;
        System.out.printf("Number of purchased tickets: %d\n", ticketsPurchased);
        System.out.printf("Percentage: %.2f%%\n", percentPurchased);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", potentialIncome);
    }

    private int getTicketPrice(int row) {
        boolean isSmallTheater = rows * seatsPerRow < MIN_NUM_SEATS_LARGE;
        boolean isInFront = row <= rows / 2;

        if (isSmallTheater || isInFront) {
            return STANDARD_TICKET_PRICE;
        } else {
            return REDUCED_TICKET_PRICE;
        }
    }

    private void purchaseTicket(int row, int seat) {
        int ticketPrice = getTicketPrice(row);
        theaterLayout[row - 1][seat - 1] = BOOKED_SEAT;
        currentIncome += ticketPrice;
        ticketsPurchased++;
        System.out.println("Ticket price: $" + ticketPrice);
    }

    private boolean validateSeatChoice(int row, int seat) {
        boolean isRowValid = row - 1 >= 0 && row - 1 < rows;
        boolean isSeatValid = seat - 1 >= 0 && seat - 1 < seatsPerRow;
        return isRowValid && isSeatValid;
    }

    private static int calculateTotalPotentialIncome(int rows, int seatsPerRow) {
        boolean isSmallTheater = rows * seatsPerRow < MIN_NUM_SEATS_LARGE;

        if (isSmallTheater) {
            return STANDARD_TICKET_PRICE * rows * seatsPerRow;
        }
        int frontRows = rows / 2;
        int backRows = rows - (rows / 2);

        int frontHalfIncome = STANDARD_TICKET_PRICE * seatsPerRow * frontRows;
        int backHalfIncome = REDUCED_TICKET_PRICE * seatsPerRow * backRows;

        return frontHalfIncome + backHalfIncome;
    }
}
