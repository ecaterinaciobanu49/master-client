package org.example.services;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static String generateRandomLastName() {
        return faker.name().lastName();
    }

    public static String generateRandomFirstName() {
        return faker.name().firstName();
    }

    public static String generateRandomEmail(String firstName, String lastName) {
        String domain = faker.internet().domainName();
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + domain;
    }

    public static String generateRandomPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public static Date generateRandomAdultBirthDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.YEAR, -18);
        Date maxDate = calendar.getTime();

        calendar.add(Calendar.YEAR, -82);
        Date minDate = calendar.getTime();

        return faker.date().between(minDate, maxDate);
    }

    public static String generateRandomSubjectCode() {
        String letters = faker.regexify("[A-Z]{3}");
        String numbers = faker.regexify("[0-9]{3,4}");
        return letters + numbers;
    }

    public static String generateRandomAccountNumber() {
        return faker.number().digits(10);
    }

    public static double generateRandomBalance() {
        return faker.number().randomDouble(2, 0, 10000);
    }
}
