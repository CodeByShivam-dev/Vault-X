package com.vaultx.app;
import java.util.UUID;
import java.util.Random;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vaultx.otp.OtpService;

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

    private static final OtpService otpService = new OtpService();


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
                case "1":
                    withdrawMoney(account);
                    break;
                case "2":
                    depositMoney(account);
                    break;
                case "3":
                    System.out.println("Current balance: " + account.getBalance());
                    break;
                case "4":
                    List<Transaction> transactions = account.getMiniStatement(5);
                    if (transactions.isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        System.out.println("Last 5 transactions:");
                        for (Transaction t : transactions) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "5":
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }

        }
    }

    private static void withdrawMoney(Account account)
    {
        double amount = readAmount("Enter amount to withdraw: ");
        try
        {
            account.withdraw(amount);
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void depositMoney(Account account)
    {
        double amount = readAmount("Enter amount to deposit: ");
        try {
            account.deposit(amount);
            System.out.println("Deposit successful. New balance: " + account.getBalance());
            // Send deposit confirmation message
            User user = findUserByAccount(account);
            if (user != null) {
                sendTransactionConfirmation(user.getPhone(), "credited", amount, account.getBalance());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void sendTransactionConfirmation(String phone, String type, double amount, double newBalance)
    {
        String messageBody = String.format(
                "Dear customer, your account has been %s with ₹%.2f. Your new balance is ₹%.2f. Thank you for banking with VaultX!",
                type, amount, newBalance
        );
        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + phone),
                new PhoneNumber("whatsapp:+14155238886"), // Twilio sandbox WhatsApp number
                messageBody
        ).create();
        System.out.println("Transaction confirmation sent with SID: " + message.getSid());
    }

    private static User findUserByAccount(Account account)
    {
        for (User user : usersByPhone.values()) {
            if (user.getAccountNumber() == account.getAccountNumber()) {
                return user;
            }
        }
        return null;
    }



    private static String getInput(String prompt)
    {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    private static String getValidInput(String prompt, Pattern pattern, String errorMsg)
    {
        while (true)
        {
            String input = getInput(prompt);
            if (!pattern.matcher(input).matches()) {
                System.out.println(errorMsg);
            } else {
                return input;
            }
        }
    }

    private static double readAmount(String prompt)
    {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    throw new NumberFormatException();
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid positive number.");
            }
        }
    }

    private static void sendOtp(String phone)
    {
        String otp = otpService.sendOtp(phone); // Generates OTP using your OtpService class
        otpStore.put(phone, otp);               // Stores OTP for later verification
    }


    // Sends OTP—replace this with real API for production

    // Verifies entered OTP matches stored OTP
    private static boolean verifyOtp(String phone, String otp) {
        String storedOtp = otpStore.get(phone);
        return otp != null && otp.equals(storedOtp);
    }


    // Generates a unique account number not in use
    private static long generateUniqueAccountNumber()
    {
        long number;
        Random rnd = new Random();
        do {
            number = 1000000000L + rnd.nextInt(900000000);
        } while (accountsByNumber.containsKey(number));
        return number;
    }

}