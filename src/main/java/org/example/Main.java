package org.example;


import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

//        System.out.println("Start");
//        ClientBuilder.insertDataBase();
//        System.out.println("Done");

        int clients = 5;
        int cycles = 5;


        List<Thread> listOfClients = new ArrayList<>();

        for (int i = 0; i < clients; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Client.getRequests(cycles);
                }
            });

            listOfClients.add(thread);
        }

        for (Thread thread : listOfClients) {
            thread.start();
        }

        for (Thread thread : listOfClients) {
            thread.join();
        }
    }
}