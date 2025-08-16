package com.banking.model;

import com.banking.exception.InvalidAccountException;

/**
 * SavingsAccount class demonstrating inheritance and polymorphism
 * Extends the abstract Account class with specific savings account behavior
 */
public class SavingsAccount extends Account {
    // Class constants for business rules
    private static final double MINIMUM_BALANCE = 100.00;
    private static final double INTEREST_RATE = 0.035; // 3.5% annual interest
    private static final double MONTHLY_MAINTENANCE_FEE = 5.00;
    private static final int FREE_WITHDRAWALS_PER_MONTH = 6;
    private static final double EXCESS_WITHDRAWAL_FEE = 2.00;
    private static final double MAINTENANCE_FEE_WAIVER_BALANCE = 500.00;
    
    // Instance variables for account-specific state
    private int withdrawalsThisMonth;
    
    /**
     * Constructor for SavingsAccount
     * @param accountHolderName Name of the account holder
     * @param initialBalance Initial balance for the account
     * @throws InvalidAccountException if account data is invalid
     */
    public SavingsAccount(String accountHolderName, double initialBalance) throws InvalidAccountException {
        super(accountHolderName, Math.max(initialBalance, MINIMUM_BALANCE));
        this.withdrawalsThisMonth = 0;
        
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
        return "Savings Account";
    }
    
    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
    
    @Override
    public boolean canWithdraw(double amount) {
        double balanceAfterWithdrawal = balance - amount;
        
        // Check if withdrawal would go below minimum balance
        if (balanceAfterWithdrawal < MINIMUM_BALANCE) {
            return false;
        }
        
        // Allow withdrawal if within free withdrawal limit or sufficient balance for fees
        if (withdrawalsThisMonth < FREE_WITHDRAWALS_PER_MONTH) {
            return true;
        } else {
            // Additional withdrawal fee applies
            return balanceAfterWithdrawal >= (MINIMUM_BALANCE + EXCESS_WITHDRAWAL_FEE);
        }
    }
    
    @Override
    protected void performWithdrawal(double amount) {
        super.performWithdrawal(amount);
        withdrawalsThisMonth++;
        
        // Apply withdrawal fee if over free limit
        if (withdrawalsThisMonth > FREE_WITHDRAWALS_PER_MONTH) {
            addFeeTransaction(EXCESS_WITHDRAWAL_FEE, "Excess withdrawal fee");
        }
    }
    
    @Override
    public void applyMonthlyMaintenance() {
        // Reset withdrawal counter
        withdrawalsThisMonth = 0;
        
        // Apply monthly maintenance fee if balance is below threshold
        if (balance < MAINTENANCE_FEE_WAIVER_BALANCE && balance >= MONTHLY_MAINTENANCE_FEE) {
            addFeeTransaction(MONTHLY_MAINTENANCE_FEE, "Monthly maintenance fee");
        }
        
        // Apply monthly interest
        double monthlyInterest = balance * (INTEREST_RATE / 12);
        if (monthlyInterest >= 0.01) { // Only apply if at least 1 cent
            addInterestTransaction(monthlyInterest);
        }
    }
    
    // Savings account specific methods
    public double getInterestRate() {
        return INTEREST_RATE;
    }
    
    public int getWithdrawalsThisMonth() {
        return withdrawalsThisMonth;
    }
    
    public int getRemainingFreeWithdrawals() {
        return Math.max(0, FREE_WITHDRAWALS_PER_MONTH - withdrawalsThisMonth);
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
        summary.append(String.format("Interest Rate: %.2f%% annually\n", INTEREST_RATE * 100));
        summary.append(String.format("Monthly Maintenance Fee: $%.2f\n", MONTHLY_MAINTENANCE_FEE));
        summary.append(String.format("Fee Waiver Balance: $%.2f\n", MAINTENANCE_FEE_WAIVER_BALANCE));
        summary.append(String.format("Withdrawals This Month: %d\n", withdrawalsThisMonth));
        summary.append(String.format("Free Withdrawals Remaining: %d\n", getRemainingFreeWithdrawals()));
        return summary.toString();
    }
}
