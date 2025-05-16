/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.java;

import java.util.*;

class Transaction {
    private String type;
    private double amount;
    private Date timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public String toString() {
        return timestamp + " - " + type + ": ₹" + amount;
    }
}

class BankAccount {
    private double balance;
    private List<Transaction> transactions;

    public BankAccount() {
        balance = 0.0;
        transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount));
            return true;
        }
        return false;
    }

    public void transfer(double amount, BankAccount receiver) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            transactions.add(new Transaction("Transfer", amount));
        }
    }

    public void printTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }
}

class User {
    private String userId;
    private String pin;
    private BankAccount account;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.account = new BankAccount();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public BankAccount getAccount() {
        return account;
    }
}

public class Atm {
    private static HashMap<String, User> users = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        users.put("user123", new User("user123", "1234"));  // Sample user

        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        User user = users.get(userId);
        if (user != null && user.getPin().equals(pin)) {
            showMenu(user);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void showMenu(User user) {
        int choice;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 : user.getAccount().printTransactionHistory();continue;
                case 2 : {
                    System.out.print("Enter amount to withdraw: ");
                    double amount = sc.nextDouble();
                    if (user.getAccount().withdraw(amount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                }continue;
                case 3 : {
                    System.out.print("Enter amount to deposit: ");
                    double amount = sc.nextDouble();
                    user.getAccount().deposit(amount);
                    System.out.println("Deposit successful.");continue;
                }
                case 4 : {
                    sc.nextLine();  // consume leftover newline
                    System.out.print("Enter recipient User ID: ");
                    String recipientId = sc.nextLine();
                    User recipient = users.get(recipientId);
                    if (recipient != null) {
                        System.out.print("Enter amount to transfer: ");
                        double amount = sc.nextDouble();
                        user.getAccount().transfer(amount, recipient.getAccount());
                        System.out.println("Transfer completed.");
                    } else {
                        System.out.println("Recipient not found.");
                    }
                }
                case 5 : System.out.println("Thank you for using the ATM.");break;
                default : System.out.println("Invalid option.");
            }
        } while (choice != 5);
    }
}
