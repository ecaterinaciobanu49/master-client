package org.example.models;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {
    private Long customerId;
    private String subjectCode;
    private String lastname;
    private String firstname;
    private String email;
    private String phone;
    private Date birthDate;
}
