package com.pluralsight.finance;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ledger {
    // Ledger attributes: start and end date for the ledger, list of transactions, and CSV format string
    private ArrayList<Transaction> transactions;
    private String csvLineFormat;

    // Constructor initializes transactions list, and null for date and format fields
    public Ledger() {
        this.transactions = new ArrayList<>();
        this.csvLineFormat = null;
    }

    // Method to load transactions from a CSV file into the ledger
    public void loadTransactionsFromCSV() {
        String path = "./src/main/resources/transactions.csv";  // Path to the CSV file
        try {
            FileReader fr = new FileReader(path);  // File reader to read CSV file
            BufferedReader br = new BufferedReader(fr);  // Buffered reader for efficient reading

            // Read the first line of the CSV to set the format for later writing (header)
            csvLineFormat = br.readLine();

            String line;  // Variable to hold each line read from the file
            while ((line = br.readLine()) != null) {  // Read each line of the CSV after the header
                // Split the CSV line into individual transaction details using '|' as delimiter
                String[] details = line.split("[|]");
                try {
                    // Create a new Transaction object from the parsed details and add it to the list
                    transactions.add(new Transaction(details[0], details[1], details[2], details[3], Double.parseDouble(details[4])));
                } catch (NumberFormatException e) {
                    e.printStackTrace();  // Handle any issues converting amount to a number
                }
            }
            // Close the reader after reading
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);  // Handle case where the file is not found
        } catch (IOException e) {
            throw new RuntimeException(e);  // Handle general I/O exceptions
        }
    }

    // Private method to update the transactions back into a CSV file after making any changes
    private void updateTransactionsInCSV() {
        sortLedgerByMostRecent();
        String path = "./src/main/resources/transactions.csv";  // Output path for updated CSV
        try {
            // Create a file writer and buffered writer to write transactions to a new CSV file
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write the header line (format) first
            bw.write(csvLineFormat + "\n");

            // Write each transaction to the CSV file in the correct format
            for (Transaction t : transactions) {
                bw.write(t.toCSVFormat() + "\n");
            }

            // Close the writer after writing
            bw.close();
            System.out.println("Ledger updated...");  // Confirmation message
        } catch (IOException e) {
            throw new RuntimeException(e);  // Handle any issues during writing
        }
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    // Adds a new debit (payment) to the csv file and transactions list
    public void makePayment(String[] paymentInfo) {
        LocalDateTime ldt = LocalDateTime.now();  // Get current date and time of the payment
        // Extract transaction details from the paymentInfo array
        String description = paymentInfo[0];
        String vendor = paymentInfo[1];
        Double paymentAmount = Double.parseDouble(paymentInfo[2]);

        // Create a new transaction object using current date and payment details
        Transaction t = new Transaction(ldt, description, vendor, paymentAmount);

        // Add the new payment to the transactions list and update the CSV file
        transactions.add(t);
        updateTransactionsInCSV();
    }

    // Method to add a new deposit (income) transaction to the ledger
    public void addDeposit(String[] depositInfo) {
        LocalDateTime ldt = LocalDateTime.now();  // Get current date and time for the deposit

        // Extract transaction details from the depositInfo array
        String description = depositInfo[0];
        String vendor = depositInfo[1];
        Double depositAmount = Double.parseDouble(depositInfo[2]);

        // Create a new transaction object using current date and deposit details
        Transaction t = new Transaction(ldt, description, vendor, depositAmount);

        // Add the new deposit to the transactions list and update the CSV file
        transactions.add(t);
        updateTransactionsInCSV();
    }

    // Method to display the entire ledger in the console
    public void displayAllLedgerEntries() {
        sortLedgerByMostRecent();  // Sort the transactions by the most recent first
        // Print each transaction in the ledger
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // Displays only deposits (positive) entries from the ledger
    public void displayDeposits() {
        sortLedgerByMostRecent();  // Sort the transactions by the most recent first

        // Print each transaction in the ledger
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                System.out.println(t);
            }
        }
    }

    public void displayPayments() {
        sortLedgerByMostRecent();  // Sort the transactions by the most recent first

        // Print each transaction in the ledger
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                System.out.println(t);
            }
        }
    }

    // Method to sort the ledger by the most recent transaction (in reverse order)
    public void sortLedgerByMostRecent() {
        // Create a comparator that compares transactions based on their date and time
        Comparator<Transaction> comparator = (t1, t2) -> Integer.valueOf(t1.getIsoLocalDateTime().compareTo(t2.getIsoLocalDateTime()));

        // Sort the transactions list in ascending order based on the comparator
        Collections.sort(transactions, comparator);

        // Reverse the order to make it descending (most recent first)
        Collections.reverse(transactions);
    }

    // Filter and display transactions from the current month to date
    public void filterMonthToDate() {
        sortLedgerByMostRecent();
        int monthValue = LocalDate.now().getMonthValue();
        for (Transaction t : transactions) {
            if (t.getIsoLocalDateTime().getMonthValue() == monthValue) {
                System.out.println(t);
            }
        }
    }

    // Filter and display transactions from the previous month
    public void filterByPreviousMonth() {
        sortLedgerByMostRecent();
        int previousMonthValue = LocalDate.now().getMonthValue() - 1;
        if (previousMonthValue == 0) { // If current month is January, wrap value so it is December
            previousMonthValue = 12;
        }
        for (Transaction t : transactions) {
            if (t.getIsoLocalDateTime().getMonthValue() == previousMonthValue) {
                System.out.println(t);
            }
        }
    }

    // Filter and display transactions for the current year to date
    public void filterYearToDate() {
        sortLedgerByMostRecent();
        int yearValue = LocalDate.now().getYear();
        for (Transaction t : transactions) {
            if (t.getIsoLocalDateTime().getYear() == yearValue) {
                System.out.println(t);
            }
        }
    }

    // Filter and display transactions from the previous year
    public void filterByPreviousYear() {
        sortLedgerByMostRecent();
        int previousYearValue = LocalDate.now().getYear() - 1;
        for (Transaction t : transactions) {
            if (t.getIsoLocalDateTime().getYear() == previousYearValue) {
                System.out.println(t);
            }
        }

    }

    // Filter and display transactions from the previous year
    public void filterByVendor(String input) {
        sortLedgerByMostRecent();
        boolean found = false;
        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(input)) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("There are no vendors by that name in the ledger...");
        }
    }
}
