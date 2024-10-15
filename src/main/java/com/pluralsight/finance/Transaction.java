package com.pluralsight.finance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    // Class attributes: stores date and time, description of the transaction, vendor, and amount
    private LocalDateTime isoLocalDateTime;  // The ISO date and time format
    private String dateString;  // Date in string format (yyyy-MM-dd)
    private String timeString;  // Time in string format (hh:mm:ss)
    private String description;  // Description of the transaction
    private String vendor;  // Vendor associated with the transaction
    private double amount;  // Amount for the transaction

    // Default constructor initializes the transaction object with empty or default values
    public Transaction() {
        this.isoLocalDateTime = null;  // No date/time set
        this.dateString = "";  // Empty date string
        this.timeString = "";  // Empty time string
        this.description = "";  // Empty description
        this.vendor = "";  // Empty vendor
        this.amount = 0.0;  // Default amount of 0.0
    }

    // Constructor that initializes a transaction object with all parameters passed as strings
    public Transaction(String dateString, String timeString, String description, String vendor, double amount) {
        this.dateString = dateString;  // Set the date string
        this.timeString = timeString;  // Set the time string
        this.description = description;  // Set the description of the transaction
        this.vendor = vendor;  // Set the vendor
        this.amount = amount;  // Set the transaction amount

        // Create a formatter to parse the combined date and time string into an ISO format LocalDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.isoLocalDateTime = LocalDateTime.parse(dateString + "T" + timeString, formatter);  // Parse and store date-time
    }

    // Constructor that directly accepts a LocalDateTime object for date and time
    public Transaction(LocalDateTime ldt, String description, String vendor, double amount) {
        this.isoLocalDateTime = ldt;  // Set the date and time
        this.description = description;  // Set the transaction description
        this.vendor = vendor;  // Set the vendor
        this.amount = amount;  // Set the amount

        // Format the LocalDateTime into a string for date and time separately
        this.dateString = isoLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));  // Format and store the date
        this.timeString = isoLocalDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss"));  // Format and store the time
    }

    // Getter method to retrieve the ISO formatted LocalDateTime object
    public LocalDateTime getIsoLocalDateTime() {
        return this.isoLocalDateTime;
    }

    // Getter method for transaction description
    public String getDescription() {
        return description;
    }

    // Setter method for transaction description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter method for vendor
    public String getVendor() {
        return vendor;
    }

    // Setter method for vendor
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    // Getter method for transaction amount
    public double getAmount() {
        return amount;
    }

    // Setter method for transaction amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Method to format the transaction details in a CSV-friendly format (pipe-separated)
    public String toCSVFormat() {
        return dateString + "|" + timeString + "|" + description + "|" + vendor + "|" + amount;
    }

    // Override the toString method to provide a readable format for printing a transaction object
    @Override
    public String toString() {
        return "Transaction{" +
                "dateAndTime=" + isoLocalDateTime +  // Display the full ISO date and time
                ", dateString='" + dateString + '\'' +  // Display the formatted date
                ", timeString='" + timeString + '\'' +  // Display the formatted time
                ", description='" + description + '\'' +  // Display the transaction description
                ", vendor='" + vendor + '\'' +  // Display the vendor
                ", amount=" + amount +  // Display the amount
                '}';
    }
}
