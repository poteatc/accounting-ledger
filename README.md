# Account Ledger Application

## Overview

The **Account Ledger Application** is a simple console-based tool designed to help users manage their financial transactions. Users can add deposits, make payments, view a full ledger of their transactions, and generate filtered reports based on various criteria. The project is structured around several classes that handle the core functionality of recording transactions, displaying reports, and managing user interactions through menus.

## Features

- Add deposits to the ledger.
- Make payments (recorded as negative amounts).
- View all transactions, deposits, or payments.
- Generate filtered reports by date, vendor, or custom search.
- Transactions are stored in a CSV file for persistence.

## How to Use

### Home Screen Menu

When the application starts, users are presented with the Home Screen Menu. The options available are:

- **D**: Add a deposit to the ledger.
- **P**: Make a payment (debit).
- **L**: View the ledger menu.
- **X**: Exit the application.

### Ledger Menu

The Ledger Menu allows users to view and manage transactions. Options include:

- **A**: View all entries in the ledger.
- **D**: View only deposits.
- **P**: View only payments.
- **R**: Access the reports menu for advanced filtering.
- **H**: Return to the Home Screen.

### Reports Menu

The Reports Menu provides various filters for generating reports:

- **1**: View transactions for the current month (Month-to-Date).
- **2**: View transactions for the previous month.
- **3**: View transactions for the current year (Year-to-Date).
- **4**: View transactions for the previous year.
- **5**: Search transactions by vendor.
- **6**: Perform a custom search by date range, description, and amount.

- ### Custom Search

The custom search allows you to filter transactions based on the following criteria:

- **Start Date** and **End Date**: Filter transactions within a specific date range.
- **Description**: Search for a specific keyword in the transaction description.
- **Amount**: Filter by exact transaction amount.

## Code Structure

### Transaction Class

The `Transaction` class represents a single financial transaction. It holds data about:

- **Date and Time**: Stored as a `LocalDateTime` object and formatted in both date (`yyyy-MM-dd`) and time (`hh:mm:ss`) strings.
- **Description**: A brief description of the transaction.
- **Vendor**: The person or company involved in the transaction.
- **Amount**: The amount for the transaction, where negative amounts represent debits (payments).

Methods:

- `toCSVFormat()`: Returns the transaction details in a pipe-separated string format for saving to CSV.
- `toString()`: Formats the transaction for display in a human-readable format.

### Ledger Class

The `Ledger` class is responsible for managing the collection of transactions. It provides methods to:

- **Add Deposits** and **Payments**: Create new transactions and add them to the ledger.
- **Display All Entries**: List all transactions in the ledger.
- **Filter by Date Range, Vendor, and Amount**: Generate reports by filtering the ledger based on specific criteria.

The ledger data is persisted in a CSV file, which is loaded when the application starts.

### Menus Class

The `Menus` class provides the user interface for interacting with the ledger. It handles user input, menu navigation, and delegating tasks to the `Ledger` class. Key features include:

- **Display Home Screen**: Manages the main menu where users can choose actions.
- **Ledger and Reports Menus**: Allows users to navigate between options and generate reports.
- **Input Validation**: Ensures that user inputs, such as amounts, are valid before proceeding.

## Transaction CSV Format

Transactions are stored in a CSV file with the following structure:

Date|Time|Description|Vendor|Amount

Ex.

2023-10-10|14:35:00|Deposit for services|John Doe|150.00 

2023-10-11|16:50:00|Payment for rent|Landlord|-850.00

- **Date**: The transaction date in `yyyy-MM-dd` format.
- **Time**: The transaction time in `hh:mm:ss` format.
- **Description**: A brief description of the transaction.
- **Vendor**: The person or company involved in the transaction.
- **Amount**: The transaction amount, where negative values represent payments (debits).
