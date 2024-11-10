package org.example.services;

import com.github.javafaker.Faker;

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
    public static int generateRandomIntInRange() {
        return faker.number().numberBetween(1, 31);
    }
    public static Date generateRandomNearNowDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date maxDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, -14);
        Date minDate = calendar.getTime();
        return faker.date().between(minDate, maxDate);
    }
    public static Date addRandomYears(Date date) {
        int yearsToAdd = faker.number().numberBetween(1, 11);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearsToAdd);

        return calendar.getTime();
    }

    public static String generateRandomCardNumber() {
        return faker.finance().creditCard();
    }

    public static Date generateRandomExpireDate() {
        Calendar calendar = Calendar.getInstance();

        int yearsToAdd = faker.number().numberBetween(1, 6);

        calendar.add(Calendar.YEAR, yearsToAdd);

        int month = faker.number().numberBetween(0, 12); // Months are 0-indexed in Calendar
        calendar.set(Calendar.MONTH, month);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    public static String generateRandomCVV() {
        return faker.number().digits(3);
    }


}
