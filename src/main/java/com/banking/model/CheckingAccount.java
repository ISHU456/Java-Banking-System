package com.banking.model;

import com.banking.exception.InvalidAccountException;
import com.banking.exception.InvalidTransactionException;
import com.banking.exception.InsufficientFundsException;

/**
 * CheckingAccount class demonstrating inheritance and polymorphism
 * Extends the abstract Account class with specific checking account behavior
 */
public class CheckingAccount extends Account {
    // Class constants for business rules
    private static final double MINIMUM_BALANCE = 25.00;
    private static final double MONTHLY_MAINTENANCE_FEE = 10.00;
    private static final double OVERDRAFT_FEE = 35.00;
    private static final double OVERDRAFT_LIMIT = 500.00;
    private static final double MAINTENANCE_FEE_WAIVER_BALANCE = 1000.00;
    private static final double PREMIUM_INTEREST_THRESHOLD = 5000.00;
    private static final double PREMIUM_INTEREST_RATE = 0.001; // 0.1% annual
    
    // Instance variables for account-specific state
    private boolean overdraftProtection;
    private int checksWrittenThisMonth;
    
    /**
     * Constructor for CheckingAccount with default overdraft protection
     * @param accountHolderName Name of the account holder
     * @param initialBalance Initial balance for the account
     * @throws InvalidAccountException if account data is invalid
     */
    public CheckingAccount(String accountHolderName, double initialBalance) throws InvalidAccountException {
        this(accountHolderName, initialBalance, true); // Default with overdraft protection
    }
    
    /**
     * Constructor for CheckingAccount with specified overdraft protection
     * @param accountHolderName Name of the account holder
     * @param initialBalance Initial balance for the account
     * @param overdraftProtection Whether overdraft protection is enabled
     * @throws InvalidAccountException if account data is invalid
     */
    public CheckingAccount(String accountHolderName, double initialBalance, boolean overdraftProtection) 
            throws InvalidAccountException {
        super(accountHolderName, Math.max(initialBalance, MINIMUM_BALANCE));
        this.overdraftProtection = overdraftProtection;
        this.checksWrittenThisMonth = 0;
        
        // If initial balance was less than minimum, add the difference
        if (initialBalance < MINIMUM_BALANCE) {
            double difference = MINIMUM_BALANCE - initialBalance;
            super.performDeposit(difference);
            addTransaction(Transaction.TransactionType.DEPOSIT, difference, 
                         "Minimum balance requirement deposit");
        }
    }
    
    @Override
    public String getAccountType() {
        return "Checking Account";
    }
    
    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
    
    @Override
    public boolean canWithdraw(double amount) {
        double balanceAfterWithdrawal = balance - amount;
        
        if (balanceAfterWithdrawal >= 0) {
            return true; // Sufficient funds
        }
        
        // Check overdraft protection
        if (overdraftProtection) {
            double overdraftAmount = Math.abs(balanceAfterWithdrawal);
            return overdraftAmount <= OVERDRAFT_LIMIT;
        }
        
        return false; // No overdraft protection and insufficient funds
    }
    
    @Override
    protected void performWithdrawal(double amount) {
        double balanceBeforeWithdrawal = balance;
        super.performWithdrawal(amount);
        
        // Check if withdrawal caused overdraft
        if (balanceBeforeWithdrawal >= 0 && balance < 0 && overdraftProtection) {
            addFeeTransaction(OVERDRAFT_FEE, "Overdraft fee");
        }
    }
    
    @Override
    public void applyMonthlyMaintenance() {
        // Reset monthly counters
        checksWrittenThisMonth = 0;
        
        // Apply monthly maintenance fee if balance is below waiver threshold
        if (balance < MAINTENANCE_FEE_WAIVER_BALANCE) {
            if (balance >= MONTHLY_MAINTENANCE_FEE) {
                addFeeTransaction(MONTHLY_MAINTENANCE_FEE, "Monthly maintenance fee");
            } else if (balance > 0) {
                // Partial fee if balance is insufficient for full fee
                addFeeTransaction(balance, "Partial monthly maintenance fee");
            }
        }
        
        // Premium accounts earn interest on high balances
        if (balance > PREMIUM_INTEREST_THRESHOLD) {
            double monthlyInterest = balance * (PREMIUM_INTEREST_RATE / 12);
            if (monthlyInterest >= 0.01) {
                addInterestTransaction(monthlyInterest);
            }
        }
    }
    
    /**
     * Method specific to checking accounts - write a check
     * @param amount Amount of the check
     * @param payee Who the check is written to
     * @throws InsufficientFundsException if insufficient funds
     * @throws InvalidTransactionException if transaction is invalid
     */
    public void writeCheck(double amount, String payee) 
            throws InsufficientFundsException, InvalidTransactionException {
        validateTransactionAmount(amount);
        validateWithdrawal(amount);
        performWithdrawal(amount);
        checksWrittenThisMonth++;
        addTransaction(Transaction.TransactionType.WITHDRAWAL, amount, "Check written to " + payee);
    }
    
    // Checking account specific getter methods
    public boolean isOverdraftProtectionEnabled() {
        return overdraftProtection;
    }
    
    public void enableOverdraftProtection() {
        this.overdraftProtection = true;
    }
    
    public void disableOverdraftProtection() {
        this.overdraftProtection = false;
    }
    
    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }
    
    public double getOverdraftFee() {
        return OVERDRAFT_FEE;
    }
    
    public double getAvailableOverdraft() {
        if (!overdraftProtection) return 0.00;
        
        if (balance >= 0) {
            return OVERDRAFT_LIMIT;
        } else {
            return Math.max(0, OVERDRAFT_LIMIT + balance); // balance is negative
        }
    }
    
    public int getChecksWrittenThisMonth() {
        return checksWrittenThisMonth;
    }
    
    public boolean isOverdrawn() {
        return balance < 0;
    }
    
    public double getMonthlyMaintenanceFee() {
        return MONTHLY_MAINTENANCE_FEE;
    }
    
    public double getMaintenanceFeeWaiverBalance() {
        return MAINTENANCE_FEE_WAIVER_BALANCE;
    }
    
    @Override
    public String getAccountSummary() {
        StringBuilder summary = new StringBuilder(super.getAccountSummary());
        summary.append(String.format("Overdraft Protection: %s\n", overdraftProtection ? "Enabled" : "Disabled"));
        if (overdraftProtection) {
            summary.append(String.format("Overdraft Limit: $%.2f\n", OVERDRAFT_LIMIT));
            summary.append(String.format("Available Overdraft: $%.2f\n", getAvailableOverdraft()));
            summary.append(String.format("Overdraft Fee: $%.2f\n", OVERDRAFT_FEE));
        }
        summary.append(String.format("Monthly Maintenance Fee: $%.2f\n", MONTHLY_MAINTENANCE_FEE));
        summary.append(String.format("Fee Waiver Balance: $%.2f\n", MAINTENANCE_FEE_WAIVER_BALANCE));
        summary.append(String.format("Checks Written This Month: %d\n", checksWrittenThisMonth));
        if (isOverdrawn()) {
            summary.append(String.format("*** ACCOUNT OVERDRAWN BY $%.2f ***\n", Math.abs(balance)));
        }
        return summary.toString();
    }
}
