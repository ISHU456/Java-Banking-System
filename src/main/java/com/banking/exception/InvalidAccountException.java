package com.banking.exception;

/**
 * Exception thrown for invalid account operations or data
 */
public class InvalidAccountException extends Exception {
    public InvalidAccountException(String message) {
        super(message);
    }
    
    public InvalidAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
