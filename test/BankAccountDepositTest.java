import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountDepositTest {

    private BankAccount createActiveAccount(double balance) {
        BankAccount acc = new BankAccount(
                "A123",
                "Fares",
                balance,
                1000.0    // dailyWithdrawalLimit
        );
        assertEquals(AccountStatus.ACTIVE, acc.getStatus());
        return acc;
    }

    // D1: amount < 0 → false, balance unchanged
    @Test
    void deposit_negativeAmount_returnsFalse_andBalanceUnchanged() {
        BankAccount acc = createActiveAccount(1000.0);

        boolean result = acc.deposit(-100.0);

        assertFalse(result);
        assertEquals(1000.0, acc.getBalance(), 0.0001);
    }

    // D2: amount == 0 → false, balance unchanged
    @Test
    void deposit_zeroAmount_returnsFalse_andBalanceUnchanged() {
        BankAccount acc = createActiveAccount(1000.0);

        boolean result = acc.deposit(0.0);

        assertFalse(result);
        assertEquals(1000.0, acc.getBalance(), 0.0001);
    }

    // D3: amount > 0, ACTIVE → true, balance increases
    @Test
    void deposit_positiveAmountOnActiveAccount_returnsTrue_andIncreasesBalance() {
        BankAccount acc = createActiveAccount(1000.0);

        boolean result = acc.deposit(100.0);

        assertTrue(result);
        assertEquals(1100.0, acc.getBalance(), 0.0001);
    }

    // D4: status = FROZEN → false, balance unchanged
    @Test
    void deposit_onFrozenAccount_returnsFalse_andBalanceUnchanged() {
        BankAccount acc = createActiveAccount(1000.0);
        acc.setStatus(AccountStatus.FROZEN);

        boolean result = acc.deposit(100.0);

        assertFalse(result);
        assertEquals(1000.0, acc.getBalance(), 0.0001);
    }

    // D5: status = CLOSED → false, balance unchanged
    @Test
    void deposit_onClosedAccount_returnsFalse_andBalanceUnchanged() {
        BankAccount acc = createActiveAccount(1000.0);
        acc.setStatus(AccountStatus.CLOSED);

        boolean result = acc.deposit(100.0);

        assertFalse(result);
        assertEquals(1000.0, acc.getBalance(), 0.0001);
    }
}
