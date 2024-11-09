package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Long cardId;
    private String cardNumber;
    private Date expireDate;
    private String cvv;
    private Customer customer;

    public Card(String cardNumber, Date expireDate, String cvv, Customer customer) {
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.customer = customer;
    }
}
