import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "bank_data.txt";
    private static final Map<String, Account> allAccounts = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadAllAccounts(); // Load everyone into memory first

        System.out.println("Simple Bank Application by Charalampos-Raphael Ntavelidis");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter 4-digit PIN: ");
        String pinInput = scanner.next();

        // Check if user exists in our Map
        Account userAccount = allAccounts.get(name.toLowerCase());

        if (userAccount == null) {
            System.out.println("No account found. Creating new account for: " + name);
            userAccount = new Account(name, pinInput, 0.0);
            allAccounts.put(name.toLowerCase(), userAccount);
        } else {
            if (!userAccount.validatePin(pinInput)) {
                System.out.println("Access Denied: Incorrect PIN.");
                return;
            }
            System.out.println("Welcome back, " + userAccount.getName());
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Deposit\n2. Withdraw\n3. Balance\n4. Statement\n5. Exit");
            System.out.print("Choice: ");

            // Simple validation to prevent crashing on non-integers
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number (1-5).");
                scanner.next(); // Clear invalid input
                continue;
            }

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter deposit amount: ");
                    userAccount.deposit(scanner.nextDouble());
                }
                case 2 -> {
                    System.out.print("Enter withdrawal amount: ");
                    double amount = scanner.nextDouble();
                    if (!userAccount.withdraw(amount)) {
                        System.out.println("Transaction failed: Insufficient funds or invalid amount.");
                    } else {
                        System.out.println("Withdrawal successful.");
                    }
                }
                case 3 -> System.out.println("Current Balance: $" + userAccount.getBalance());
                case 4 -> {
                    System.out.println("\n--- Transaction History ---");
                    for (String log : userAccount.getTransactionHistory()) {
                        System.out.println(log);
                    }
                }
                case 5 -> {
                    saveAllData(); // Save everyone back to file
                    saveLogs(userAccount); // Save this user's specific text logs
                    running = false;
                    System.out.println("Data saved. Shutting down!");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void loadAllAccounts() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                // Expecting Name, PIN, Balance
                Account acc = new Account(p[0], p[1], Double.parseDouble(p[2]));
                allAccounts.put(p[0].toLowerCase(), acc);
            }
        } catch (Exception e) {
            System.out.println("System Error: Could not read database.");
        }
    }

    private static void saveAllData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Account acc : allAccounts.values()) {
                writer.println(acc.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving database!");
        }
    }

    private static void saveLogs(Account account) {
        String logFile = account.getName().toLowerCase() + "_logs.txt";
        try (PrintWriter logWriter = new PrintWriter(new FileWriter(logFile, true))) {
            for (String log : account.getTransactionHistory()) {
                logWriter.println(log);
            }
        } catch (IOException e) {
            System.out.println("Error saving logs.");
        }
    }
}