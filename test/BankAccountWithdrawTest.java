import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountWithdrawTest {

    private BankAccount createActiveAccount(double balance, double dailyLimit) {
        BankAccount acc = new BankAccount(
                "W123",
                "Fares",
                balance,
                dailyLimit
        );
        assertEquals(AccountStatus.ACTIVE, acc.getStatus());
        return acc;
    }

    // W1: amount < 0 → false
    @Test
    void withdraw_negativeAmount_returnsFalse() {
        BankAccount acc = createActiveAccount(1000.0, 1000.0);

        boolean result = acc.withdraw(-50.0);

        assertFalse(result);
        assertEquals(1000.0, acc.getBalance(), 0.0001);
    }

    // W2: amount == 0 → false
    @Test
    void withdraw_zeroAmount_returnsFalse() {
        BankAccount acc = createActiveAccount(1000.0, 1000.0);

        boolean result = acc.withdraw(0.0);

        assertFalse(result);
        assertEquals(1000.0, acc.getBalance(), 0.0001);
    }

    // W3: simple valid case
    @Test
    void withdraw_validAmountOnActiveAccount_returnsTrue_andReducesBalance() {
        BankAccount acc = createActiveAccount(1000.0, 1000.0);

        boolean result = acc.withdraw(100.0);

        assertTrue(result);
        assertEquals(900.0, acc.getBalance(), 0.0001);
    }

    // W4: non-active status → false
    @Test
    void withdraw_fromFrozenAccount_returnsFalse() {
        BankAccount acc = createActiveAccount(1000.0, 1000.0);
        acc.setStatus(AccountStatus.FROZEN);

        boolean result = acc.withdraw(100.0);

        assertFalse(result);
        assertEquals(1000.0, acc.getBalance(), 0.0001);
    }

    // W5: dailyWithdrawnAmount + amount == limit → allowed
    @Test
    void withdraw_reachesDailyLimitExactly_returnsTrue() {
        BankAccount acc = createActiveAccount(2000.0, 1000.0);

        // Setup: already withdrawn 800 today (via method itself)
        boolean setup = acc.withdraw(800.0);
        assertTrue(setup, "Setup withdraw should succeed");

        // Test: withdraw 200 more → total = 1000 == limit
        boolean result = acc.withdraw(200.0);

        assertTrue(result);
        assertEquals(1000.0, acc.getBalance(), 0.0001); // 2000 - 800 - 200
    }

    // W6: dailyWithdrawnAmount + amount > limit → rejected
    @Test
    void withdraw_exceedsDailyLimit_returnsFalse() {
        BankAccount acc = createActiveAccount(2000.0, 1000.0);

        boolean setup = acc.withdraw(800.0);
        assertTrue(setup, "Setup withdraw should succeed");

        // This would push to 1001 > 1000
        boolean result = acc.withdraw(201.0);

        assertFalse(result);
        assertEquals(1200.0, acc.getBalance(), 0.0001); // only first 800 taken
    }

    // W7: amount == balance → allowed
    @Test
    void withdraw_amountEqualToBalance_returnsTrue_andBalanceZero() {
        BankAccount acc = createActiveAccount(500.0, 1000.0);

        boolean result = acc.withdraw(500.0);

        assertTrue(result);
        assertEquals(0.0, acc.getBalance(), 0.0001);
    }

    // W8: amount > balance → rejected
    @Test
    void withdraw_amountGreaterThanBalance_returnsFalse() {
        BankAccount acc = createActiveAccount(500.0, 1000.0);

        boolean result = acc.withdraw(501.0);

        assertFalse(result);
        assertEquals(500.0, acc.getBalance(), 0.0001);
    }
}
