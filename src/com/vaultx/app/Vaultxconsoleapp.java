package com.vaultx.app;

import com.vaultx.model.Account;
import com.vaultx.model.Transaction;
import com.vaultx.model.User;
import java.util.*;
import java.util.regex.Pattern;

public class Vaultxconsoleapp
{
    // Map to store users by their phone number
    private static final Map<String, User> usersByPhone = new HashMap<>();

    // Map to track accounts by account number
    private static final Map<Long, Account> accountsByNumber = new HashMap<>();

    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);

    // Regex patterns for input validation
    private static final Pattern PAN_PATTERN = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,15}$"); // 10 to 15 digits

    // In-memory store for OTP mapping
    private static final Map<String, String> otpStore = new HashMap<>();

    // Main entry point for the application
    public static void main(String[] args)
    {
        System.out.println("--------Welcome to India's one of the most trustable Bank VaultX!-----");
        System.out.println("");
        System.out.println("---------------------VaultX!-----------------");
        while (true) {
            System.out.println("\nPress 1 to Register");
            System.out.println("Press 2 to Login");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    loginUser();
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerUser()
    {
        // Print the registration section header for user awareness
        System.out.println("\n-- Registration --");

        String name = getInput("Enter your full name: ");
        String fatherName = getInput("Enter your father's name: ");
        String motherName = getInput("Enter your mother's name: ");
        String panCard = getValidInput("Enter PAN card number (e.g. ABCDE1234F): ", PAN_PATTERN, "Invalid PAN format! Try again.");
        String email = getValidInput("Enter email address: ", EMAIL_PATTERN, "Invalid email format! Try again.");
        String phone = getValidInput("Enter phone number (10-15 digits): ", PHONE_PATTERN, "Invalid phone number! Try again.");
        sendOtp(phone);
        System.out.println("OTP sent on your device. Please don't share it with anyone. OTP expires in 3 minutes.");
        String userOtp = getInput("Enter OTP: ");
        if (!verifyOtp(phone, userOtp))
        {
            System.out.println("OTP verification failed. Registration aborted.");
            return;
        }

        long accountNumber = generateUniqueAccountNumber();
        User user = new User(UUID.randomUUID().toString(), accountNumber, name, fatherName, motherName, email, panCard, phone);
        Account account = new Account(accountNumber, name, 0);
        usersByPhone.put(phone, user);
        accountsByNumber.put(accountNumber, account);

        System.out.println("Your account has been created! Your account number is: " + accountNumber);
        transactionMenu(account);
    }

    private static void loginUser()
    {
        System.out.println("\n-- Login --");
        String phone = getValidInput("Enter your phone number: ", PHONE_PATTERN, "Invalid phone number! Try again.");
        User user = usersByPhone.get(phone);
        if (user == null)
        {
            System.out.println("No user found with this phone number. Please register first.");
            return;
        }
        sendOtp(phone);
        System.out.println("OTP sent on your device. Please don't share it. OTP expires in 3 minutes.");
        String userOtp = getInput("Enter OTP: ");
        if (!verifyOtp(phone, userOtp)) {
            System.out.println("OTP verification failed. Login aborted.");
            return;
        }
        System.out.println("Login successful! Welcome, " + user.getName());
        Account account = accountsByNumber.get(user.getAccountNumber());
        transactionMenu(account);
    }


    private static void transactionMenu(Account account)
    {
        while (true)
        {
            System.out.println("\n--- Transaction Menu ---");
            System.out.println("1. Withdraw money");
            System.out.println("2. Deposit money");
            System.out.println("3. Show balance");
            System.out.println("4. Transaction history (last 5)");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice)
            {
                case "1" -> withdrawMoney(account);
                case "2" -> depositMoney(account);
                case "3" -> System.out.println("Current balance: " + account.getBalance());
                case "4" ->
                {
                    List<Transaction> transactions = account.getMiniStatement(5);
                    if (transactions.isEmpty())
                    {
                        System.out.println("No transactions found.");
                    } else
                    {
                        System.out.println("Last 5 transactions:");
                        transactions.forEach(System.out::println);
                    }
                }
                case "5" ->
                {
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

}