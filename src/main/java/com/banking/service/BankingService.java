package com.banking.service;

import com.banking.model.*;
import com.banking.exception.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * BankingService class demonstrating composition, aggregation, and high-level banking operations
 * Manages customers, accounts, and provides banking services
 */
public class BankingService {
    private final String bankName;
    private final String bankCode;
    private final Map<String, Customer> customers;
    private final Map<String, Account> accounts;
    
    /**
     * Constructor for BankingService
     * @param bankName Name of the bank
     * @param bankCode Bank's unique identifier code
     */
    public BankingService(String bankName, String bankCode) {
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
    }
    
    /**
     * Create a new customer
     * @param firstName Customer's first name
     * @param lastName Customer's last name
     * @param email Customer's email address
     * @return The created Customer object
     * @throws InvalidAccountException if customer data is invalid or email already exists
     */
    public Customer createCustomer(String firstName, String lastName, String email) 
            throws InvalidAccountException {
        // Check if customer already exists with same email
        for (Customer existingCustomer : customers.values()) {
            if (existingCustomer.getEmail().equalsIgnoreCase(email)) {
                throw new InvalidAccountException("Customer with email " + email + " already exists");
            }
        }
        
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }
    
    /**
     * Get customer by ID
     * @param customerId The customer ID
     * @return The Customer object
     * @throws AccountNotFoundException if customer not found
     */
    public Customer getCustomer(String customerId) throws AccountNotFoundException {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new AccountNotFoundException("Customer not found: " + customerId);
        }
        return customer;
    }
    
    /**
     * Get all customers
     * @return List of all customers
     */
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    /**
     * Get all active customers
     * @return List of active customers
     */
    public List<Customer> getActiveCustomers() {
        List<Customer> activeCustomers = new ArrayList<>();
        for (Customer customer : customers.values()) {
            if (customer.isActive()) {
                activeCustomers.add(customer);
            }
        }
        return activeCustomers;
    }
    
    /**
     * Create a savings account for a customer
     * @param customerId The customer ID
     * @param initialBalance Initial balance for the account
     * @return The created SavingsAccount
     * @throws InvalidAccountException if account data is invalid
     * @throws AccountNotFoundException if customer not found
     */
    public SavingsAccount createSavingsAccount(String customerId, double initialBalance) 
            throws InvalidAccountException, AccountNotFoundException {
        Customer customer = getCustomer(customerId);
        
        SavingsAccount account = new SavingsAccount(customer.getFullName(), initialBalance);
        customer.addAccount(account);
        accounts.put(account.getAccountNumber(), account);
        
        return account;
    }
    
    /**
     * Create a checking account for a customer with specified overdraft protection
     * @param customerId The customer ID
     * @param initialBalance Initial balance for the account
     * @param overdraftProtection Whether overdraft protection is enabled
     * @return The created CheckingAccount
     * @throws InvalidAccountException if account data is invalid
     * @throws AccountNotFoundException if customer not found
     */
    public CheckingAccount createCheckingAccount(String customerId, double initialBalance, 
                                               boolean overdraftProtection) 
            throws InvalidAccountException, AccountNotFoundException {
        Customer customer = getCustomer(customerId);
        
        CheckingAccount account = new CheckingAccount(customer.getFullName(), initialBalance, overdraftProtection);
        customer.addAccount(account);
        accounts.put(account.getAccountNumber(), account);
        
        return account;
    }
    
    /**
     * Create a checking account for a customer with default overdraft protection
     * @param customerId The customer ID
     * @param initialBalance Initial balance for the account
     * @return The created CheckingAccount
     * @throws InvalidAccountException if account data is invalid
     * @throws AccountNotFoundException if customer not found
     */
    public CheckingAccount createCheckingAccount(String customerId, double initialBalance) 
            throws InvalidAccountException, AccountNotFoundException {
        return createCheckingAccount(customerId, initialBalance, true);
    }
    
    /**
     * Get account by account number
     * @param accountNumber The account number
     * @return The Account object
     * @throws AccountNotFoundException if account not found
     */
    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }
    
    /**
     * Get all accounts
     * @return List of all accounts
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
    
    /**
     * Get all active accounts
     * @return List of active accounts
     */
    public List<Account> getActiveAccounts() {
        List<Account> activeAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.isActive()) {
                activeAccounts.add(account);
            }
        }
        return activeAccounts;
    }
    
    /**
     * Deposit money into an account
     * @param accountNumber The account number
     * @param amount The amount to deposit
     * @throws AccountNotFoundException if account not found
     * @throws InvalidTransactionException if transaction is invalid
     */
    public void deposit(String accountNumber, double amount) 
            throws AccountNotFoundException, InvalidTransactionException {
        Account account = getAccount(accountNumber);
        account.deposit(amount);
    }
    
    /**
     * Withdraw money from an account
     * @param accountNumber The account number
     * @param amount The amount to withdraw
     * @throws AccountNotFoundException if account not found
     * @throws InsufficientFundsException if insufficient funds
     * @throws InvalidTransactionException if transaction is invalid
     */
    public void withdraw(String accountNumber, double amount) 
            throws AccountNotFoundException, InsufficientFundsException, InvalidTransactionException {
        Account account = getAccount(accountNumber);
        account.withdraw(amount);
    }
    
    /**
     * Transfer money between accounts
     * @param fromAccountNumber Source account number
     * @param toAccountNumber Destination account number
     * @param amount Amount to transfer
     * @throws AccountNotFoundException if either account not found
     * @throws InsufficientFundsException if insufficient funds in source account
     * @throws InvalidTransactionException if transaction is invalid
     */
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) 
            throws AccountNotFoundException, InsufficientFundsException, InvalidTransactionException {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new InvalidTransactionException("Cannot transfer to the same account");
        }
        
        Account fromAccount = getAccount(fromAccountNumber);
        Account toAccount = getAccount(toAccountNumber);
        
        // Perform transfer
        fromAccount.transferOut(amount, toAccountNumber);
        toAccount.transferIn(amount, fromAccountNumber);
    }
    
    /**
     * Get account balance
     * @param accountNumber The account number
     * @return The account balance
     * @throws AccountNotFoundException if account not found
     */
    public double getAccountBalance(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        return account.getBalance();
    }
    
    /**
     * Write a check from a checking account
     * @param accountNumber The checking account number
     * @param amount The check amount
     * @param payee Who the check is written to
     * @throws AccountNotFoundException if account not found
     * @throws InsufficientFundsException if insufficient funds
     * @throws InvalidTransactionException if account is not a checking account or transaction is invalid
     */
    public void writeCheck(String accountNumber, double amount, String payee) 
            throws AccountNotFoundException, InsufficientFundsException, InvalidTransactionException {
        Account account = getAccount(accountNumber);
        if (!(account instanceof CheckingAccount)) {
            throw new InvalidTransactionException("Check writing is only available for checking accounts");
        }
        
        CheckingAccount checkingAccount = (CheckingAccount) account;
        checkingAccount.writeCheck(amount, payee);
    }
    
    /**
     * Apply monthly maintenance to all active accounts
     */
    public void applyMonthlyMaintenanceToAllAccounts() {
        for (Account account : accounts.values()) {
            if (account.isActive()) {
                account.applyMonthlyMaintenance();
            }
        }
    }
    
    /**
     * Apply monthly maintenance to a specific account
     * @param accountNumber The account number
     * @throws AccountNotFoundException if account not found
     */
    public void applyMonthlyMaintenanceToAccount(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        account.applyMonthlyMaintenance();
    }
    
    /**
     * Deactivate an account
     * @param accountNumber The account number
     * @throws AccountNotFoundException if account not found
     */
    public void deactivateAccount(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        account.deactivateAccount();
    }
    
    /**
     * Activate an account
     * @param accountNumber The account number
     * @throws AccountNotFoundException if account not found
     */
    public void activateAccount(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        account.activateAccount();
    }
    
    /**
     * Get total balance across all accounts in the bank
     * @return Total bank balance
     */
    public double getTotalBankBalance() {
        double totalBalance = 0.0;
        for (Account account : accounts.values()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }
    
    /**
     * Get total number of customers
     * @return Total customer count
     */
    public int getTotalCustomerCount() {
        return customers.size();
    }
    
    /**
     * Get number of active customers
     * @return Active customer count
     */
    public int getActiveCustomerCount() {
        return getActiveCustomers().size();
    }
    
    /**
     * Get total number of accounts
     * @return Total account count
     */
    public int getTotalAccountCount() {
        return accounts.size();
    }
    
    /**
     * Get number of active accounts
     * @return Active account count
     */
    public int getActiveAccountCount() {
        return getActiveAccounts().size();
    }
    
    /**
     * Get count of accounts by type
     * @return Map of account type to count
     */
    public Map<String, Integer> getAccountTypeCounts() {
        Map<String, Integer> typeCounts = new HashMap<>();
        
        for (Account account : accounts.values()) {
            String type = account.getAccountType();
            typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
        }
        
        return typeCounts;
    }
    
    // Bank information getters
    public String getBankName() { 
        return bankName; 
    }
    
    public String getBankCode() { 
        return bankCode; 
    }
    
    @Override
    public String toString() {
        return String.format("Bank: %s (%s) | Customers: %d | Accounts: %d | Total Balance: $%.2f",
                           bankName, bankCode, customers.size(), accounts.size(), getTotalBankBalance());
    }
    
    /**
     * Get comprehensive bank summary
     * @return Formatted bank summary string
     */
    public String getBankSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== Bank Summary ===\n");
        summary.append(String.format("Bank Name: %s\n", bankName));
        summary.append(String.format("Bank Code: %s\n", bankCode));
        summary.append(String.format("Total Customers: %d\n", getTotalCustomerCount()));
        summary.append(String.format("Active Customers: %d\n", getActiveCustomerCount()));
        summary.append(String.format("Total Accounts: %d\n", getTotalAccountCount()));
        summary.append(String.format("Active Accounts: %d\n", getActiveAccountCount()));
        summary.append(String.format("Total Bank Balance: $%.2f\n", getTotalBankBalance()));
        
        Map<String, Integer> typeCounts = getAccountTypeCounts();
        if (!typeCounts.isEmpty()) {
            summary.append("\n--- Account Types ---\n");
            for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
                summary.append(String.format("• %s: %d accounts\n", entry.getKey(), entry.getValue()));
            }
        }
        
        return summary.toString();
    }
}
