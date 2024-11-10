package org.example.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.models.*;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CSVWriter {

    public static void writeCustomersToCSV(String csvPath) {
        String name = "";
        String surName = "";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath, true))) {
            for (int i = 1; i < 1000; i++) {
                name = DataGenerator.generateRandomFirstName();
                surName = DataGenerator.generateRandomLastName();
                String[] data = {
                        DataGenerator.generateRandomSubjectCode(),
                        name,
                        surName,
                        DataGenerator.generateRandomEmail(name, surName),
                        DataGenerator.generateRandomPhoneNumber(),
                        DataGenerator.generateRandomAdultBirthDate().toString()
                };

                String line = String.join(",", data);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    public static void writeAccountToCsv(String csvPath, String customerPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath, true))) {
            for (int i = 1; i < 500; i++) {
                String[] data = {
                        DataGenerator.generateRandomAccountNumber(),
                        AccountType.SALARY.toString(),
                        String.valueOf(DataGenerator.generateRandomBalance()),
                        Status.ACTIVE.name(),
                        readSubjectCodeFromCsv(customerPath, i)
                };

                String line = String.join(",", data);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    public static void writeTransactionToCSV(String path, String accountPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            for (int i = 1; i < 400; i++) {
                String[] data = {
                        String.valueOf(DataGenerator.generateRandomBalance()),
                        new Date().toString(),
                        TransactionType.TRANSFER.name(),
                        readSubjectCodeFromCsv(accountPath, i)
                };

                String line = String.join(",", data);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    public static void writeLoanToCsv(String filePath, String customerPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (int i = 1; i < 200; i++) {
                Date startDate = DataGenerator.generateRandomNearNowDate();
                String[] data = {
                        startDate.toString(),
                        DataGenerator.addRandomYears(startDate).toString(),
                        String.valueOf(DataGenerator.generateRandomBalance()/(i+1)),
                        String.valueOf(DataGenerator.generateRandomIntInRange()),
                        LoanStatus.ACTIVE.name(),
                        readSubjectCodeFromCsv(customerPath, i*2)
                };

                String line = String.join(",", data);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    public static void writeCardToCsv(String path, String customerPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            for (int i = 1; i < 350; i++) {

                String[] data = {
                        DataGenerator.generateRandomCardNumber(),
                        DataGenerator.generateRandomExpireDate().toString(),
                        DataGenerator.generateRandomCVV(),
                        readSubjectCodeFromCsv(customerPath, i*2)
                };

                String line = String.join(",", data);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }
    private static String readSubjectCodeFromCsv(String filePath, int rowIndex) throws IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = csvReader.readAll();
            if (rowIndex >= 0 && rowIndex < rows.size()) {
                return rows.get(rowIndex)[0];
            }
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
