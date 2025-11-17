/**
 * Represents a simple bank account with basic operations.
 *
 * This class is intentionally designed with several branches and conditions
 * so that it can be used for Input Space Partitioning (ISP) and
 * Graph Coverage (e.g., node/edge coverage) in SWE 472.
 */
public class BankAccount {

    private final String accountNumber;
    private final String ownerName;

    private double balance;
    private double dailyWithdrawalLimit; // Maximum total amount that can be withdrawn per day
    private double dailyWithdrawnAmount; // How much has been withdrawn today

    private String status;

    /**
     * Creates a new BankAccount.
     *
     * @param accountNumber        unique account identifier (non-null, non-empty)
     * @param ownerName            name of the account owner (non-null, non-empty)
     * @param initialBalance       initial balance (can be zero or positive)
     * @param dailyWithdrawalLimit maximum amount allowed to withdraw per day (must
     *                             be > 0 and less than 5,000)
     */
    public BankAccount(String accountNumber, String ownerName, double initialBalance, double dailyWithdrawalLimit) {

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number must not be empty.");
        }
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name must not be empty.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        if (dailyWithdrawalLimit <= 0) {
            throw new IllegalArgumentException("Daily withdrawal limit must be positive.");
        }

        this.accountNumber = accountNumber.trim();
        this.ownerName = ownerName.trim();
        this.balance = initialBalance;
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        this.dailyWithdrawnAmount = 0.0;
        this.status = AccountStatus.ACTIVE;
    }

    // --------- Getters --------- //

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public double getDailyWithdrawnAmount() {
        return dailyWithdrawnAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {
        if (dailyWithdrawalLimit <= 0) {
            throw new IllegalArgumentException("Daily withdrawal limit must be positive.");
        }
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }

    public void setStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }

        if (status != AccountStatus.ACTIVE && status != AccountStatus.CLOSED && status != AccountStatus.FROZEN) {
            throw new IllegalArgumentException("Status is invalid.");
        }

        this.status = status;
    }

    /**
     * Resets the daily withdrawn amount.
     * Call this method at the start of a "new day" in your tests.
     */
    public void resetDailyWithdrawnAmount() {
        this.dailyWithdrawnAmount = 0.0;
    }

    // --------- Core Operations --------- //

    /**
     * Deposits money into the account.
     *
     * @param amount amount to deposit (must be > 0)
     * @return true if the deposit succeeded, false otherwise.
     */
    public boolean deposit(double amount) {
        if (status != AccountStatus.ACTIVE) {
            // Cannot deposit into frozen or closed accounts
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        balance += amount;
        return true;
    }

    /**
     * Withdraws money from the account.
     *
     * This method includes multiple checks to allow rich test cases:
     * - Account must be ACTIVE.
     * - Amount must be > 0.
     * - Must not exceed daily withdrawal limit.
     * - Must not exceed available balance + overdraft limit.
     *
     * @param amount amount to withdraw (must be > 0)
     * @return true if the withdrawal succeeded, false otherwise.
     */
    public boolean withdraw(double amount) {
        if (status != AccountStatus.ACTIVE) {
            // Cannot withdraw from frozen or closed accounts
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        // Check daily limit
        if (dailyWithdrawnAmount + amount > dailyWithdrawalLimit) {
            return false;
        }

        if (amount > balance) {
            // Not enough balance + overdraft
            return false;
        }

        // Perform withdrawal
        balance -= amount;
        dailyWithdrawnAmount += amount;
        return true;
    }

    /**
     * Transfers money to another BankAccount.
     *
     * The transfer succeeds only if:
     * - Both accounts are ACTIVE.
     * - Amount is > 0.
     * - This account can successfully withdraw the amount (respecting
     * daily limits and overdraft).
     *
     * @param target the target account to transfer to (must not be null)
     * @param amount amount to transfer
     * @return true if the transfer succeeded, false otherwise.
     */
    public boolean transferTo(BankAccount target, double amount) {
        if (target == null) {
            return false;
        }

        // Cannot transfer to the same account
        if (this == target) {
            return false;
        }

        if (this.status != AccountStatus.ACTIVE || target.status != AccountStatus.ACTIVE) {
            return false;
        }

        // Withdraw first; if successful, then deposit into target
        boolean withdrawn = this.withdraw(amount);
        if (!withdrawn) {
            return false;
        }

        boolean deposited = target.deposit(amount);
        if (!deposited) {
            // Rollback if deposit fails for some reason
            this.balance += amount;
            this.dailyWithdrawnAmount -= amount;
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", balance=" + balance +
                ", dailyWithdrawalLimit=" + dailyWithdrawalLimit +
                ", dailyWithdrawnAmount=" + dailyWithdrawnAmount +
                ", status=" + status +
                '}';
    }
}
