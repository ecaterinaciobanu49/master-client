import com.github.javafaker.Faker;
import org.example.services.CSVWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVWriterTest {
    private static final String CUSTOMER_CSV_PATH = "src/main/resources/test_customers.csv";
    private static final String ACCOUNT_CSV_PATH = "src/main/resources/test_accounts.csv";
    private static final String TRANSACTION_CSV_PATH = "src/main/resources/test_transactions.csv";
    private static final String LOAN_CSV_PATH = "src/main/resources/test_loans.csv";
    private static final String CARD_CSV_PATH = "src/main/resources/test_cards.csv";

    @Test
    void testWriteCustomersToCSV() {
        CSVWriter.writeCustomersToCSV(CUSTOMER_CSV_PATH);
        assertTrue(new File(CUSTOMER_CSV_PATH).exists());
    }

    @Test
    void testWriteAccountToCsv() {
        CSVWriter.writeAccountToCsv(ACCOUNT_CSV_PATH, CUSTOMER_CSV_PATH);
        assertTrue(new File(ACCOUNT_CSV_PATH).exists());
    }

    @Test
    void testWriteTransactionToCSV() {
        CSVWriter.writeTransactionToCSV(TRANSACTION_CSV_PATH, ACCOUNT_CSV_PATH);
        assertTrue(new File(TRANSACTION_CSV_PATH).exists());
    }

    @Test
    void testWriteLoanToCsv() {
        CSVWriter.writeLoanToCsv(LOAN_CSV_PATH, CUSTOMER_CSV_PATH);
        assertTrue(new File(LOAN_CSV_PATH).exists());
    }

    @Test
    void testWriteCardToCsv() {
        CSVWriter.writeCardToCsv(CARD_CSV_PATH, CUSTOMER_CSV_PATH);
        assertTrue(new File(CARD_CSV_PATH).exists());
    }


    @Test
    void testWriteTime() {
        Faker faker = new Faker();
        CSVWriter.writeTimeToCSV(faker.number().randomNumber(), "src/main/resources/time");
    }
}
