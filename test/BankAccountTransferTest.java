import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTransferTest {

    private BankAccount createActiveAccount(String id, double balance, double dailyLimit) {
        BankAccount acc = new BankAccount(
                id,
                "Owner " + id,
                balance,
                dailyLimit
        );
        assertEquals(AccountStatus.ACTIVE, acc.getStatus());
        return acc;
    }

    // T1: target == null → false
    @Test
    void transferTo_nullTarget_returnsFalse() {
        BankAccount source = createActiveAccount("SRC", 1000.0, 1000.0);

        boolean result = source.transferTo(null, 100.0);

        assertFalse(result);
        assertEquals(1000.0, source.getBalance(), 0.0001);
    }

    // T2: this == target → false
    @Test
    void transferTo_sameAccount_returnsFalse() {
        BankAccount source = createActiveAccount("SRC", 1000.0, 1000.0);

        boolean result = source.transferTo(source, 100.0);

        assertFalse(result);
        assertEquals(1000.0, source.getBalance(), 0.0001);
    }

    // T3: source not ACTIVE → false
    @Test
    void transferTo_sourceNotActive_returnsFalse() {
        BankAccount source = createActiveAccount("SRC", 1000.0, 1000.0);
        BankAccount target = createActiveAccount("TGT", 500.0, 1000.0);
        source.setStatus(AccountStatus.FROZEN);

        boolean result = source.transferTo(target, 100.0);

        assertFalse(result);
        assertEquals(1000.0, source.getBalance(), 0.0001);
        assertEquals(500.0, target.getBalance(), 0.0001);
    }

    // T4: target not ACTIVE → false
    @Test
    void transferTo_targetNotActive_returnsFalse() {
        BankAccount source = createActiveAccount("SRC", 1000.0, 1000.0);
        BankAccount target = createActiveAccount("TGT", 500.0, 1000.0);
        target.setStatus(AccountStatus.FROZEN);

        boolean result = source.transferTo(target, 100.0);

        assertFalse(result);
        assertEquals(1000.0, source.getBalance(), 0.0001);
        assertEquals(500.0, target.getBalance(), 0.0001);
    }

    // T5: amount == 0 → withdraw fails → false
    @Test
    void transferTo_zeroAmount_returnsFalse() {
        BankAccount source = createActiveAccount("SRC", 1000.0, 1000.0);
        BankAccount target = createActiveAccount("TGT", 500.0, 1000.0);

        boolean result = source.transferTo(target, 0.0);

        assertFalse(result);
        assertEquals(1000.0, source.getBalance(), 0.0001);
        assertEquals(500.0, target.getBalance(), 0.0001);
    }

    // T6: amount > source balance → withdraw fails → false
    @Test
    void transferTo_amountGreaterThanSourceBalance_returnsFalse() {
        BankAccount source = createActiveAccount("SRC", 50.0, 1000.0);
        BankAccount target = createActiveAccount("TGT", 500.0, 1000.0);

        boolean result = source.transferTo(target, 100.0);

        assertFalse(result);
        assertEquals(50.0, source.getBalance(), 0.0001);
        assertEquals(500.0, target.getBalance(), 0.0001);
    }

    // T7: successful transfer path
    @Test
    void transferTo_validAmountAndStatuses_returnsTrue_andMovesMoney() {
        BankAccount source = createActiveAccount("SRC", 1000.0, 1000.0);
        BankAccount target = createActiveAccount("TGT", 500.0, 1000.0);

        boolean result = source.transferTo(target, 200.0);

        assertTrue(result);
        assertEquals(800.0, source.getBalance(), 0.0001);
        assertEquals(700.0, target.getBalance(), 0.0001);
    }
}
