package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.example.constants.Constants.*;

public class RequestSender {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static Customer createNewCustomer(Customer customer) {
        try {
            String requestBody = objectMapper.writeValueAsString(customer);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(URI.create(BASE_URL + POST_CUSTOMER))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.statusCode());
            System.out.println(response.body());

            return objectMapper.readValue(response.body(), Customer.class);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public static Customer getCustomerBySubjectCode(String subjectCode) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(BASE_URL + GET_CUSTOMER_BY_CUSTOMER_CODE + subjectCode))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), Customer.class);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public static Customer editCustomerEmail(String subjectCode, String newEmail) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(newEmail))
                    .uri(URI.create(BASE_URL + PUT_CUSTOMER_EMAIL + subjectCode))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), Customer.class);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public static boolean deleteCustomer(String subjectCode) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .DELETE()
                    .uri(URI.create(BASE_URL + DELETE_CUSTOMER + subjectCode))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return true;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    public static Account addNewAccount(Account account) {
        try {
            String requestBody = objectMapper.writeValueAsString(account);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(URI.create(BASE_URL + POST_ACCOUNT))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), Account.class);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public static List<Account> getAllAccountsBySubjectCode(String subjectCode) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(BASE_URL + GET_ACCOUNTS_BY_CUSTOMER_CODE + subjectCode))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), new TypeReference<List<Account>>() {});
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public static Account getAccountByAccountNumber(String accountNumber) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(BASE_URL + GET_ACCOUNT_BY_NUMBER + accountNumber))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), Account.class);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public static Account updateAccountBalance(String accountNumber, Double balance) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(String.valueOf(balance)))
                    .uri(URI.create(BASE_URL + PUT_ACCOUNT_BALANCE + accountNumber))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), Account.class);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public static boolean closeAccount(String accountNumber) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .uri(URI.create(BASE_URL + PUT_CLOSE_ACCOUNT + accountNumber))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (IOException | InterruptedException ignored) {
            return false;
        }
    }

}
