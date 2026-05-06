import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private double balance;
    private List<String> transactionHistory;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public List<String> getTransactionHistory() { return transactionHistory; }
    public String getName() { return name; }
    public double getBalance() { return balance; }

    @Override
    public String toString() {
        return name + "," + balance;
    }
}