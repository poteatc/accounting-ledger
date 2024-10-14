package com.pluralsight.finance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDateTime dateAndTime;
    private String dateString;
    private String timeString;
    private String description;
    private String vendor;
    private double amount;

    // Default constructor --doesn't have parameters
    public Transaction() {
        this.dateAndTime = null;
        this.dateString = "";
        this.timeString = "";
        this.description = "";
        this.vendor = "";
        this.amount = 0.0;
    }

    // Parametrized constructor
    public Transaction(String dateString, String timeString, String description, String vendor, double amount) {
        this.dateString = dateString;
        this.timeString = timeString;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;

        // create a formatter
        DateTimeFormatter formatter
                = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.dateAndTime = LocalDateTime.parse(dateString + "T" + timeString, formatter);
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
                "dateAndTime=" + dateAndTime +
                ", dateString='" + dateString + '\'' +
                ", timeString='" + timeString + '\'' +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
