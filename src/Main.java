public class Main {

    public static void main(String[] args) {

        // Create two accounts
        BankAccount acc1 = new BankAccount(
                "A1001",
                "Fares",
                1000.0,
                500.0        // daily withdrawal limit
        );

        BankAccount acc2 = new BankAccount(
                "A2001",
                "Mohammed",
                300.0,
                400.0
        );

        System.out.println("=== INITIAL STATE ===");
        System.out.println(acc1);
        System.out.println(acc2);

        // Deposit test
        System.out.println("\n=== TEST: Deposit 200 into acc1 ===");
        boolean dep = acc1.deposit(200.0);
        System.out.println("Deposit result: " + dep);
        System.out.println("New balance: " + acc1.getBalance());

        // Invalid deposit test
        System.out.println("\n=== TEST: Deposit -50 (invalid) ===");
        boolean depInvalid = acc1.deposit(-50.0);
        System.out.println("Deposit result: " + depInvalid);
        System.out.println("Balance remains: " + acc1.getBalance());

        // Withdrawal test
        System.out.println("\n=== TEST: Withdraw 300 from acc1 ===");
        boolean wd = acc1.withdraw(300.0);
        System.out.println("Withdraw result: " + wd);
        System.out.println("New balance: " + acc1.getBalance());

        // Daily limit exceeded test
        System.out.println("\n=== TEST: Withdraw 400 (should exceed daily limit) ===");
        boolean wdLimit = acc1.withdraw(400.0);
        System.out.println("Withdraw result: " + wdLimit);
        System.out.println("Balance remains: " + acc1.getBalance());

        // Overdraft test
        System.out.println("\n=== TEST: Withdraw 1000 from acc1 (should use overdraft) ===");
        boolean wdOver = acc1.withdraw(1000.0);
        System.out.println("Withdraw result: " + wdOver);
        System.out.println("Balance after: " + acc1.getBalance());

        // Transfer test
        System.out.println("\n=== TEST: Transfer 200 from acc1 → acc2 ===");
        boolean transfer = acc1.transferTo(acc2, 200.0);
        System.out.println("Transfer result: " + transfer);

        System.out.println("\n--- FINAL STATE ---");
        System.out.println(acc1);
        System.out.println(acc2);

        System.out.println("\n=== END OF PROGRAM ===");
    }
}
