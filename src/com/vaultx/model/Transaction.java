package com.vaultx.model;
import java.time.LocalDateTime;

public class Transaction
{
    public enum TransactionType { DEPOSIT, WITHDRAW, TRANSFER }

    public String transactionId;
    public String transactionType;
    private double amount;
    private LocalDateTime timestamp;
    private String status;
    private String description;

    public Transaction(String transactionId, String transactionType, double amount, LocalDateTime timestamp,String status, String description)
    {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
        this.description = description;
    }


    public String getTransactionId()
    {
        return transactionId;
    }

    public String getTransactionType()
    {
        return transactionType;
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