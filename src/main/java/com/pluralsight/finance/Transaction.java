package com.pluralsight.finance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDateTime isoLocalDateTime;
    private String dateString;
    private String timeString;
    private String description;
    private String vendor;
    private double amount;

    // Default constructor --doesn't have parameters
    public Transaction() {
        this.isoLocalDateTime = null;
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
        this.isoLocalDateTime = LocalDateTime.parse(dateString + "T" + timeString, formatter);
    }

    public Transaction(LocalDateTime ldt, String description, String vendor, double amount) {
        this.isoLocalDateTime = ldt;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        //String ldtAsString = isoLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //String[] dateAndTimeStrings = ldtAsString.split("T");
        this.dateString = isoLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.timeString = isoLocalDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
    }

    public LocalDateTime getIsoLocalDateTime() {
        return this.isoLocalDateTime;
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

    public String toCSVFormat() {
        return dateString + "|" + timeString + "|" + description + "|" + vendor + "|" + amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "dateAndTime=" + isoLocalDateTime +
                ", dateString='" + dateString + '\'' +
                ", timeString='" + timeString + '\'' +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
