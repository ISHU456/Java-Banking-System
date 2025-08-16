# OOP Banking System in Java

A comprehensive banking system implementation demonstrating all four pillars of Object-Oriented Programming (OOP) in Java.

## 🎯 OOP Concepts Demonstrated

### 1. **Encapsulation**
- **Private fields** with **public getter/setter methods**
- **Data validation** in constructors and setters
- **Defensive copying** in collection getters
- **Access control** through method visibility

**Examples:**
- `Account` class: Private balance, account number with controlled access
- `Customer` class: Private personal information with validation
- `Transaction` class: Immutable transaction records

### 2. **Inheritance**
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

### 3. **Polymorphism**
- **Method overriding**: Different account types implement different behaviors
- **Interface uniformity**: Same method calls work on different account types
- **Runtime binding**: Correct method called based on actual object type

**Examples:**
- `getAccountType()`, `getMinimumBalance()`, `canWithdraw()` behave differently per account type
- `applyMonthlyMaintenance()` has different logic for each account type

### 4. **Abstraction**
- **Abstract methods** define interface without implementation
- **Template method pattern** in base Account class
- **High-level operations** hide implementation complexity
- **Clean interfaces** separate what from how

## 🏗️ System Architecture

### Core Classes

1. **Account (Abstract)**
   - Base class for all account types
   - Defines common operations and data
   - Template methods for deposits/withdrawals

2. **SavingsAccount**
   - Higher minimum balance ($100)
   - Interest earnings (3.5% annually)
   - Limited free withdrawals (6 per month)
   - Monthly maintenance fees

3. **CheckingAccount**
   - Lower minimum balance ($25)
   - Overdraft protection option
   - Check writing capability
   - Different fee structure

4. **Customer**
   - Personal information management
   - Account relationship tracking
   - Data validation and encapsulation

5. **Bank**
   - Central system management
   - Customer and account operations
   - Business rule enforcement

6. **Transaction**
   - Immutable transaction records
   - Complete audit trail
   - Automatic timestamp and ID generation

### Supporting Classes

- **BankingExceptions**: Custom exception hierarchy
- **BankingSystemDemo**: Comprehensive demonstration application

## 🚀 Features

### Account Management
- ✅ Create savings and checking accounts
- ✅ Different minimum balance requirements
- ✅ Account activation/deactivation
- ✅ Polymorphic account operations

### Banking Operations
- ✅ Deposits and withdrawals
- ✅ Inter-account transfers
- ✅ Check writing (checking accounts)
- ✅ Balance inquiries

### Transaction Management
- ✅ Complete transaction history
- ✅ Automatic transaction logging
- ✅ Transaction categorization
- ✅ Audit trail maintenance

### Customer Services
- ✅ Customer registration and management
- ✅ Multiple accounts per customer
- ✅ Customer information updates
- ✅ Account relationship tracking

### Business Rules
- ✅ Minimum balance enforcement
- ✅ Overdraft protection
- ✅ Monthly maintenance fees
- ✅ Interest calculations
- ✅ Withdrawal limits

### Exception Handling
- ✅ Insufficient funds detection
- ✅ Invalid transaction prevention
- ✅ Account not found handling
- ✅ Data validation errors

## 🎮 How to Run

### Compile the System
```bash
javac *.java
```

### Run the Demonstration
```bash
java BankingSystemDemo
```

### What You'll See
The demo runs through 8 comprehensive sections:

1. **Customer Management** - Creating and managing customers
2. **Account Types** - Different account types and polymorphism
3. **Banking Operations** - Deposits, withdrawals, transfers
4. **Exception Handling** - Error cases and custom exceptions
5. **Transaction History** - Complete audit trails
6. **Monthly Maintenance** - Polymorphic maintenance operations
7. **Bank Reporting** - Comprehensive system reporting
8. **Interactive Menu** - Try the system yourself!

## 📊 OOP Benefits Demonstrated

### Code Reusability
- Common account functionality in base `Account` class
- Shared customer management across account types
- Reusable transaction and exception classes

### Maintainability
- Clear separation of concerns
- Easy to modify account-specific behavior
- Centralized business rule management

### Extensibility
- Easy to add new account types
- Simple to extend customer information
- Straightforward to add new transaction types

### Reliability
- Strong encapsulation prevents data corruption
- Exception handling ensures system stability
- Input validation maintains data integrity

## 🔧 Key Design Patterns

### Template Method
- `Account` class defines transaction algorithm
- Subclasses customize specific steps
- Ensures consistent operation flow

### Factory Method
- `Bank` class creates accounts
- Ensures proper initialization
- Maintains object relationships

### Strategy Pattern
- Different validation strategies per account type
- Polymorphic maintenance operations
- Flexible business rule implementation

## 📈 Advanced Features

### Interest Calculation
- Automatic monthly interest for savings accounts
- Configurable interest rates
- Compound interest support

### Fee Management
- Overdraft fees for checking accounts
- Monthly maintenance fees
- Withdrawal fees for excess transactions

### Overdraft Protection
- Configurable overdraft limits
- Automatic fee calculation
- Balance validation

### Audit Trail
- Complete transaction history
- Immutable transaction records
- Timestamp and ID tracking

## 🧪 Testing the System

The `BankingSystemDemo` class provides comprehensive testing:

- **Unit-level testing** of individual operations
- **Integration testing** of system workflows
- **Exception testing** of error conditions
- **Interactive testing** through menu system

## 🎓 Learning Outcomes

This system demonstrates:

1. **How to design** class hierarchies with inheritance
2. **How to implement** polymorphic behavior
3. **How to use** encapsulation for data protection
4. **How to apply** abstraction for clean interfaces
5. **How to handle** exceptions gracefully
6. **How to structure** large object-oriented systems

## 📝 Code Statistics

- **7 Classes**: Complete object-oriented design
- **4 OOP Pillars**: All demonstrated comprehensively
- **Custom Exceptions**: 4 specialized exception types
- **Design Patterns**: Template Method, Factory, Strategy
- **Features**: 20+ banking operations
- **Demo Scenarios**: 8 comprehensive test sections

## 🚀 Next Steps

To extend this system, consider adding:

- **Loan accounts** with payment schedules
- **Credit cards** with credit limits
- **Investment accounts** with portfolio management
- **Multi-currency support** with exchange rates
- **Online banking** with security features
- **Mobile app integration** with REST APIs

---

**Author**: OOP Banking System Demo  
**Purpose**: Educational demonstration of Java OOP concepts  
**Language**: Java  
**Paradigm**: Object-Oriented Programming
