package com.clevertec.dao.impl;

import com.clevertec.dao.AccountDao;
import com.clevertec.dao.TransactionDao;
import com.clevertec.domain.entity.Account;

public class TransactionDaoImpl implements TransactionDao {
    private AccountDao accDao;


    @Override
    public void makeTransfer() {

    }

    @Override
    public void refill(long accountId, double amount) {
    Account acc = accDao.get(accountId);
        if (acc == null) {
            throw new IllegalArgumentException("Неверные реквизиты счета");
        }
    acc.setBalance(acc.getBalance()+amount);

    }

    @Override
    public void withdrowal(long accountId, double amount) {

    }
}
