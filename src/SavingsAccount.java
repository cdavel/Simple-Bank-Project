public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.02; // 2% interest

    public SavingsAccount(String name, String pin, double balance) {
        super(name, pin, balance); // Calls the parent constructor to apply values
    }

    public void applyInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
        addLog("Interest Applied (2%): $" + interest);
    }

    @Override
    public String toString() {
        // We add a prefix to identify the type in the text file
        return "SAVINGS," + getName() + "," + getPin() + "," + getBalance();
    }
}