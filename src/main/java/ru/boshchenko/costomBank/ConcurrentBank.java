package ru.boshchenko.costomBank;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class ConcurrentBank {

    private ConcurrentHashMap<UUID, BankAccount> accounts = new ConcurrentHashMap();

    public BankAccount createAccount(BigDecimal balance) {
        BankAccount bankAccount = new BankAccount(balance);
        accounts.put(bankAccount.getId(), bankAccount);
        return bankAccount;
    }

    public void transfer(BankAccount accountForDebiting, BankAccount accountForReplenishment, BigDecimal amount) {
        if (accountForDebiting == accountForReplenishment) {
            throw new IllegalArgumentException("Нельзя переводить на тот же счёт");
        }

        BankAccount first = accountForDebiting.getId().compareTo(accountForReplenishment.getId()) < 0 ? accountForDebiting : accountForReplenishment;
        BankAccount second = (accountForDebiting == first) ? accountForReplenishment : accountForDebiting;

        try {
            first.lock();
            second.lock();

            accountForDebiting.withdraw(amount);
            accountForReplenishment.deposit(amount);
        } finally {
            second.unlock();
            first.unlock();
        }
    }

    public BigDecimal getTotalBalance() {
        BigDecimal sum = new BigDecimal("0");
        for (Map.Entry<UUID, BankAccount> el : accounts.entrySet()) {
            sum = sum.add(el.getValue().getBalance());
        }
        return sum;
    }
}
