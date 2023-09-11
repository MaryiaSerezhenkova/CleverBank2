package com.clevertec.service.impl;

import com.clevertec.dao.impl.AccountDaoSingleton;
import com.clevertec.domain.mapper.impl.AccountMapper;
import com.clevertec.service.AccountService;

public class AccountServiceSingleton {
    private final AccountService service;
    private volatile static AccountServiceSingleton firstInstance = null;

    public AccountServiceSingleton() {
        this.service = new AccountServiceImpl(AccountDaoSingleton.getInstance(), new AccountMapper());
    }

    public AccountServiceSingleton(AccountService service) {
        this.service = service;
    }

    public static AccountService getInstance() {
        synchronized (AccountServiceSingleton.class) {
            if (firstInstance == null) {
                firstInstance = new AccountServiceSingleton();
            }
        }
        return firstInstance.service;
    }
}

