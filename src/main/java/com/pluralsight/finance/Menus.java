package com.pluralsight.finance;

import java.util.Scanner;

public class Menus {
    private Scanner scanner;
    private Ledger ledger;

    // Menu titles
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

    // Commonly printed statements
    private String invalidInput = "Sorry, your input does not match any of the given options. Please try again!\n";
    private String askToTryAgain = "Would you like to try again? Enter 'Y' for yes or any other key to return to previous screen.\n";

    public Menus() {
        this.scanner = new Scanner(System.in);
        this.ledger = new Ledger();
        ledger.loadTransactionsFromCSV();
    }

    public void displayHomeScreen() {
        String prompt = """
                Please enter one of the following options:
                D) Add Deposit - save a new deposit to the ledger
                P) Make Payment (Debit) - save payment info to the ledger
                L) Ledger - display the ledger screen
                X) Exit - quit the application
                """;
        boolean done = false; // controls the input loop
        do {
            System.out.println(homeScreenTitle);
            System.out.println(prompt);
            String input = scanner.nextLine().toLowerCase().trim();
            switch (input) {
                case "d":
                    ledger.addDeposit(getDepositInfo());
                    break;
                case "p":
                    //makePayment();
                    break;
                case "l":
                    ledger.displayLedger();
                    break;
                case "x":
                    System.out.println("Exiting Account Ledger Application...");
                    done = true;
                    break;
                default:
                    System.out.println(invalidInput);
                    break;
            }
        } while (!done);
    }

    private String[] getDepositInfo() {
        System.out.println("---Enter your deposit information---");
        System.out.println("Please enter a brief description of the deposit: ");
        String description = scanner.nextLine();
        System.out.println("Please enter the name of the person or company who made the deposit:");
        String vendor = scanner.nextLine();
        String deposit = "";
        do {
            System.out.println("Please enter the deposit amount (positive number):");
            deposit = scanner.nextLine();
        } while (!isValidDeposit(deposit));
        return new String[]{description, vendor, deposit};
    }

    private boolean isValidDeposit(String input) {
        try {
            double value = Double.parseDouble(input);
            if (value > 0) {
                return true;
            }
        } catch (NumberFormatException e) {}
        System.out.println("Please enter a positive number... Ex: 10, 594.3, 321.45");
        return false;
    }
}
