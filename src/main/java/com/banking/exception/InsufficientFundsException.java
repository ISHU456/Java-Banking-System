package com.banking.exception;

/**
 * Exception thrown when there are insufficient funds for a transaction
 */
public class InsufficientFundsException extends Exception {
    private final double requestedAmount;
    private final double availableBalance;
    
    public InsufficientFundsException(double requestedAmount, double availableBalance) {
        super("Insufficient funds. Requested: $" + String.format("%.2f", requestedAmount) + 
              ", Available: $" + String.format("%.2f", availableBalance));
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }
    
    public double getRequestedAmount() { 
        return requestedAmount; 
    }
    
    public double getAvailableBalance() { 
        return availableBalance; 
    }
}
