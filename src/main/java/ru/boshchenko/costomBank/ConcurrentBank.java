package ru.boshchenko.costomBank;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentBank {

    private Set<BankAccount> accounts = new CopyOnWriteArraySet<>();
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount createAccount(BigDecimal balance) {
        BankAccount bankAccount = new BankAccount(balance);
        accounts.add(bankAccount);
        return bankAccount;
    }

    public void transfer(BankAccount accountForDebiting, BankAccount accountForReplenishment, BigDecimal amount) {
        lock.lock();
        try {
            accountForDebiting.withdraw(amount);
            accountForReplenishment.deposit(amount);
        } finally {
            lock.unlock();
        }
    }

    public BigDecimal getTotalBalance() {
        lock.lock();
        try {
            BigDecimal sum = new BigDecimal("0");
            for (BankAccount el : accounts) {
                sum = sum.add(el.getBalance());
            }
            return sum;
        } finally {
            lock.unlock();
        }
    }
}
