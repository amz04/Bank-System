# Bank System – SWE 472 (Software Testing & Quality Assurance)

This project is a small, testable banking system developed for the SWE 472 Course Project (Fall 2025–26).  
It follows the requirements of the assignment by providing a realistic program (200–600 LOC) that supports
systematic test design using Input Space Partitioning (ISP) and Graph Coverage Criteria (Node/Edge Coverage).

The system consists of two main classes: **BankAccount** and **Bank**, along with a basic `Main` program for manual testing.

---

## 🚀 Project Overview

The goal of this project is to design a simple banking system and apply formal software testing techniques:

- Input Space Partitioning (ISP)
- Block Coverage (BCC)
- Edge Coverage (ECC)
- All-Combinations Coverage (ACoC)
- Control Flow Graph (CFG) modeling
- Automated test execution (JUnit)

The system includes realistic banking operations such as deposits, withdrawals, transfers, account status changes,
and modifying withdrawal limits.

---

## 📦 Project Structure

```
/src
 ├── BankAccount.java
 ├── Bank.java
 ├── AccountStatus.java
 ├── Main.java
 └── (JUnit tests will be added in /test)
```

---

## 🏦 BankAccount Class

### **Constructor**
```java
BankAccount(String accountNumber,
            String ownerName,
            double initialBalance,
            double overdraftLimit,
            double dailyWithdrawalLimit)
```

### **Features**
- Deposit and withdraw money  
- Transfer funds to another account  
- Status management (ACTIVE, FROZEN, CLOSED)  
- Daily withdrawal limit + overdraft logic  
- Validations for all input parameters  
- Internal state tracking (balance, limits, daily withdrawn amount)

### **Methods**
- `deposit(double amount)`
- `withdraw(double amount)`
- `transferTo(BankAccount target, double amount)`
- `setDailyWithdrawalLimit(double newLimit)`
- `setStatus(AccountStatus newStatus)`
- Getters for all fields

---

## 🏢 Bank Class

### **Features**
- Holds all customer accounts  
- Supports searching, updating, and modifying accounts  
- Implements higher-level banking operations

### **Methods**
- `addAccount(BankAccount account)`
- `findAccount(String accountNumber)`
- `transfer(String fromAccountNumber, String toAccountNumber, double amount)`
- `changeStatus(String accountNumber, AccountStatus newStatus)`
- `setDailyLimit(String accountNumber, double newLimit)`
- `printAllAccounts()`

---

## ▶️ Main Program

The `Main` class provides a simple demonstration of the system:

- Creating accounts  
- Depositing and withdrawing funds  
- Performing transfers  
- Changing account status  
- Adjusting daily withdrawal limits  

This class is useful for manual testing before running automated tests.

---

## 🧪 Testing (ISP + CFG)

This project is used to demonstrate:

### ✔ Input Space Partitioning
- Identification of input parameters  
- Characteristics and blocks for each method  
- ECC, BCC, ACoC tables  
- Test case generation

### ✔ Graph Coverage
- Control Flow Graph drawn for `withdraw()` and `transferTo()`  
- Node and Edge coverage  
- Optional path coverage

### ✔ Automated Testing (JUnit)
JUnit test classes (added in `/test`) validate:
- Correct handling of inputs  
- Rejection of invalid cases  
- Coverage of all decision branches  
- Correct balance/state transitions  

---

## 📘 How to Run

### **Using any Java IDE (IntelliJ, Eclipse, JCreator)**:
1. Clone the repository:
   ```
   git clone https://github.com/amz04/Bank-System.git
   ```
2. Open the project in your IDE.
3. Run `Main.java`.

### **Compile manually (terminal)**:
```
javac *.java
java Main
```

---

## 📄 License

This project is for academic purposes under the SWE 472 course.  
Do not redistribute without permission.

---

## 👤 Authors

- **Abdulwahed Z.**

Course: SWE 472 – Software Testing and Quality Assurance  
Instructor: **Prof. Adel Khelifi**