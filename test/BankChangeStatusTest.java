import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankChangeStatusTest {

    private Bank createBankWithSingleActiveAccount() {
        Bank bank = new Bank();
        BankAccount acc = new BankAccount(
                "A123",
                "Fares",
                1000.0,
                1000.0    // dailyWithdrawalLimit
        );
        acc.setStatus(AccountStatus.ACTIVE);
        boolean added = bank.addAccount(acc);
        assertTrue(added, "Account should be added in setup");
        return bank;
    }

    // C1: accountNumber == null → false
    @Test
    void changeStatus_nullAccountNumber_returnsFalse() {
        Bank bank = createBankWithSingleActiveAccount();

        boolean result = bank.changeStatus(null, AccountStatus.ACTIVE);

        assertFalse(result);
    }

    // C2: accountNumber blank → false
    @Test
    void changeStatus_blankAccountNumber_returnsFalse() {
        Bank bank = createBankWithSingleActiveAccount();

        boolean result = bank.changeStatus("   ", AccountStatus.ACTIVE);

        assertFalse(result);
    }

    // C3: newStatus == null → false
    @Test
    void changeStatus_nullNewStatus_returnsFalse() {
        Bank bank = createBankWithSingleActiveAccount();

        boolean result = bank.changeStatus("A123", null);

        assertFalse(result);
    }

    // C4: account not found → false
    @Test
    void changeStatus_nonExistingAccount_returnsFalse() {
        Bank bank = createBankWithSingleActiveAccount();

        boolean result = bank.changeStatus("X999", AccountStatus.FROZEN);

        assertFalse(result);
    }

    // C6: newStatus same as current → false, no change
    @Test
    void changeStatus_sameStatus_returnsFalse_andDoesNotChangeStatus() {
        Bank bank = createBankWithSingleActiveAccount();
        BankAccount acc = bank.findAccount("A123");
        assertNotNull(acc);
        assertEquals(AccountStatus.ACTIVE, acc.getStatus());

        boolean result = bank.changeStatus("A123", AccountStatus.ACTIVE);

        assertFalse(result);
        assertEquals(AccountStatus.ACTIVE, acc.getStatus());
    }

    // C7: valid change ACTIVE → FROZEN → true
    @Test
    void changeStatus_validChange_returnsTrue_andUpdatesStatus() {
        Bank bank = createBankWithSingleActiveAccount();
        BankAccount acc = bank.findAccount("A123");
        assertNotNull(acc);
        assertEquals(AccountStatus.ACTIVE, acc.getStatus());

        boolean result = bank.changeStatus("A123", AccountStatus.FROZEN);

        assertTrue(result);
        assertEquals(AccountStatus.FROZEN, acc.getStatus());
    }
}
