package com.vaultx.model;

import java.time.LocalDateTime;
import com.vaultx.model.exception.InsufficientBalanceException;
/**
 * Transaction class represents a bank account transaction.
 * It records details like transaction ID, type (deposit, withdraw, transfer),
 * amount, date/time, status, and description.
 */
public class Transaction
{
    /**
     * Enum to define possible types of transactions
     */
    public enum TransactionType { DEPOSIT, WITHDRAW, TRANSFER }

    // Unique ID of the transaction
    public String transactionId;

    // Type of transaction, represented as enum for type safety
    public TransactionType transactionType;

    // Transaction amount
    private double amount;

    // Date and time when transaction occurred
    private LocalDateTime timestamp;

    // Status of transaction e.g. SUCCESS or FAILURE
    private String status;

    // Detailed description or notes related to transaction
    private String description;

    /**
     * Constructor to initialize a transaction record.
     * @param transactionId Unique identifier
     * @param transactionType Type of transaction (enum)
     * @param amount Transaction amount
     * @param timestamp Timestamp of transaction event
     * @param status Status of transaction
     * @param description Additional descriptive details
     */
    public Transaction(String transactionId, TransactionType transactionType, double amount,
                       LocalDateTime timestamp, String status, String description)
    {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
        this.description = description;
    }

    // Getter for transaction ID
public String getTransactionId()
    {
        return transactionId;
    }

    /**
     * Getter for transaction type as String.
     * Uses enum's name() method to get exact constant name.
     * Both name() and toString() return similar values here.
     */
    public String getTransactionType()
    {
        return transactionType.name();  // // safer to rely on name() which is final
    }


    public double getAmount()
    {
        return amount;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Overriding toString for easier logging/debugging with full details
     */
    @Override
    public String toString()
    {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}