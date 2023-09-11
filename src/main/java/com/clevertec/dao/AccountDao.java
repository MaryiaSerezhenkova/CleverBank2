package com.clevertec.dao;

import com.clevertec.domain.entity.Account;

import java.util.List;

public interface AccountDao {

    Account create(Account acc);
    Account get(long id);
    List<Account> userAccounts(long userId);
    List<Account> bankAccounts(long bankId);
}
