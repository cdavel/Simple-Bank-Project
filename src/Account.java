import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private String pin; // Stored as String to handle leading zeros (e.g., 0012)
    protected double balance;
    private List<String> transactionHistory;

    public Account(String name, String pin, double balance) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        addLog("Account initialized/accessed.");
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addLog("Deposited: $" + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addLog("Withdrew: $" + amount);
            return true;
        }
        addLog("Failed withdrawal attempt: $" + amount);
        return false;
    }

    protected void addLog(String message) {
        String timestamp = new java.util.Date().toString();
        transactionHistory.add("[" + timestamp + "] " + message);
    }

    public List<String> getTransactionHistory() { return transactionHistory; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public String getPin() { return pin; }

    @Override
    public String toString() {
        return "STANDARD" + "," + name + "," + pin + "," + balance;
    }
}