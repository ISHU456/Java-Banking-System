package com.banking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Transaction class to represent individual banking transactions
 * Demonstrates encapsulation and data management
 */
public class Transaction {
    private static int transactionCounter = 1000;
    
    private final String transactionId;
    private final String accountNumber;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String description;
    private final double balanceAfterTransaction;
    
    /**
     * Enum for transaction types
     */
    public enum TransactionType {
        DEPOSIT("Deposit"),
        WITHDRAWAL("Withdrawal"),
        TRANSFER_IN("Transfer In"),
        TRANSFER_OUT("Transfer Out"),
        INTEREST_CREDIT("Interest Credit"),
        FEE_DEBIT("Fee Debit");
        
        private final String description;
        
        TransactionType(String description) {
            this.description = description;
        }
        
        @Override
        public String toString() {
            return description;
        }
    }
    
    /**
     * Constructor for creating a transaction
     * @param accountNumber The account number
     * @param type The transaction type
     * @param amount The transaction amount
     * @param description The transaction description
     * @param balanceAfterTransaction The account balance after this transaction
     */
    public Transaction(String accountNumber, TransactionType type, double amount, 
                      String description, double balanceAfterTransaction) {
        this.transactionId = "TXN" + (++transactionCounter);
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getter methods (encapsulation)
    public String getTransactionId() { 
        return transactionId; 
    }
    
    public String getAccountNumber() { 
        return accountNumber; 
    }
    
    public TransactionType getType() { 
        return type; 
    }
    
    public double getAmount() { 
        return amount; 
    }
    
    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public double getBalanceAfterTransaction() { 
        return balanceAfterTransaction; 
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%-10s | %-15s | %10.2f | %20s | %10.2f | %s", 
                           transactionId, type, amount, 
                           timestamp.format(formatter), balanceAfterTransaction, description);
    }
    
    /**
     * Returns a formatted string representation of the transaction
     * @return Formatted transaction string
     */
    public String getFormattedTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        return String.format("%s - %s: $%.2f (Balance: $%.2f) [%s]",
                           timestamp.format(formatter), type, amount, 
                           balanceAfterTransaction, description);
    }
}
