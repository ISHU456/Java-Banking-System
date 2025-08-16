package com.banking.model;

import com.banking.exception.InvalidAccountException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer class demonstrating encapsulation and composition
 * Manages customer information and their associated accounts
 */
public class Customer {
    private static int customerCounter = 1000;
    
    // Private fields demonstrating encapsulation
    private final String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private final LocalDateTime dateJoined;
    private boolean isActive;
    private final List<Account> accounts;
    
    /**
     * Constructor for Customer
     * @param firstName Customer's first name
     * @param lastName Customer's last name  
     * @param email Customer's email address
     * @throws InvalidAccountException if customer data is invalid
     */
    public Customer(String firstName, String lastName, String email) throws InvalidAccountException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidAccountException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidAccountException("Last name cannot be empty");
        }
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            throw new InvalidAccountException("Valid email address is required");
        }
        
        this.customerId = "CUST" + (++customerCounter);
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.email = email.trim().toLowerCase();
        this.dateJoined = LocalDateTime.now();
        this.isActive = true;
        this.accounts = new ArrayList<>();
    }
    
    // Getter methods (encapsulation)
    public String getCustomerId() { 
        return customerId; 
    }
    
    public String getFirstName() { 
        return firstName; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public String getFullName() { 
        return firstName + " " + lastName; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    public String getAddress() { 
        return address; 
    }
    
    public LocalDateTime getDateJoined() { 
        return dateJoined; 
    }
    
    public boolean isActive() { 
        return isActive; 
    }
    
    // Setter methods with validation
    public void setFirstName(String firstName) throws InvalidAccountException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidAccountException("First name cannot be empty");
        }
        this.firstName = firstName.trim();
    }
    
    public void setLastName(String lastName) throws InvalidAccountException {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidAccountException("Last name cannot be empty");
        }
        this.lastName = lastName.trim();
    }
    
    public void setEmail(String email) throws InvalidAccountException {
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            throw new InvalidAccountException("Valid email address is required");
        }
        this.email = email.trim().toLowerCase();
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;
    }
    
    public void setAddress(String address) {
        this.address = address != null ? address.trim() : null;
    }
    
    /**
     * Deactivate the customer and all associated accounts
     */
    public void deactivateCustomer() {
        this.isActive = false;
        // Also deactivate all accounts
        for (Account account : accounts) {
            account.deactivateAccount();
        }
    }
    
    /**
     * Activate the customer
     */
    public void activateCustomer() {
        this.isActive = true;
    }
    
    // Account management methods
    public void addAccount(Account account) {
        if (account != null && !accounts.contains(account)) {
            accounts.add(account);
        }
    }
    
    public boolean removeAccount(Account account) {
        return accounts.remove(account);
    }
    
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts); // Return defensive copy
    }
    
    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    
    public List<Account> getActiveAccounts() {
        List<Account> activeAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.isActive()) {
                activeAccounts.add(account);
            }
        }
        return activeAccounts;
    }
    
    /**
     * Calculate total balance across all accounts
     * @return Total balance across all customer accounts
     */
    public double getTotalBalance() {
        double totalBalance = 0.0;
        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }
    
    public int getAccountCount() {
        return accounts.size();
    }
    
    public int getActiveAccountCount() {
        return getActiveAccounts().size();
    }
    
    /**
     * Get accounts by type
     * @param accountType The type of account to search for
     * @return List of accounts of the specified type
     */
    public List<Account> getAccountsByType(String accountType) {
        List<Account> accountsByType = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getAccountType().equals(accountType)) {
                accountsByType.add(account);
            }
        }
        return accountsByType;
    }
    
    // Utility methods
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && 
               email.indexOf("@") < email.lastIndexOf(".") &&
               email.indexOf("@") > 0 &&
               email.lastIndexOf(".") < email.length() - 1;
    }
    
    @Override
    public String toString() {
        return String.format("Customer: %s | Name: %s | Email: %s | Accounts: %d | Status: %s",
                           customerId, getFullName(), email, accounts.size(), 
                           isActive ? "Active" : "Inactive");
    }
    
    /**
     * Get comprehensive customer summary
     * @return Formatted customer summary string
     */
    public String getCustomerSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== Customer Summary ===\n");
        summary.append(String.format("Customer ID: %s\n", customerId));
        summary.append(String.format("Name: %s\n", getFullName()));
        summary.append(String.format("Email: %s\n", email));
        if (phoneNumber != null) {
            summary.append(String.format("Phone: %s\n", phoneNumber));
        }
        if (address != null) {
            summary.append(String.format("Address: %s\n", address));
        }
        summary.append(String.format("Date Joined: %s\n", dateJoined.toLocalDate()));
        summary.append(String.format("Status: %s\n", isActive ? "Active" : "Inactive"));
        summary.append(String.format("Total Accounts: %d\n", accounts.size()));
        summary.append(String.format("Active Accounts: %d\n", getActiveAccountCount()));
        summary.append(String.format("Total Balance: $%.2f\n", getTotalBalance()));
        
        if (!accounts.isEmpty()) {
            summary.append("\n--- Accounts ---\n");
            for (Account account : accounts) {
                summary.append(String.format("• %s\n", account));
            }
        }
        
        return summary.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return customerId.equals(customer.customerId);
    }
    
    @Override
    public int hashCode() {
        return customerId.hashCode();
    }
}
