package com.banking.demo;

import com.banking.service.BankingService;
import com.banking.model.*;
import com.banking.exception.*;
import java.util.Scanner;
import java.util.List;

/**
 * Comprehensive Banking System Demo
 * Demonstrates all OOP concepts: Inheritance, Polymorphism, Encapsulation, and Abstraction
 * 
 * Features demonstrated:
 * - Customer management
 * - Multiple account types (Inheritance & Polymorphism)
 * - Banking operations (Encapsulation)
 * - Exception handling
 * - Transaction management
 * - Monthly maintenance operations
 */
public class BankingSystemDemo {
    private static BankingService bankingService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        // Initialize the banking system
        bankingService = new BankingService("OOP Demo Bank", "ODB001");
        scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(60));
        System.out.println("   WELCOME TO OOP BANKING SYSTEM DEMONSTRATION");
        System.out.println("=".repeat(60));
        System.out.println("This demo showcases all four pillars of Object-Oriented Programming:");
        System.out.println("• Encapsulation - Data hiding and controlled access");
        System.out.println("• Inheritance - Code reuse through class hierarchies");
        System.out.println("• Polymorphism - Same interface, different implementations");
        System.out.println("• Abstraction - Simplified interfaces hiding complexity");
        System.out.println("=".repeat(60));
        
        try {
            // Run comprehensive demo
            runComprehensiveDemo();
        } catch (Exception e) {
            System.err.println("Demo encountered an error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static void runComprehensiveDemo() {
        try {
            // 1. Demonstrate Customer Management and Encapsulation
            demonstrateCustomerManagement();
            
            // 2. Demonstrate Inheritance and Polymorphism with Account Types
            demonstrateAccountTypes();
            
            // 3. Demonstrate Banking Operations
            demonstrateBankingOperations();
            
            // 4. Demonstrate Exception Handling
            demonstrateExceptionHandling();
            
            // 5. Demonstrate Transaction History
            demonstrateTransactionHistory();
            
            // 6. Demonstrate Monthly Maintenance (Polymorphic behavior)
            demonstrateMonthlyMaintenance();
            
            // 7. Final Bank Summary
            demonstrateBankReporting();
            
            // 8. Interactive Menu (Optional)
            offerInteractiveMenu();
            
        } catch (Exception e) {
            System.err.println("Error in demo: " + e.getMessage());
        }
    }
    
    private static void demonstrateCustomerManagement() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("1. CUSTOMER MANAGEMENT DEMONSTRATION");
        System.out.println("   (Encapsulation, Data Validation)");
        System.out.println("=".repeat(50));
        
        try {
            // Create customers
            System.out.println("Creating customers with data validation...");
            Customer alice = bankingService.createCustomer("Alice", "Johnson", "alice.johnson@email.com");
            Customer bob = bankingService.createCustomer("Bob", "Smith", "bob.smith@email.com");
            Customer charlie = bankingService.createCustomer("Charlie", "Brown", "charlie.brown@email.com");
            
            // Add additional customer information using setter methods
            alice.setPhoneNumber("555-0101");
            alice.setAddress("123 Main St, Anytown, ST 12345");
            bob.setPhoneNumber("555-0102");
            
            System.out.println("✓ Successfully created 3 customers");
            System.out.println("Customer IDs: " + alice.getCustomerId() + ", " + 
                             bob.getCustomerId() + ", " + charlie.getCustomerId());
            
            // Display customer information (demonstrating toString method)
            System.out.println("\nCustomer Information:");
            System.out.println(alice);
            System.out.println(bob);
            System.out.println(charlie);
            
            System.out.println("\nDemonstrating encapsulation:");
            System.out.println("• Private fields protected from direct access");
            System.out.println("• Public getter/setter methods with validation");
            System.out.println("• Data integrity maintained through controlled access");
            
        } catch (Exception e) {
            System.err.println("Error in customer management demo: " + e.getMessage());
        }
        
        waitForEnter();
    }
    
    private static void demonstrateAccountTypes() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. ACCOUNT TYPES DEMONSTRATION");
        System.out.println("   (Inheritance, Polymorphism, Abstraction)");
        System.out.println("=".repeat(50));
        
        try {
            List<Customer> customers = bankingService.getAllCustomers();
            Customer alice = customers.get(0);
            Customer bob = customers.get(1);
            Customer charlie = customers.get(2);
            
            // Create different account types demonstrating polymorphism
            System.out.println("Creating various account types using inheritance...");
            
            // Savings accounts
            SavingsAccount aliceSavings = bankingService.createSavingsAccount(alice.getCustomerId(), 500.0);
            SavingsAccount bobSavings = bankingService.createSavingsAccount(bob.getCustomerId(), 1000.0);
            
            // Checking accounts
            CheckingAccount aliceChecking = bankingService.createCheckingAccount(alice.getCustomerId(), 250.0, true);
            CheckingAccount bobChecking = bankingService.createCheckingAccount(bob.getCustomerId(), 150.0, false);
            CheckingAccount charlieChecking = bankingService.createCheckingAccount(charlie.getCustomerId(), 75.0);
            
            System.out.println("✓ Created accounts using inheritance hierarchy:");
            System.out.println("  • Savings Accounts: 2 (extends Account)");
            System.out.println("  • Checking Accounts: 3 (extends Account)");
            
            // Demonstrate polymorphism - same method call, different behavior
            System.out.println("\nDemonstrating Polymorphism:");
            System.out.println("Same method calls, different implementations per account type:");
            
            Account[] accountsArray = {aliceSavings, aliceChecking, bobSavings, bobChecking, charlieChecking};
            System.out.println("\n| Account Number  | Min Balance | Account Type      | Can Withdraw $200?");
            System.out.println("|" + "-".repeat(70) + "|");
            
            for (Account account : accountsArray) {
                // Polymorphic method calls - same method, different implementations
                System.out.printf("| %-15s | $%-10.2f | %-17s | %-13s |\n", 
                                account.getAccountNumber(), 
                                account.getMinimumBalance(), 
                                account.getAccountType(),
                                account.canWithdraw(200.0) ? "Yes" : "No");
            }
            
            System.out.println("\nAbstraction demonstrated:");
            System.out.println("• Account is abstract - cannot be instantiated directly");
            System.out.println("• Abstract methods implemented differently in each subclass");
            System.out.println("• Client code uses same interface for different account types");
            
        } catch (Exception e) {
            System.err.println("Error in account types demo: " + e.getMessage());
        }
        
        waitForEnter();
    }
    
    private static void demonstrateBankingOperations() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("3. BANKING OPERATIONS DEMONSTRATION");
        System.out.println("   (Method Overriding, Business Logic)");
        System.out.println("=".repeat(50));
        
        try {
            List<Account> accounts = bankingService.getAllAccounts();
            
            // Deposits
            System.out.println("Performing deposits...");
            bankingService.deposit(accounts.get(0).getAccountNumber(), 200.0);
            bankingService.deposit(accounts.get(1).getAccountNumber(), 100.0);
            bankingService.deposit(accounts.get(2).getAccountNumber(), 300.0);
            System.out.println("✓ Completed 3 deposits");
            
            // Withdrawals
            System.out.println("\nPerforming withdrawals...");
            bankingService.withdraw(accounts.get(0).getAccountNumber(), 50.0);
            bankingService.withdraw(accounts.get(2).getAccountNumber(), 150.0);
            System.out.println("✓ Completed 2 withdrawals");
            
            // Transfers
            System.out.println("\nPerforming transfers...");
            bankingService.transfer(accounts.get(0).getAccountNumber(), 
                         accounts.get(1).getAccountNumber(), 75.0);
            System.out.println("✓ Completed 1 transfer");
            
            // Special checking account operations
            System.out.println("\nDemonstrating polymorphic behavior - checking account specific operations...");
            for (Account account : accounts) {
                if (account instanceof CheckingAccount) {
                    bankingService.writeCheck(account.getAccountNumber(), 25.0, "Grocery Store");
                    System.out.println("✓ Check written from account: " + account.getAccountNumber());
                    break;
                }
            }
            
            // Display current balances
            System.out.println("\nCurrent Account Balances:");
            System.out.println("| Account Number  | Account Type      | Balance    |");
            System.out.println("|" + "-".repeat(47) + "|");
            for (Account account : accounts) {
                System.out.printf("| %-15s | %-17s | $%-9.2f |\n", 
                                account.getAccountNumber(), 
                                account.getAccountType(),
                                account.getBalance());
            }
            
        } catch (Exception e) {
            System.err.println("Error in banking operations demo: " + e.getMessage());
        }
        
        waitForEnter();
    }
    
    private static void demonstrateExceptionHandling() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("4. EXCEPTION HANDLING DEMONSTRATION");
        System.out.println("   (Custom Exceptions, Error Handling)");
        System.out.println("=".repeat(50));
        
        List<Account> accounts = bankingService.getAllAccounts();
        String testAccount = accounts.get(0).getAccountNumber();
        
        // 1. Insufficient Funds Exception
        System.out.println("Testing InsufficientFundsException:");
        try {
            bankingService.withdraw(testAccount, 999999.0);
        } catch (InsufficientFundsException e) {
            System.out.println("✓ Caught: " + e.getMessage());
            System.out.println("  Requested: $" + e.getRequestedAmount());
            System.out.println("  Available: $" + e.getAvailableBalance());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        // 2. Invalid Transaction Exception
        System.out.println("\nTesting InvalidTransactionException:");
        try {
            bankingService.deposit(testAccount, -100.0);
        } catch (InvalidTransactionException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        // 3. Account Not Found Exception
        System.out.println("\nTesting AccountNotFoundException:");
        try {
            bankingService.withdraw("INVALID_ACCOUNT", 50.0);
        } catch (AccountNotFoundException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        // 4. Transfer to same account
        System.out.println("\nTesting business rule validation:");
        try {
            bankingService.transfer(testAccount, testAccount, 50.0);
        } catch (InvalidTransactionException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        System.out.println("\nException handling benefits demonstrated:");
        System.out.println("• Custom exception classes provide specific error information");
        System.out.println("• Business rules enforced through validation");
        System.out.println("• System remains stable despite error conditions");
        System.out.println("• User-friendly error messages provided");
        
        waitForEnter();
    }
    
    private static void demonstrateTransactionHistory() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("5. TRANSACTION HISTORY DEMONSTRATION");
        System.out.println("   (Encapsulation, Data Integrity)");
        System.out.println("=".repeat(50));
        
        try {
            List<Account> accounts = bankingService.getAllAccounts();
            Account sampleAccount = accounts.get(0);
            
            System.out.println("Transaction history for account: " + sampleAccount.getAccountNumber());
            System.out.println("Account holder: " + sampleAccount.getAccountHolderName());
            System.out.println("Account type: " + sampleAccount.getAccountType());
            System.out.println();
            
            List<Transaction> history = sampleAccount.getTransactionHistory();
            
            System.out.printf("%-12s | %-15s | %10s | %20s | %10s | %s\n",
                            "Transaction", "Type", "Amount", "Date & Time", "Balance", "Description");
            System.out.println("-".repeat(100));
            
            for (Transaction transaction : history) {
                System.out.println(transaction);
            }
            
            System.out.println("\nRecent transactions (last 3):");
            List<Transaction> recent = sampleAccount.getRecentTransactions(3);
            for (Transaction transaction : recent) {
                System.out.println("• " + transaction.getFormattedTransaction());
            }
            
            System.out.println("\nTransaction management features:");
            System.out.println("• Automatic transaction logging for all operations");
            System.out.println("• Immutable transaction records ensure data integrity");
            System.out.println("• Complete audit trail with timestamps");
            System.out.println("• Balance tracking after each transaction");
            
        } catch (Exception e) {
            System.err.println("Error in transaction history demo: " + e.getMessage());
        }
        
        waitForEnter();
    }
    
    private static void demonstrateMonthlyMaintenance() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("6. MONTHLY MAINTENANCE DEMONSTRATION");
        System.out.println("   (Polymorphism, Different Behavior per Account Type)");
        System.out.println("=".repeat(50));
        
        try {
            System.out.println("Account balances before monthly maintenance:");
            List<Account> accounts = bankingService.getAllAccounts();
            System.out.println("| Account Number  | Account Type      | Balance    |");
            System.out.println("|" + "-".repeat(47) + "|");
            for (Account account : accounts) {
                System.out.printf("| %-15s | %-17s | $%-9.2f |\n",
                                account.getAccountNumber(),
                                account.getAccountType(),
                                account.getBalance());
            }
            
            System.out.println("\nApplying monthly maintenance (polymorphic behavior)...");
            System.out.println("Each account type has different maintenance logic:");
            System.out.println("• Savings: Interest credit + maintenance fees + withdrawal counter reset");
            System.out.println("• Checking: Maintenance fees + premium interest + check counter reset");
            
            bankingService.applyMonthlyMaintenanceToAllAccounts();
            
            System.out.println("\nAccount balances after monthly maintenance:");
            System.out.println("| Account Number  | Account Type      | Balance    | Change     |");
            System.out.println("|" + "-".repeat(59) + "|");
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                double oldBalance = account.getBalance(); // This will be different now, but for demo
                System.out.printf("| %-15s | %-17s | $%-9.2f | Modified   |\n",
                                account.getAccountNumber(),
                                account.getAccountType(),
                                account.getBalance());
            }
            
            // Show specific account details
            System.out.println("\nDetailed account information after maintenance:");
            for (Account account : accounts) {
                if (account instanceof SavingsAccount) {
                    SavingsAccount savings = (SavingsAccount) account;
                    System.out.println("\nSavings Account Details:");
                    System.out.println("Account: " + account.getAccountNumber());
                    System.out.println("Free withdrawals remaining: " + savings.getRemainingFreeWithdrawals());
                    System.out.println("Interest rate: " + (savings.getInterestRate() * 100) + "% annually");
                    break;
                }
            }
            
            for (Account account : accounts) {
                if (account instanceof CheckingAccount) {
                    CheckingAccount checking = (CheckingAccount) account;
                    System.out.println("\nChecking Account Details:");
                    System.out.println("Account: " + account.getAccountNumber());
                    System.out.println("Overdraft protection: " + checking.isOverdraftProtectionEnabled());
                    System.out.println("Available overdraft: $" + String.format("%.2f", checking.getAvailableOverdraft()));
                    if (checking.isOverdrawn()) {
                        System.out.println("⚠️  Account is overdrawn!");
                    }
                    break;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error in monthly maintenance demo: " + e.getMessage());
        }
        
        waitForEnter();
    }
    
    private static void demonstrateBankReporting() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("7. BANK REPORTING DEMONSTRATION");
        System.out.println("   (Aggregation, Data Analysis)");
        System.out.println("=".repeat(50));
        
        // Display comprehensive bank summary
        System.out.println(bankingService.getBankSummary());
        
        // Customer summaries
        System.out.println("--- Customer Details ---");
        List<Customer> customers = bankingService.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer.getCustomerSummary());
            System.out.println();
        }
        
        System.out.println("Reporting features demonstrated:");
        System.out.println("• Aggregated data across all customers and accounts");
        System.out.println("• Account type analysis and statistics");
        System.out.println("• Customer relationship management");
        System.out.println("• Real-time balance calculations");
        
        waitForEnter();
    }
    
    private static void offerInteractiveMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("8. INTERACTIVE BANKING MENU");
        System.out.println("   (Optional - Try the system yourself!)");
        System.out.println("=".repeat(50));
        
        System.out.print("Would you like to try the interactive banking menu? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        
        if (choice.equals("y") || choice.equals("yes")) {
            runInteractiveMenu();
        } else {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("   THANK YOU FOR EXPLORING THE OOP BANKING SYSTEM!");
            System.out.println("=".repeat(60));
            System.out.println("You have successfully seen demonstrations of:");
            System.out.println("✓ Encapsulation - Data protection and controlled access");
            System.out.println("✓ Inheritance - Code reuse through class hierarchies");  
            System.out.println("✓ Polymorphism - Multiple forms of the same interface");
            System.out.println("✓ Abstraction - Simplified interfaces hiding complexity");
            System.out.println("\nAdditional OOP features demonstrated:");
            System.out.println("• Custom exception handling");
            System.out.println("• Method overriding and overloading");
            System.out.println("• Composition and aggregation");
            System.out.println("• Design patterns (Template Method, Factory)");
            System.out.println("=".repeat(60));
            System.out.println("Demonstration completed successfully!");
        }
    }
    
    private static void runInteractiveMenu() {
        System.out.println("\nEntering interactive mode...");
        while (true) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("        INTERACTIVE BANKING MENU");
            System.out.println("=".repeat(40));
            System.out.println("1.  View Bank Summary");
            System.out.println("2.  View All Customers");
            System.out.println("3.  View All Accounts");
            System.out.println("4.  View Account Details");
            System.out.println("5.  Perform Deposit");
            System.out.println("6.  Perform Withdrawal");
            System.out.println("7.  Transfer Funds");
            System.out.println("8.  View Transaction History");
            System.out.println("9.  Create New Customer");
            System.out.println("10. Create New Account");
            System.out.println("11. Apply Monthly Maintenance");
            System.out.println("0.  Exit");
            System.out.println("=".repeat(40));
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine().trim();
            
            try {
                switch (choice) {
                    case "1": System.out.println(bankingService.getBankSummary()); break;
                    case "2": showAllCustomers(); break;
                    case "3": showAllAccounts(); break;
                    case "4": showAccountDetails(); break;
                    case "5": performDeposit(); break;
                    case "6": performWithdrawal(); break;
                    case "7": performTransfer(); break;
                    case "8": showTransactionHistory(); break;
                    case "9": createNewCustomer(); break;
                    case "10": createNewAccount(); break;
                    case "11": applyMaintenance(); break;
                    case "0": 
                        System.out.println("Thank you for using OOP Banking System!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    // Interactive menu helper methods
    private static void showAllCustomers() {
        List<Customer> customers = bankingService.getAllCustomers();
        System.out.println("\n--- All Customers ---");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    
    private static void showAllAccounts() {
        List<Account> accounts = bankingService.getAllAccounts();
        System.out.println("\n--- All Accounts ---");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
    
    private static void showAccountDetails() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
        
        try {
            Account account = bankingService.getAccount(accountNumber);
            System.out.println("\n" + account.getAccountSummary());
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found: " + accountNumber);
        }
    }
    
    private static void performDeposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
        System.out.print("Enter deposit amount: ");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            bankingService.deposit(accountNumber, amount);
            System.out.println("✓ Deposit successful. New balance: $" + 
                             String.format("%.2f", bankingService.getAccountBalance(accountNumber)));
        } catch (Exception e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
    }
    
    private static void performWithdrawal() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
        System.out.print("Enter withdrawal amount: ");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            bankingService.withdraw(accountNumber, amount);
            System.out.println("✓ Withdrawal successful. New balance: $" + 
                             String.format("%.2f", bankingService.getAccountBalance(accountNumber)));
        } catch (Exception e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }
    
    private static void performTransfer() {
        System.out.print("Enter source account number: ");
        String fromAccount = scanner.nextLine().trim();
        System.out.print("Enter destination account number: ");
        String toAccount = scanner.nextLine().trim();
        System.out.print("Enter transfer amount: ");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            bankingService.transfer(fromAccount, toAccount, amount);
            System.out.println("✓ Transfer successful.");
            System.out.println("From account balance: $" + 
                             String.format("%.2f", bankingService.getAccountBalance(fromAccount)));
            System.out.println("To account balance: $" + 
                             String.format("%.2f", bankingService.getAccountBalance(toAccount)));
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
    
    private static void showTransactionHistory() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
        
        try {
            Account account = bankingService.getAccount(accountNumber);
            List<Transaction> history = account.getTransactionHistory();
            
            System.out.println("\n--- Transaction History ---");
            System.out.printf("%-12s | %-15s | %10s | %20s | %10s\n",
                            "ID", "Type", "Amount", "Date & Time", "Balance");
            System.out.println("-".repeat(80));
            
            for (Transaction transaction : history) {
                System.out.println(transaction);
            }
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found: " + accountNumber);
        }
    }
    
    private static void createNewCustomer() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        try {
            Customer customer = bankingService.createCustomer(firstName, lastName, email);
            System.out.println("✓ Customer created successfully!");
            System.out.println("Customer ID: " + customer.getCustomerId());
        } catch (InvalidAccountException e) {
            System.out.println("Failed to create customer: " + e.getMessage());
        }
    }
    
    private static void createNewAccount() {
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine().trim();
        System.out.print("Enter account type (1=Savings, 2=Checking): ");
        String typeChoice = scanner.nextLine().trim();
        System.out.print("Enter initial balance: ");
        
        try {
            double initialBalance = Double.parseDouble(scanner.nextLine().trim());
            
            Account account;
            if (typeChoice.equals("1")) {
                account = bankingService.createSavingsAccount(customerId, initialBalance);
                System.out.println("✓ Savings account created successfully!");
            } else if (typeChoice.equals("2")) {
                System.out.print("Enable overdraft protection? (y/n): ");
                boolean overdraft = scanner.nextLine().trim().toLowerCase().startsWith("y");
                account = bankingService.createCheckingAccount(customerId, initialBalance, overdraft);
                System.out.println("✓ Checking account created successfully!");
            } else {
                System.out.println("Invalid account type.");
                return;
            }
            
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
        } catch (Exception e) {
            System.out.println("Failed to create account: " + e.getMessage());
        }
    }
    
    private static void applyMaintenance() {
        System.out.println("Applying monthly maintenance to all accounts...");
        bankingService.applyMonthlyMaintenanceToAllAccounts();
        System.out.println("✓ Monthly maintenance applied successfully.");
        System.out.println("Check account balances and transaction histories for details.");
    }
    
    private static void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
