package ru.boshchenko;

import org.junit.jupiter.api.Test;
import ru.boshchenko.costomBank.BankAccount;
import ru.boshchenko.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    @Test
    public void creatAccountWithPositiveBalance() {
        BigDecimal balance = new BigDecimal("1000");

        BankAccount bankAccount = new BankAccount(balance);

        assertThat(bankAccount.getBalance()).isEqualByComparingTo(balance);
    }

    @Test
    public void creatAccountWithNegativeBalance() {
        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> new BankAccount(new BigDecimal("-100"))
        );

        assertThat(exception.getMessage()).contains("Сумма указана не корректно");
    }

    @Test
    public void depositPositive() {
        BankAccount account = new BankAccount(new BigDecimal("1000"));

        account.deposit(new BigDecimal("300"));

        assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal("1300"));
    }

    @Test
    public void depositNegative() {
        BankAccount account = new BankAccount(new BigDecimal("1000"));

        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> account.deposit(new BigDecimal("-500"))
        );

        assertThat(exception.getMessage()).contains("Сумма указана не корректно");
    }

    @Test
    public void withdrawCorrect() {
        BankAccount account = new BankAccount(new BigDecimal("1000"));

        account.withdraw(new BigDecimal("500"));

        assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal("500"));
    }

    @Test
    public void withdrawNegative() {
        BankAccount account = new BankAccount(new BigDecimal("1000"));

        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> account.withdraw(new BigDecimal("-500"))
        );

        assertThat(exception.getMessage()).contains("Сумма указана не корректно");
    }

    @Test
    public void withdrawWithLargerBalanceAmount() {
        BankAccount account = new BankAccount(new BigDecimal("1000"));

        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> account.withdraw(new BigDecimal("2500"))
        );

        assertThat(exception.getMessage()).contains("Сумма указана не корректно");
    }

    @Test
    void concurrentDepositsShouldCorrectlyUpdateBalance() throws InterruptedException {
        BankAccount account = new BankAccount(BigDecimal.ZERO);
        int threadsCount = 10;
        int depositsPerThread = 1000;
        BigDecimal amount = BigDecimal.ONE;

        Runnable task = () -> {
            for (int i = 0; i < depositsPerThread; i++) {
                account.deposit(amount);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        BigDecimal expected = BigDecimal.valueOf(threadsCount * depositsPerThread);
        assertThat(account.getBalance()).isEqualByComparingTo(expected);
    }
}
