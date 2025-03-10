package ru.boshchenko.costomBank;

import ru.boshchenko.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private final UUID id = UUID.randomUUID();
    private AtomicReference<BigDecimal> balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new InsufficientFundsException("Сумма указана не корректно");
        }
        balance = new AtomicReference<>(amount);
    }

    public void deposit(BigDecimal amount) {
        lock.lock();
        try {
            if (amount.signum() < 0) {
                throw new InsufficientFundsException("Сумма указана не корректно");
            }
            balance.updateAndGet(oldAmount -> oldAmount.add(amount));
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(BigDecimal amount) {
        lock.lock();
        try {
            if ((amount.signum() < 0) || (amount.compareTo(balance.get()) > 0)) {
                throw new InsufficientFundsException("Сумма указана не корректно");
            }
            balance.updateAndGet(oldAmount -> oldAmount.subtract(amount));
        } finally {
            lock.unlock();
        }
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public BigDecimal getBalance() {
        return balance.get();
    }

    public UUID getId() {
        return id;
    }
}
