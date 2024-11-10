package org.example;

import com.github.javafaker.Faker;
import org.example.models.*;
import org.example.services.DataGenerator;
import org.example.services.RequestSender;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Client {

    public static List<String> customersCodes = Arrays.asList("OAJ326",
            "ANF998", "JJO8475", "TOO8794", "YYU946", "CZG3697",
            "ACG3101", "DRZ734", "KVY8017", "TCL0846", "IRA0105",
            "CIB636", "NUN3581", "MVE4234", "GBY7380", "UZE558",
            "PBG159", "QCC6525"
    );

    private static List<String> accountNumbers = Arrays.asList("3407083329",
            "8608759873", "4125463538", "6745777332", "4944567675"
    );

    private static final Faker faker = new Faker();

    public static void getRequests(int repetitions) {
        for (int i = 0; i < repetitions; i++) {
            String subjectCode = DataGenerator.generateRandomSubjectCode();
            customersCodes.add(subjectCode);
            insertNewCustomer(subjectCode, DataGenerator.generateRandomFirstName(),
                    DataGenerator.generateRandomLastName(), DataGenerator.generateRandomEmail("flower", "sun"),
                    DataGenerator.generateRandomPhoneNumber(), DataGenerator.generateRandomAdultBirthDate());
            getCustomerBySubjectCode(customersCodes.get(i));
            getAccountByAccountNumber(accountNumbers.get(faker.number().numberBetween(0, accountNumbers.size())));
            getTransactionById((long) faker.number().numberBetween(0, 50));
            getLoanById((long) faker.number().numberBetween(0, 75));

            String accountN = DataGenerator.generateRandomAccountNumber();
            accountNumbers.add(accountN);
            insertNewAccount(accountN, AccountType.DEPOSIT, DataGenerator.generateRandomBalance(),
                    Status.ACTIVE, customersCodes.get(faker.number().numberBetween(0, customersCodes.size())));

            insertNewLoan(new Date(), DataGenerator.generateRandomExpireDate(),
                    (double) i * customersCodes.size() + 100, faker.number().numberBetween(1, 15),
                    LoanStatus.ACTIVE, customersCodes.get(faker.number().numberBetween(0, customersCodes.size())));
            getCardById((long) i + faker.number().numberBetween(1, 15));

            insertNewTransaction(DataGenerator.generateRandomBalance(), TransactionType.TRANSFER, accountNumbers.get(faker.number().numberBetween(0, accountNumbers.size())));
            insertNewCard(DataGenerator.generateRandomCardNumber(), DataGenerator.generateRandomExpireDate(),
                    DataGenerator.generateRandomCVV(),
                    customersCodes.get(faker.number().numberBetween(0, customersCodes.size())));

            getAllAccountsBySubjectCode(customersCodes.get(i));
            getAllTransactionsByAccountId((long) faker.number().numberBetween(1, 50)); // Example account ID
            getAllLoansForCustomer(customersCodes.get(i));
            getAllCardsForCustomer(customersCodes.get(i));

            updateCustomerEmail(subjectCode, "updated.email" + i + "@example.com");
            updateAccountBalance(accountNumbers.get(i % accountNumbers.size()), faker.number().randomDouble(2, 1000, 5000));

            String closeAccountNumber = accountNumbers.get(faker.number().numberBetween(0, accountNumbers.size()));
            closeAccount(closeAccountNumber);
            Long loanId = (long) faker.number().numberBetween(1, 50);
            updateLoanOutstandingAmount(loanId, faker.number().randomDouble(2, 100, 500));
            insertNewCard(DataGenerator.generateRandomCardNumber(), DataGenerator.generateRandomExpireDate(),
                    DataGenerator.generateRandomCVV(), customersCodes.get(faker.number().numberBetween(0, customersCodes.size())));

        }
    }

    public static void getAllAccountsBySubjectCode(String subjectCode) {
        List<Account> accounts = RequestSender.getAllAccountsBySubjectCode(subjectCode);
        System.out.println("All Accounts for Customer with Subject Code (" + subjectCode + "): " + accounts);
    }

    public static void getAllTransactionsByAccountId(Long accountId) {
        List<Transaction> transactions = RequestSender.getAllTransactionByAccountId(accountId);
        System.out.println("All Transactions for Account ID (" + accountId + "): " + transactions);
    }

    public static void getAllLoansForCustomer(String subjectCode) {
        List<Loan> loans = RequestSender.retrieveLoansForACustomer(subjectCode);
        System.out.println("All Loans for Customer with Subject Code (" + subjectCode + "): " + loans);
    }

    public static void getAllCardsForCustomer(String subjectCode) {
        List<Card> cards = RequestSender.getCardsBySubjectCode(subjectCode);
        System.out.println("All Cards for Customer with Subject Code (" + subjectCode + "): " + cards);
    }

    public static void getCustomerBySubjectCode(String subjectCode) {
        Customer customer = RequestSender.getCustomerBySubjectCode(subjectCode);
        System.out.println("Customer Retrieved by Subject Code (" + subjectCode + "): " + customer);
    }

    public static void getAccountByAccountNumber(String accountNumber) {
        Account account = RequestSender.getAccountByAccountNumber(accountNumber);
        System.out.println("Account Retrieved by Account Number (" + accountNumber + "): " + account);
    }

    public static void getTransactionById(Long transactionId) {
        Transaction transaction = RequestSender.getTransactionById(transactionId);
        System.out.println("Transaction Retrieved by Transaction ID (" + transactionId + "): " + transaction);
    }

    public static void getLoanById(Long loanId) {
        Loan loan = RequestSender.getLoanById(loanId);
        System.out.println("Loan Retrieved by Loan ID (" + loanId + "): " + loan);
    }

    public static void getCardById(Long cardId) {
        Card card = RequestSender.getCardById(cardId);
        System.out.println("Card Retrieved by Card ID (" + cardId + "): " + card);
    }

    public static void insertNewCustomer(String subjectCode, String firstName, String lastName, String email, String phone, Date birthDate) {
        Customer customer = new Customer();
        customer.setSubjectCode(subjectCode);
        customer.setFirstname(firstName);
        customer.setLastname(lastName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setBirthDate(birthDate);

        Customer createdCustomer = RequestSender.createNewCustomer(customer);
        System.out.println("Inserted New Customer: " + createdCustomer);
    }

    public static void insertNewAccount(String accountNumber, AccountType accountType, double balance, Status status, String customerSubjectCode) {
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setBalance(balance);
        account.setStatus(status);

        Customer customer = RequestSender.getCustomerBySubjectCode(customerSubjectCode);
        account.setCustomer(customer);

        Account createdAccount = RequestSender.addNewAccount(account);
        System.out.println("Inserted New Account: " + createdAccount);
    }

    public static void insertNewTransaction(double sum, TransactionType transactionType, String accountNumber) {
        Transaction transaction = new Transaction();
        transaction.setSum(sum);
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(transactionType);

        Account account = RequestSender.getAccountByAccountNumber(accountNumber);
        transaction.setAccount(account);

        Transaction createdTransaction = RequestSender.addNewTransaction(transaction);
        System.out.println("Inserted New Transaction: " + createdTransaction);
    }

    public static void insertNewLoan(Date startDate, Date expireDate, double outstandingAmount, int percent, LoanStatus status, String customerSubjectCode) {
        Loan loan = new Loan();
        loan.setStartDate(startDate);
        loan.setExpireDate(expireDate);
        loan.setOutstandingAmount(outstandingAmount);
        loan.setPercent(percent);
        loan.setStatus(status);

        Customer customer = RequestSender.getCustomerBySubjectCode(customerSubjectCode);
        loan.setCustomer(customer);

        Loan createdLoan = RequestSender.addNewLoan(loan);
        System.out.println("Inserted New Loan: " + createdLoan);
    }

    public static void insertNewCard(String cardNumber, Date expireDate, String cvv, String customerSubjectCode) {
        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setExpireDate(expireDate);
        card.setCvv(cvv);

        Customer customer = RequestSender.getCustomerBySubjectCode(customerSubjectCode);
        card.setCustomer(customer);

        Card createdCard = RequestSender.addNewCard(card);
        System.out.println("Inserted New Card: " + createdCard);
    }

    public static void updateCustomerEmail(String subjectCode, String newEmail) {
        Customer updatedCustomer = RequestSender.editCustomerEmail(subjectCode, newEmail);
        System.out.println("Updated Customer Email: " + updatedCustomer);
    }

    public static void updateAccountBalance(String accountNumber, double newBalance) {
        Account updatedAccount = RequestSender.updateAccountBalance(accountNumber, newBalance);
        System.out.println("Updated Account Balance: " + updatedAccount);
    }

    public static void closeAccount(String accountNumber) {
        boolean isClosed = RequestSender.closeAccount(accountNumber);
        System.out.println("Account Closed Status for " + accountNumber + ": " + isClosed);
    }

    public static void updateLoanOutstandingAmount(Long loanId, double paymentAmount) {
        Loan updatedLoan = RequestSender.updateOutstandingAmount(loanId, paymentAmount);
        System.out.println("Updated Loan Outstanding Amount: " + updatedLoan);
    }

    public static void closeLoan(Long loanId) {
        boolean isClosed = RequestSender.closeLoan(loanId);
        System.out.println("Loan Closed Status for Loan ID " + loanId + ": " + isClosed);
    }

}
