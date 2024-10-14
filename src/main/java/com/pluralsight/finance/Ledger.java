package com.pluralsight.finance;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Ledger {
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<Transaction> transactions;

    public Ledger() {
        this.transactions = new ArrayList<>();
        this.startDate = null;
        this.endDate = null;
    }

    public void loadTransactionsFromCSV() {
        String path = "./src/main/resources/transactions.csv";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            // Each line is a transaction
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                // Split each line from format: date|time|description|vendor|amount
                String[] details = line.split("[|]");
                try {
                    transactions.add(new Transaction(details[0], details[1], details[2], details[3], Double.parseDouble(details[4])));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printTransactions() {
        System.out.println("All transactions");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}
