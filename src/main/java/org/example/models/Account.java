package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long accountId;
    private String accountNumber;
    private AccountType accountType;
    private double balance;
    private Status status;
    private Customer customer;

    public Account(String accountNumber, AccountType accountType, double balance, Status status, Customer customer) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
        this.customer = customer;
    }
}
