package com.clevertec.service.impl;

import com.clevertec.dao.impl.BankDaoSingleton;
import com.clevertec.domain.mapper.impl.BankMapper;
import com.clevertec.service.BankService;

public class BankServiceSingleton {
    private final BankService bankService;
    private volatile static BankServiceSingleton firstInstance = null;

    public BankServiceSingleton() {
        this.bankService = new BankServiceImpl(BankDaoSingleton.getInstance(), new BankMapper());
    }

    public BankServiceSingleton(BankService bankService) {
        this.bankService = bankService;
    }

    public static BankService getInstance() {
        synchronized (BankServiceSingleton.class) {
            if (firstInstance == null) {
                firstInstance = new BankServiceSingleton();
            }
        }
        return firstInstance.bankService;
    }
}
