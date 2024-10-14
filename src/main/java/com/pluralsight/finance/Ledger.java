package com.pluralsight.finance;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ledger {
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<Transaction> transactions;
    private String csvLineFormat;

    public Ledger() {
        this.transactions = new ArrayList<>();
        this.startDate = null;
        this.endDate = null;
        this.csvLineFormat = null;
    }

    public void loadTransactionsFromCSV() {
        String path = "./src/main/resources/transactions.csv";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            // Skip over first line so it isn't read but keep it so it can be printed to the updated csv
            //linesOfCSVFile.add(br.readLine());
            csvLineFormat = br.readLine();
            // Each line is a transaction
            String line;
            while ((line = br.readLine()) != null) {
                //linesOfCSVFile.add(line);
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

    private void updateTransactionsInCSV() {
        //sortLedgerByMostRecent();
        String path = "./src/main/resources/transactionsOutput.csv";
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(csvLineFormat + "\n");
            for (Transaction t : transactions) {
                bw.write(t.toCSVFormat() + "\n");
            }
            bw.close();
            System.out.println("Ledger updated...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDeposit(String[] depositInfo) {
        LocalDateTime ldt = LocalDateTime.now();
        String description = depositInfo[0];
        String vendor = depositInfo[1];
        Double depositAmount = Double.parseDouble(depositInfo[2]);
        Transaction t = new Transaction(ldt, description, vendor, depositAmount);
        transactions.add(t);
        updateTransactionsInCSV();
        //linesOfCSVFile.add(t.toCSVFormat());
    }


    // TODO: make look nice
    public void displayLedger() {
        sortLedgerByMostRecent();
        System.out.println("All transactions");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public void sortLedgerByMostRecent() {
        Comparator<Transaction> comparator = (c1, c2) -> Integer.valueOf(c1.getIsoLocalDateTime().compareTo(c2.getIsoLocalDateTime()));
        Collections.sort(transactions, comparator);
        Collections.reverse(transactions);
    }
}
