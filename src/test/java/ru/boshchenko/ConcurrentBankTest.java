package ru.boshchenko;

import org.junit.jupiter.api.Test;
import ru.boshchenko.costomBank.BankAccount;
import ru.boshchenko.costomBank.ConcurrentBank;
import ru.boshchenko.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConcurrentBankTest {

    @Test
    void createAccountShouldAddToTotalBalance() {
        ConcurrentBank bank = new ConcurrentBank();
        BankAccount account = bank.createAccount(new BigDecimal("1500"));
        assertThat(bank.getTotalBalance()).isEqualByComparingTo("1500");
        BankAccount anotherAccount = bank.createAccount(new BigDecimal("500"));
        assertThat(bank.getTotalBalance()).isEqualByComparingTo("2000");
    }

    @Test
    void transferShouldUpdateBalancesCorrectly() {
        ConcurrentBank bank = new ConcurrentBank();
        BankAccount acc1 = bank.createAccount(new BigDecimal("1000"));
        BankAccount acc2 = bank.createAccount(BigDecimal.ZERO);

        bank.transfer(acc1, acc2, new BigDecimal("500"));

        assertThat(acc1.getBalance()).isEqualByComparingTo("500");
        assertThat(acc2.getBalance()).isEqualByComparingTo("500");
        assertThat(bank.getTotalBalance()).isEqualByComparingTo("1000");
    }

    @Test
    void transferWithInsufficientFundsShouldThrow() {
        ConcurrentBank bank = new ConcurrentBank();
        BankAccount acc1 = bank.createAccount(new BigDecimal("200"));
        BankAccount acc2 = bank.createAccount(BigDecimal.ZERO);

        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> bank.transfer(acc1, acc2, new BigDecimal("300"))
        );

        assertThat(exception.getMessage()).contains("Сумма указана не корректно");
        assertThat(acc1.getBalance()).isEqualByComparingTo("200");
        assertThat(acc2.getBalance()).isEqualByComparingTo("0");
    }

    @Test
    void transferWithNegativeAmountShouldThrow() {
        ConcurrentBank bank = new ConcurrentBank();
        BankAccount acc1 = bank.createAccount(new BigDecimal("1000"));
        BankAccount acc2 = bank.createAccount(BigDecimal.ZERO);

        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> bank.transfer(acc1, acc2, new BigDecimal("-300"))
        );

        assertThat(exception.getMessage()).contains("Сумма указана не корректно");
        assertThat(acc1.getBalance()).isEqualByComparingTo("1000");
        assertThat(acc2.getBalance()).isEqualByComparingTo("0");
    }

    @Test
    void totalBalanceShouldRemainConsistentAfterTransfers() {
        ConcurrentBank bank = new ConcurrentBank();
        BankAccount acc1 = bank.createAccount(new BigDecimal("1000"));
        BankAccount acc2 = bank.createAccount(new BigDecimal("2000"));

        BigDecimal totalBefore = bank.getTotalBalance();
        bank.transfer(acc1, acc2, new BigDecimal("500"));
        bank.transfer(acc2, acc1, new BigDecimal("1000"));
        bank.transfer(acc1, acc2, new BigDecimal("200"));

        assertThat(bank.getTotalBalance()).isEqualByComparingTo(totalBefore);
    }

    @Test
    void concurrentTransfersShouldKeepTotalBalanceConsistent() throws InterruptedException {
        ConcurrentBank bank = new ConcurrentBank();
        BankAccount acc1 = bank.createAccount(new BigDecimal("100000"));
        BankAccount acc2 = bank.createAccount(new BigDecimal("100000"));
        BigDecimal initialTotal = bank.getTotalBalance();

        int threadsCount = 10;
        int transfersPerThread = 1000;
        Runnable task = () -> {
            for (int i = 0; i < transfersPerThread; i++) {
                bank.transfer(acc1, acc2, BigDecimal.ONE);
                bank.transfer(acc2, acc1, BigDecimal.ONE);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        assertThat(bank.getTotalBalance()).isEqualByComparingTo(initialTotal);
        assertThat(acc1.getBalance()).isPositive();
        assertThat(acc2.getBalance()).isPositive();
    }
}
