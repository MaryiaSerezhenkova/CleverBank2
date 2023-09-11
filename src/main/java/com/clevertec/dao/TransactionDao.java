package com.clevertec.dao;

public interface TransactionDao {

    void makeTransfer();
    void refill(long accountId, double amount);
    void withdrowal(long accountId, double amount);

}
