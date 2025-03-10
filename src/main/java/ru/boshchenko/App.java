package ru.boshchenko;

import ru.boshchenko.costomBank.BankAccount;
import ru.boshchenko.costomBank.ConcurrentBank;

import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        ConcurrentBank bank = new ConcurrentBank();

        // Создание счетов
        BankAccount account1 = bank.createAccount(new BigDecimal("5000.00"));
        BankAccount account2 = bank.createAccount(new BigDecimal("5000.00"));

        // Перевод между счетами
        Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2, new BigDecimal("500.00")));
        Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1, new BigDecimal("100.00")));

        transferThread1.start();
        transferThread2.start();

        try {
            transferThread1.join();
            transferThread2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вывод общего баланса
        System.out.println("Total balance: " + bank.getTotalBalance());
    }
}
