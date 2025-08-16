package com.banking.exception;

/**
 * Exception thrown when an account is not found
 */
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String accountNumber) {
        super("Account not found: " + accountNumber);
    }
    
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
