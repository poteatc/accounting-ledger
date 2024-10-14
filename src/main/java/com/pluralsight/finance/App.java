package com.pluralsight.finance;

public class App {
    public static void main(String[] args) {
        Ledger ledger = new Ledger();
        ledger.loadTransactionsFromCSV();
        ledger.printTransactions();
    }
}
