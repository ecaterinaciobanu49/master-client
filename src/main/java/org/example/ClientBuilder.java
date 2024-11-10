package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.models.*;
import org.example.services.RequestSender;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientBuilder {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    private static final String CUSTOMER_CSV_PATH = "src/main/resources/test_customers.csv";
    private static final String ACCOUNT_CSV_PATH = "src/main/resources/test_accounts.csv";
    private static final String TRANSACTION_CSV_PATH = "src/main/resources/test_transactions.csv";
    private static final String LOAN_CSV_PATH = "src/main/resources/test_loans.csv";
    private static final String CARD_CSV_PATH = "src/main/resources/test_cards.csv";

    public static void insertDataBase() {
        insertCustomers();
        insertAccounts();
        insertTransactions();
        insertLoans();
        insertCards();
    }

    public static void insertCustomers() {
        try (CSVReader csvReader = new CSVReader(new FileReader(CUSTOMER_CSV_PATH))) {
            String[] values = null;
            Customer customer = new Customer();
            while ((values = csvReader.readNext()) != null) {
                customer.setSubjectCode(values[0]);
                customer.setLastname(values[1]);
                customer.setFirstname(values[2]);
                customer.setEmail(values[3]);
                customer.setPhone(values[4]);
                customer.setBirthDate(parseDate(values[5]));

                RequestSender.createNewCustomer(customer);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertAccounts() {
        try (CSVReader csvReader = new CSVReader(new FileReader(ACCOUNT_CSV_PATH))) {
            String[] values;
            Account account = new Account();
            while ((values = csvReader.readNext()) != null) {

                account.setAccountNumber(values[0]);
                account.setAccountType(AccountType.valueOf(values[1]));
                account.setBalance(Double.parseDouble(values[2]));
                account.setStatus(Status.valueOf(values[3]));
                Customer customer = RequestSender.getCustomerBySubjectCode(values[4]);
                account.setCustomer(customer);

                RequestSender.addNewAccount(account);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error processing the CSV file: " + e.getMessage(), e);
        }
    }

    public static void insertTransactions() {
        try (CSVReader csvReader = new CSVReader(new FileReader(TRANSACTION_CSV_PATH))) {
            String[] values;
            Transaction transaction = new Transaction();
            while ((values = csvReader.readNext()) != null) {
                transaction.setSum(Double.parseDouble(values[0]));
                transaction.setTransactionDate(parseDate(values[1]));
                transaction.setTransactionType(TransactionType.valueOf(values[2]));
                Account account = RequestSender.getAccountByAccountNumber(values[3]);
                transaction.setAccount(account);

                RequestSender.addNewTransaction(transaction);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error processing the CSV file: " + e.getMessage(), e);
        }
    }

    public static void insertLoans() {
        try (CSVReader csvReader = new CSVReader(new FileReader(LOAN_CSV_PATH))) {
            String[] values;
            Loan loan = new Loan();
            while ((values = csvReader.readNext()) != null) {
                loan.setStartDate(parseDate(values[0]));
                loan.setExpireDate(parseDate(values[1]));
                loan.setOutstandingAmount(Double.parseDouble(values[2]));
                loan.setPercent(Integer.parseInt(values[3]));
                loan.setStatus(LoanStatus.valueOf(values[4]));

                Customer customer = RequestSender.getCustomerBySubjectCode(values[5]);
                loan.setCustomer(customer);

                RequestSender.addNewLoan(loan);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error processing the CSV file: " + e.getMessage(), e);
        }
    }

    public static void insertCards() {
        try (CSVReader csvReader = new CSVReader(new FileReader(CARD_CSV_PATH))) {
            String[] values;
            Card card = new Card();
            while ((values = csvReader.readNext()) != null) {
                card.setCardNumber(values[0]);
                card.setExpireDate(parseDate(values[1]));
                card.setCvv(values[2]);
                Customer customer = RequestSender.getCustomerBySubjectCode(values[3]);
                card.setCustomer(customer);

                RequestSender.addNewCard(card);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error processing the CSV file: " + e.getMessage(), e);
        }
    }

    private static Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
            return null;
        }
    }
}
