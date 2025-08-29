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
    public static void main(String[] args) {
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
        System.out.println("\n-- Registration --");
        String name = getInput("Enter your full name: ");
        String fatherName = getInput("Enter your father's name: ");
        String motherName = getInput("Enter your mother's name: ");
        String panCard = getValidInput("Enter PAN card number (e.g. ABCDE1234F): ", PAN_PATTERN, "Invalid PAN format! Try again.");
        String email = getValidInput("Enter email address: ", EMAIL_PATTERN, "Invalid email format! Try again.");
        String phone = getValidInput("Enter phone number (10-15 digits): ", PHONE_PATTERN, "Invalid phone number! Try again.");

}