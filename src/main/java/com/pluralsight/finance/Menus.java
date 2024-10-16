package com.pluralsight.finance;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {
    private Scanner scanner;  // Scanner to read user input from the console
    private Ledger ledger;  // Instance of the Ledger class to manage financial transactions

    // Menu titles for different screens
    private String homeScreenTitle = """
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |          Home Screen          |
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """;
    private String ledgerMenuTitle = """
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |            Ledger             |
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """;
    private String reportsMenuTitle = """
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |            Reports            |
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """;

    // Commonly printed statements for user interaction
    private String invalidInput = "Sorry, your input does not match any of the given options. Please try again!\n"; // Message for invalid input
    private String askToTryAgain = "Would you like to try again? Enter 'Y' for yes or any other key to return to previous screen.\n"; // Prompt to retry

    // Constructor initializes the scanner and the ledger, and loads transactions from CSV
    public Menus() {
        this.scanner = new Scanner(System.in);  // Create a new Scanner object for user input
        this.ledger = new Ledger();  // Initialize the Ledger instance
        ledger.loadTransactionsFromCSV();  // Load existing transactions from the CSV file
    }

    // Method to display the home screen menu and handle user input
    public void displayHomeScreen() {
        String prompt = """
                Please enter one of the following options:
                D) Add Deposit - save a new deposit to the ledger
                P) Make Payment (Debit) - save payment info to the ledger
                L) Ledger - display the ledger screen
                X) Exit - quit the application
                """;

        boolean done = false; // Control flag to manage the input loop
        do {
            System.out.println(homeScreenTitle);  // Print the home screen title
            System.out.println(prompt);  // Print the menu options
            String input = scanner.nextLine().toLowerCase().trim();  // Read user input and normalize it

            // Handle user input based on the selected option
            switch (input) {
                case "d":  // Option to add a deposit
                    ledger.addDeposit(getDepositInfo());  // Call method to get deposit info and add it to the ledger
                    break;
                case "p":  // Option to make a payment (currently not implemented)
                    ledger.makePayment(getPaymentInfo());
                    break;
                case "l":  // Option to display the ledger
                    displayLedgerMenu();  // Call method to display the ledger menu
                    break;
                case "x":  // Option to exit the application
                    System.out.println("Exiting Account Ledger Application...");  // Inform user about exit
                    done = true;  // Set done to true to exit the loop
                    break;
                default:  // Handle invalid input
                    System.out.println(invalidInput);  // Print invalid input message
                    break;
            }
        } while (!done);  // Continue until the user chooses to exit
    }

    private void displayLedgerMenu() {
        String prompt = """
                Please enter one of the following options:
                A) All - display all entries in the ledger
                D) Deposits - show all deposits
                P) Payments - show all payments
                R) Reports - filter ledger
                H) Home - go back to the home page
                """;

        boolean done = false; // Control flag to manage the input loop
        do {
            System.out.println(ledgerMenuTitle);
            System.out.println(prompt);  // Print the menu options
            String input = scanner.nextLine().toLowerCase().trim();  // Read user input and normalize it

            // Handle user input based on the selected option
            switch (input) {
                case "a":
                    //TODO: move method into Menus class
                    ledger.displayAllLedgerEntries();
                    break;
                case "d":
                    ledger.displayDeposits();
                    break;
                case "p":
                    ledger.displayPayments();
                    break;
                case "r":
                    displayReportsMenu();
                    break;
                case "h":
                    System.out.println("Returning to Home Menu...");  // Inform user about exit
                    done = true;  // Set done to true to exit the loop
                    break;
                default:  // Handle invalid input
                    System.out.println(invalidInput);  // Print invalid input message
                    break;
            }
        } while (!done);  // Continue until the user chooses to exit
    }

    private void displayReportsMenu() {
        String prompt = """
                Filter the ledger by choosing one of the following options:
                1) Month to Date
                2) Previous month
                3) Year to Date
                4) Previous Year
                5) Search by Vendor - displays all entries from the specified vendor
                0) Back - go back to the ledger screen
                """;

        boolean done = false; // Control flag to manage the input loop
        do {
            System.out.println(reportsMenuTitle);
            System.out.println(prompt);  // Print the menu options
            //String input = scanner.nextLine().toLowerCase().trim();  // Read user input and normalize it
            int input = getIntegerFromUserInput();

            // Handle user input based on the selected option
            switch (input) {
                case 1:
                    ledger.filterMonthToDate();
                    break;
                case 2:
                    ledger.filterByPreviousMonth();
                    break;
                case 3:
                    ledger.filterYearToDate();
                    break;
                case 4:
                    ledger.filterByPreviousYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 0:
                    System.out.println("Returning to Ledger Menu...");
                    done = true;  // Set done to true to exit the loop
                    break;
                default:  // Handle invalid input
                    System.out.println(invalidInput);  // Print invalid input message
                    break;
            }
        } while (!done);  // Continue until the user chooses to exit
    }

    private void searchByVendor() {
        System.out.println("Please enter the name of the vendor: ");
        String input = scanner.nextLine().toLowerCase().trim();
        ledger.filterByVendor(input);
    }

    private int getIntegerFromUserInput() {
        boolean isValid = false;
        int input = 0;

        do {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                isValid = true;
            } catch (Exception e) {
                System.out.println("Please enter an integer...");
            }
        } while (!isValid);
        return input;
    }

    // Method to get debit payment information from the user
    private String[] getPaymentInfo() {
        System.out.println("---Enter your payment information---");
        System.out.println("Please enter a brief description of the payment: ");
        String description = scanner.nextLine();  // Read the description from user input
        System.out.println("Please enter the name of the person or company who made the payment:");  // Prompt for debit entry name
        String vendor = scanner.nextLine();
        String paymentAmount = "";

        // Loop to ensure valid deposit amount is entered
        do {
            System.out.println("Please enter the payment amount (negative number):");  // Prompt for deposit amount
            paymentAmount = scanner.nextLine();  // Read deposit amount from user input
        } while (!isValidPayment(paymentAmount));  // Validate input; continue looping if invalid

        // Return an array of strings containing deposit information
        return new String[]{description, vendor, paymentAmount};
    }

    // Method to validate if the payment amount is a negative number
    private boolean isValidPayment(String paymentAmount) {
        try {
            double value = Double.parseDouble(paymentAmount);  // Try to convert the input to a double
            if (value <= 0) {  // Check if the value is negative
                return true;  // Return true if valid
            }
        } catch (NumberFormatException e) {}  // Catch exception if parsing fails (input is not a number)

        // Inform the user about invalid input
        System.out.println("Please enter a negative non-zero number... Ex: -14, -385.3, -123.45");
        return false;  // Return false for invalid input
    }

    // Method to collect deposit information from the user
    private String[] getDepositInfo() {
        System.out.println("---Enter your deposit information---");
        System.out.println("Please enter a brief description of the deposit: ");  // Prompt for description
        String description = scanner.nextLine();  // Read the description from user input
        System.out.println("Please enter the name of the person or company who made the deposit:");  // Prompt for vendor name
        String vendor = scanner.nextLine();  // Read the vendor name
        String deposit = "";  // Initialize deposit variable

        // Loop to ensure valid deposit amount is entered
        do {
            System.out.println("Please enter the deposit amount (positive number):");  // Prompt for deposit amount
            deposit = scanner.nextLine();  // Read deposit amount from user input
        } while (!isValidDeposit(deposit));  // Validate input; continue looping if invalid

        // Return an array of strings containing deposit information
        return new String[]{description, vendor, deposit};
    }

    // Method to validate if the deposit amount is a positive number
    private boolean isValidDeposit(String input) {
        try {
            double value = Double.parseDouble(input);  // Try to convert the input to a double
            if (value >= 0) {  // Check if the value is positive
                return true;  // Return true if valid
            }
        } catch (NumberFormatException e) {}  // Catch exception if parsing fails (input is not a number)

        // Inform the user about invalid input
        System.out.println("Please enter a positive number... Ex: 10, 594.3, 321.45");
        return false;  // Return false for invalid input
    }
}
