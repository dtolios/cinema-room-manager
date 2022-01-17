package cinema;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Theater theater = initializeTheater();
        int menuChoice = -1;

        while(menuChoice != 0) {
            printMenu();
            menuChoice = readInt(null);
            performMenuAction(menuChoice, theater);
        }
    }

    private static Theater initializeTheater() {
        final int maxRows = 9, maxSeatsPerRow = 9;
        int numRows = 0, numSeatsPerRow = 0;
        boolean isValidTheaterSize = false;

        while (!isValidTheaterSize) {
            numRows = readInt("Enter the number of rows:");
            numSeatsPerRow = readInt("Enter the number of seats in each row:");

            if (numRows > maxRows || numRows < 1) {
                System.out.println("Number of rows must be between 1 and 9");
            } else if (numSeatsPerRow > maxSeatsPerRow || numSeatsPerRow < 0) {
                System.out.println("Number of seats per row must be between 1 and 9");
            } else {
                isValidTheaterSize = true;

            }
        }

        return new Theater(numRows, numSeatsPerRow);
    }

    private static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private static void performMenuAction(int choice, Theater theater) {
        switch(choice) {
            case 1:
                theater.showTheater();
                break;
            case 2:
                theater.tryToPurchaseTicket();
                break;
            case 3:
                 theater.showStats();
                break;
        }
    }

    static int readInt(String message) {
        Scanner scanner = new Scanner(System.in);
        if (message != null) {
            System.out.println(message);
        }
        return scanner.nextInt();
    }
}