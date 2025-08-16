package com.banking.model;

import com.banking.exception.InvalidAccountException;
import com.banking.exception.InvalidTransactionException;
import com.banking.exception.InsufficientFundsException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Abstract Account class demonstrating abstraction and encapsulation
 * Base class for all types of bank accounts
 */
public abstract class Account {
    protected static int accountCounter = 100000;
    
    // Private fields demonstrating encapsulation
    private final String accountNumber;
    private final String accountHolderName;
    private final LocalDateTime dateOpened;
    protected double balance;
    private boolean isActive;
    private final List<Transaction> transactionHistory;
    
    /**
     * Protected constructor for inheritance
     * @param accountHolderName Name of the account holder
     * @param initialBalance Initial balance for the account
     * @throws InvalidAccountException if account data is invalid
     */
    protected Account(String accountHolderName, double initialBalance) throws InvalidAccountException {
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new InvalidAccountException("Account holder name cannot be empty");
        }
        if (initialBalance < 0) {
            throw new InvalidAccountException("Initial balance cannot be negative");
        }
        
        this.accountNumber = generateAccountNumber();
        this.accountHolderName = accountHolderName.trim();
        this.balance = initialBalance;
        this.dateOpened = LocalDateTime.now();
        this.isActive = true;
        this.transactionHistory = new ArrayList<>();
        
        // Add initial deposit transaction if balance > 0
        if (initialBalance > 0) {
            addTransaction(Transaction.TransactionType.DEPOSIT, initialBalance, "Initial deposit");
        }
    }
    
    // Abstract methods to be implemented by subclasses (demonstrating abstraction)
    public abstract String getAccountType();
    public abstract double getMinimumBalance();
    public abstract boolean canWithdraw(double amount);
    public abstract void applyMonthlyMaintenance();
    
    // Template method pattern - defines the algorithm structure
    public final void deposit(double amount) throws InvalidTransactionException {
        validateTransactionAmount(amount);
        performDeposit(amount);
        addTransaction(Transaction.TransactionType.DEPOSIT, amount, "Cash deposit");
    }
    
    public final void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException {
        validateTransactionAmount(amount);
        validateWithdrawal(amount);
        performWithdrawal(amount);
        addTransaction(Transaction.TransactionType.WITHDRAWAL, amount, "Cash withdrawal");
    }
    
    // Protected methods for subclasses to override if needed
    protected void performDeposit(double amount) {
        balance += amount;
    }
    
    protected void performWithdrawal(double amount) {
        balance -= amount;
    }
    
    protected void validateWithdrawal(double amount) throws InsufficientFundsException {
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException(amount, balance);
        }
    }
    
    protected void validateTransactionAmount(double amount) throws InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException("Transaction amount must be positive");
        }
        if (!isActive) {
            throw new InvalidTransactionException("Account is not active");
        }
    }
    
    // Internal transfer methods
    public void transferOut(double amount, String toAccount) throws InsufficientFundsException, InvalidTransactionException {
        validateTransactionAmount(amount);
        validateWithdrawal(amount);
        performWithdrawal(amount);
        addTransaction(Transaction.TransactionType.TRANSFER_OUT, amount, "Transfer to " + toAccount);
    }
    
    public void transferIn(double amount, String fromAccount) throws InvalidTransactionException {
        validateTransactionAmount(amount);
        performDeposit(amount);
        addTransaction(Transaction.TransactionType.TRANSFER_IN, amount, "Transfer from " + fromAccount);
    }
    
    protected void addTransaction(Transaction.TransactionType type, double amount, String description) {
        Transaction transaction = new Transaction(accountNumber, type, amount, description, balance);
        transactionHistory.add(transaction);
    }
    
    protected void addInterestTransaction(double interestAmount) {
        if (interestAmount > 0) {
            balance += interestAmount;
            addTransaction(Transaction.TransactionType.INTEREST_CREDIT, interestAmount, "Monthly interest credit");
        }
    }
    
    protected void addFeeTransaction(double feeAmount, String description) {
        if (feeAmount > 0) {
            balance -= feeAmount;
            addTransaction(Transaction.TransactionType.FEE_DEBIT, feeAmount, description);
        }
    }
    
    // Getter methods (encapsulation)
    public String getAccountNumber() { 
        return accountNumber; 
    }
    
    public String getAccountHolderName() { 
        return accountHolderName; 
    }
    
    public double getBalance() { 
        return balance; 
    }
    
    public LocalDateTime getDateOpened() { 
        return dateOpened; 
    }
    
    public boolean isActive() { 
        return isActive; 
    }
    
    public void deactivateAccount() { 
        this.isActive = false; 
    }
    
    public void activateAccount() { 
        this.isActive = true; 
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // Return defensive copy
    }
    
    public List<Transaction> getRecentTransactions(int count) {
        List<Transaction> recent = new ArrayList<>();
        int start = Math.max(0, transactionHistory.size() - count);
        for (int i = start; i < transactionHistory.size(); i++) {
            recent.add(transactionHistory.get(i));
        }
        return recent;
    }
    
    private String generateAccountNumber() {
        return String.valueOf(++accountCounter);
    }
    
    @Override
    public String toString() {
        return String.format("Account: %s | Type: %s | Holder: %s | Balance: $%.2f | Status: %s",
                           accountNumber, getAccountType(), accountHolderName, balance, 
                           isActive ? "Active" : "Inactive");
    }
    
    public String getAccountSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== Account Summary ===\n");
        summary.append(String.format("Account Number: %s\n", accountNumber));
        summary.append(String.format("Account Type: %s\n", getAccountType()));
        summary.append(String.format("Account Holder: %s\n", accountHolderName));
        summary.append(String.format("Current Balance: $%.2f\n", balance));
        summary.append(String.format("Minimum Balance: $%.2f\n", getMinimumBalance()));
        summary.append(String.format("Account Status: %s\n", isActive ? "Active" : "Inactive"));
        summary.append(String.format("Date Opened: %s\n", dateOpened.toLocalDate()));
        summary.append(String.format("Total Transactions: %d\n", transactionHistory.size()));
        return summary.toString();
    }
}
