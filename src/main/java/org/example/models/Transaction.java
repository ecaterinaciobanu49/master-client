package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long transactionId;
    private double sum;
    private Date transactionDate;
    private TransactionType transactionType;
    private Account account;

    public Transaction(double sum, Date transactionDate, TransactionType transactionType, Account account) {
        this.sum = sum;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.account = account;
    }
}
