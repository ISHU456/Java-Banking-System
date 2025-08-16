# 🏦 OOP Banking System in Java

A comprehensive banking system implementation demonstrating all four pillars of Object-Oriented Programming (OOP) in Java with proper project structure and organization.

## 📁 Project Structure

```
java-banking-system/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── banking/
│                   ├── exception/          # Custom exception classes
│                   │   ├── BankingExceptions.java
│                   │   ├── InsufficientFundsException.java
│                   │   ├── InvalidAccountException.java
│                   │   ├── InvalidTransactionException.java
│                   │   └── AccountNotFoundException.java
│                   ├── model/              # Domain model classes
│                   │   ├── Account.java
│                   │   ├── SavingsAccount.java
│                   │   ├── CheckingAccount.java
│                   │   ├── Customer.java
│                   │   └── Transaction.java
│                   ├── service/            # Business logic layer
│                   │   └── BankingService.java
│                   └── demo/               # Demo application
│                       └── BankingSystemDemo.java
├── test/                                   # Unit tests (future)
├── docs/                                   # Documentation
├── README.md                               # This file
└── build/                                  # Compiled classes
```

## 🎯 OOP Concepts Demonstrated

### 1. **Encapsulation** 🔒
- **Private fields** with **public getter/setter methods**
- **Data validation** in constructors and setters
- **Defensive copying** in collection getters
- **Access control** through method visibility

**Examples:**
- `Account` class: Private balance, account number with controlled access
- `Customer` class: Private personal information with validation
- `Transaction` class: Immutable transaction records

### 2. **Inheritance** 🌳
- **Abstract base class** `Account` defines common behavior
- **Concrete subclasses** `SavingsAccount` and `CheckingAccount` extend `Account`
- **Method overriding** for account-specific behavior
- **Protected methods** for subclass access

**Class Hierarchy:**
```
Account (Abstract)
├── SavingsAccount
└── CheckingAccount
```

### 3. **Polymorphism** 🎭
- **Method overriding**: Different account types implement different behaviors
- **Interface uniformity**: Same method calls work on different account types
- **Runtime binding**: Correct method called based on actual object type

**Examples:**
- `getAccountType()`, `getMinimumBalance()`, `canWithdraw()` behave differently per account type
- `applyMonthlyMaintenance()` has different logic for each account type

### 4. **Abstraction** 🎨
- **Abstract methods** define interface without implementation
- **Template method pattern** in base Account class
- **High-level operations** hide implementation complexity
- **Clean interfaces** separate what from how

## 🚀 How to Run

### Prerequisites
- Java 11 or higher
- IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Compilation & Execution
```bash
# Navigate to project directory
cd "/Users/ishuanandmalviya/Desktop/java project"

# Compile all source files
javac -d build src/main/java/com/banking/*/*.java

# Or compile directly in source directory
javac src/main/java/com/banking/*/*.java

# Run the demo
java -cp src/main/java com.banking.demo.BankingSystemDemo
```

### What You'll See
The demo runs through 8 comprehensive sections:

1. **Customer Management** - Creating and managing customers (Encapsulation)
2. **Account Types** - Different account types and polymorphism (Inheritance & Polymorphism)
3. **Banking Operations** - Deposits, withdrawals, transfers (Method Overriding)
4. **Exception Handling** - Error cases and custom exceptions
5. **Transaction History** - Complete audit trails (Data Integrity)
6. **Monthly Maintenance** - Polymorphic maintenance operations
7. **Bank Reporting** - Comprehensive system reporting (Aggregation)
8. **Interactive Menu** - Try the system yourself!

## 🏗️ Architecture Overview

### Package Organization

#### `com.banking.exception`
Custom exception classes providing specific error handling:
- `InsufficientFundsException` - Insufficient account balance with detailed info
- `InvalidAccountException` - Invalid account operations or data
- `InvalidTransactionException` - Invalid transaction attempts
- `AccountNotFoundException` - Account or customer not found errors

#### `com.banking.model`
Core domain model classes demonstrating OOP principles:
- `Account` - Abstract base class defining common account behavior
- `SavingsAccount` - Savings account with interest, withdrawal limits, fees
- `CheckingAccount` - Checking account with overdraft protection, check writing
- `Customer` - Customer information and account relationship management
- `Transaction` - Immutable transaction records with complete audit trail

#### `com.banking.service`
Business logic and operations layer:
- `BankingService` - Central service managing all banking operations, customers, and accounts

#### `com.banking.demo`
Comprehensive demonstration application:
- `BankingSystemDemo` - Interactive demo showcasing all OOP concepts and features

## 📊 Key Design Patterns

### Template Method Pattern
```java
// Account class defines the algorithm structure
public final void deposit(double amount) throws InvalidTransactionException {
    validateTransactionAmount(amount);    // Same for all
    performDeposit(amount);              // Same for all
    addTransaction(...);                 // Same for all
}

// Subclasses implement specific behavior
protected void performDeposit(double amount) {
    balance += amount;  // Basic implementation
}
```

### Factory Pattern
```java
// BankingService acts as factory for accounts
public SavingsAccount createSavingsAccount(String customerId, double balance) {
    Customer customer = getCustomer(customerId);
    SavingsAccount account = new SavingsAccount(customer.getFullName(), balance);
    // Handle relationships and registration
    return account;
}
```

### Strategy Pattern
```java
// Different account types have different withdrawal strategies
@Override
public boolean canWithdraw(double amount) {
    // SavingsAccount strategy
    return (balance - amount) >= MINIMUM_BALANCE && 
           (withdrawalsThisMonth < FREE_WITHDRAWALS || sufficientForFees);
}

@Override  
public boolean canWithdraw(double amount) {
    // CheckingAccount strategy  
    return (balance - amount) >= 0 || 
           (overdraftProtection && overdraftAmount <= OVERDRAFT_LIMIT);
}
```

## 🔧 Advanced Features

### Inheritance Hierarchy
- **Abstract Account Class**: Defines common structure and behavior
- **Concrete Implementations**: SavingsAccount and CheckingAccount with specific rules
- **Method Overriding**: Each account type implements abstract methods differently
- **Code Reuse**: Common functionality inherited, specific behavior overridden

### Polymorphic Behavior
```java
// Same method call, different behavior based on actual object type
for (Account account : accounts) {
    account.applyMonthlyMaintenance();  // Calls appropriate implementation
    System.out.println(account.getAccountType());  // Different string per type
    boolean canWithdraw = account.canWithdraw(100.0);  // Different logic per type
}
```

### Encapsulation Examples
```java
// Private fields with controlled access
private double balance;                    // Cannot be accessed directly
private final List<Transaction> history;  // Internal state protected

// Public interface with validation
public void setEmail(String email) throws InvalidAccountException {
    if (!isValidEmail(email)) {
        throw new InvalidAccountException("Invalid email format");
    }
    this.email = email.trim().toLowerCase();
}

// Defensive copying
public List<Transaction> getTransactionHistory() {
    return new ArrayList<>(transactionHistory);  // Return copy, not reference
}
```

### Exception Handling Hierarchy
```java
// Custom exceptions provide specific error information
try {
    bankingService.withdraw(accountNumber, amount);
} catch (InsufficientFundsException e) {
    System.out.println("Requested: $" + e.getRequestedAmount());
    System.out.println("Available: $" + e.getAvailableBalance());
} catch (InvalidTransactionException e) {
    System.out.println("Transaction error: " + e.getMessage());
} catch (AccountNotFoundException e) {
    System.out.println("Account issue: " + e.getMessage());
}
```

## 📈 OOP Benefits Demonstrated

### Code Reusability
- **Template Methods**: Common transaction processing logic shared
- **Inheritance**: Account functionality reused by all account types
- **Composition**: Customer class reuses Account objects

### Maintainability
- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed Principle**: Easy to add new account types without changing existing code
- **Dependency Inversion**: High-level modules don't depend on low-level details

### Extensibility
```java
// Easy to add new account type
public class BusinessAccount extends Account {
    @Override
    public String getAccountType() { return "Business Account"; }
    
    @Override
    public double getMinimumBalance() { return 1000.0; }
    
    @Override
    public boolean canWithdraw(double amount) {
        // Business-specific withdrawal logic
        return balance - amount >= getMinimumBalance();
    }
    
    @Override
    public void applyMonthlyMaintenance() {
        // Business-specific maintenance
    }
}
```

## 🧪 Testing Features

The demo provides comprehensive testing of:

### OOP Concept Testing
- **Encapsulation**: Data validation, controlled access
- **Inheritance**: Method overriding, shared behavior
- **Polymorphism**: Same interface, different implementations
- **Abstraction**: Abstract classes, interface design

### Functional Testing
- **Account Creation**: Different types with proper initialization
- **Banking Operations**: Deposits, withdrawals, transfers
- **Business Rules**: Minimum balances, fees, limits
- **Error Handling**: Invalid operations, insufficient funds

### Integration Testing
- **Customer-Account Relationships**: Multiple accounts per customer
- **Transaction Management**: Complete audit trail
- **System Operations**: End-to-end workflows

## 📝 Code Quality Features

### Professional Structure
- **Package Organization**: Logical grouping by functionality
- **Naming Conventions**: Clear, descriptive names following Java standards
- **Documentation**: Comprehensive Javadoc comments
- **Error Handling**: Robust exception management

### Best Practices
- **SOLID Principles**: Single Responsibility, Open/Closed, etc.
- **Design Patterns**: Template Method, Factory, Strategy
- **Defensive Programming**: Input validation, null checks
- **Immutability**: Transaction records cannot be modified

### Educational Value
- **Clear Examples**: Each OOP concept clearly demonstrated
- **Progressive Complexity**: Builds from simple to complex concepts
- **Interactive Learning**: Hands-on exploration through demo
- **Real-world Application**: Practical banking domain

## 🎓 Learning Outcomes

After exploring this system, you'll understand:

1. **How to design** class hierarchies using inheritance
2. **How to implement** polymorphic behavior effectively
3. **How to use** encapsulation for data protection
4. **How to apply** abstraction for clean interfaces
5. **How to handle** exceptions gracefully
6. **How to structure** large Java applications
7. **How to organize** code with proper packages
8. **How to implement** design patterns in practice

## 🚀 Quick Start Guide

1. **Clone or Download** the project
2. **Navigate** to project directory
3. **Compile** using `javac src/main/java/com/banking/*/*.java`
4. **Run** using `java -cp src/main/java com.banking.demo.BankingSystemDemo`
5. **Follow** the interactive demo
6. **Explore** the code to see OOP concepts in action

## 📊 Project Statistics

- **10 Classes**: Complete object-oriented design
- **4 OOP Pillars**: All demonstrated comprehensively
- **5 Custom Exceptions**: Specialized error handling
- **3 Design Patterns**: Template Method, Factory, Strategy
- **25+ Methods**: Comprehensive banking operations
- **8 Demo Sections**: Progressive learning experience
- **100% Java**: No external dependencies

---

**🎯 Perfect for**: CS students, Java learners, OOP concept demonstration  
**📚 Covers**: All major OOP concepts with practical examples  
**🔧 Language**: Java 11+ with proper project structure  
**📈 Level**: Intermediate with beginner-friendly explanations
