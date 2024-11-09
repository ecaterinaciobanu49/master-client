package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    private Long loanId;
    private Date startDate;
    private Date expireDate;
    private double outstandingAmount;
    private int percent;
    private LoanStatus status;
    private Customer customer;

    public Loan(Date startDate, Date expireDate, double outstandingAmount, int percent, LoanStatus status, Customer customer) {
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.outstandingAmount = outstandingAmount;
        this.percent = percent;
        this.status = status;
        this.customer = customer;
    }
}
