import java.util.HashMap;
import java.util.Map;

/**
 * Simple bank class to manage multiple accounts.
 * This is mainly for demonstration and extra test cases
 * (e.g., finding accounts, transferring by account number).
 */
public class Bank {

    private final Map<String, BankAccount> accounts = new HashMap<>();

    public boolean addAccount(BankAccount account) {
        if (account == null) {
            return false;
        }
        String accNumber = account.getAccountNumber();
        if (accounts.containsKey(accNumber)) {
            // Duplicate account number not allowed
            return false;
        }
        accounts.put(accNumber, account);
        return true;
    }

    public BankAccount findAccount(String accountNumber) {
        if (accountNumber == null) {
            return null;
        }
        return accounts.get(accountNumber);
    }

    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        BankAccount from = findAccount(fromAccountNumber);
        BankAccount to = findAccount(toAccountNumber);

        if (from == null || to == null) {
            return false;
        }

        return from.transferTo(to, amount);
    }

    public boolean changeStatus(String accountNumber, String newStatus) {
        // Check parameter validity
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return false;
        }
        if (newStatus == null) {
            return false;
        }

        // Locate account
        BankAccount acc = findAccount(accountNumber);
        if (acc == null) {
            return false; // account does not exist
        }

        // If status is not Active nor Closed nor Frozen
        if (newStatus != AccountStatus.ACTIVE && newStatus != AccountStatus.CLOSED && newStatus != AccountStatus.FROZEN) {
            return false;
        }
        
        // If same status → no change needed
        if (acc.getStatus() == newStatus) {
            return false;
        }

        // Apply status
        acc.setStatus(newStatus);
        return true;
    }

    public boolean setDailyLimit(String accountNumber, double newLimit) {
        // Check parameters
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return false;
        }

        // Business rule: daily limit cannot be less than 100 nor greater than 5,000
        if (newLimit <= 0 || newLimit < 100 || newLimit > 5000) {
            return false; // limit must be positive
        }

        // Locate account
        BankAccount acc = findAccount(accountNumber);
        if (acc == null) {
            return false;
        }

        // Check if same as current limit
        if (acc.getDailyWithdrawalLimit() == newLimit) {
            return false;
        }

        // Update limit
        acc.setDailyWithdrawalLimit(newLimit);
        return true;
    }

    // Small helper for demo: prints all accounts to System.out.
    public void printAllAccounts() {
        for (BankAccount account : accounts.values()) {
            System.out.println(account);
        }
    }
}
