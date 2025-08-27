package com.vaultx.model;

// Import statements for utilities and exception
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import com.vaultx.model.exception.InsufficientBalanceException;

/**
 * Account class represents a single user's bank account.
 * It maintains account details, available balance, and all transactions.
 */
public class Account
{
    // Unique account number for this bank account
    private long accountNumber;

    // Name of account holder; this should not change after account creation
    private String accountHolderName;

    // Current balance in the account
    private double balance;

    // Transaction history (deposit and withdraw records)
    private ArrayList<Transaction> transactions;

    /**
     * Constructor to initialize Account object with details.
     * @param accountNumber Unique identifier for account
     * @param accountHolderName Name of the account owner
     * @param balance Starting balance amount
     */
    public Account(long accountNumber, String accountHolderName, double balance)
    {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    // ----------- Getter Methods ------------
    public long getAccountNumber()
    {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }



    /**
     * Custom toString for readable account details in logs and debugging.
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                '}';
    }


    // ----------- Business Logic Methods ------------

    /**
     * Deposit money in account. Thread-safe using synchronized.
     * @param amount Amount to deposit (must be > 0)
     * @throws IllegalArgumentException if amount <= 0
     */

    public synchronized void deposit(double amount)
    {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
        balance += amount;
        transactions.add(new Transaction(generateTransactionId(), Transaction.TransactionType.DEPOSIT, amount, LocalDateTime.now(), "SUCCESS", "Deposit of amount " + amount));

    }


/**
 * Withdraw money from the account. Thread-safe using synchronized.
 * @param amount Amount to withdraw (must be > 0 and ≤ current balance)
 * @throws IllegalArgumentException if amount <= 0
 * @throws InsufficientBalanceException if balance less than withdrawal amount
 */
public synchronized void withdraw(double amount) throws InsufficientBalanceException
{
    if (amount <= 0)
    {
        throw new IllegalArgumentException("Withdraw must be positive");
    }
    if (balance < amount)
    {
        throw new InsufficientBalanceException("Insufficient balance");
    }
    balance -= amount;
    transactions.add(new Transaction(
            generateTransactionId(),
            Transaction.TransactionType.WITHDRAW,
            amount,
            LocalDateTime.now(),
            "SUCCESS",
            "Withdrawal of amount " + amount));
}

    /**
     * Print last 5 transactions (most recent first) - Mini statement for user.
     * Prints a custom message if no transactions are found.
     */
    public List<Transaction> getMiniStatement(int n)
    {
        int size = transactions.size();
        if (n > size) n = size;
        return transactions.subList(size - n, size);
    }



    private String generateTransactionId()
    {
        return "TXN" + System.currentTimeMillis();
    }
}




