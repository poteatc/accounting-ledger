package com.pluralsight.finance;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String description;
    private String vendor;
    private double amount;

    // Default constructor --doesn't have parameters
    public Transaction() {
        this.date = null;
        this.description = "";
        this.vendor = "";
        this.amount = 0.0;
    }

    // Parametrized constructor
    public Transaction(LocalDate date, String description, String vendor, double amount) {
        this.date = date;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
