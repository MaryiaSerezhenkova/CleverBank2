package com.clevertec.dao.impl;

import com.clevertec.dao.AccountDao;
import com.clevertec.dbo.HikariCPDataSource;

public class AccountDaoSingleton {
    private AccountDao dao;
    private volatile static AccountDaoSingleton instance;

    public AccountDaoSingleton() {
        try {
            this.dao = new AccountDaoImpl (HikariCPDataSource.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static AccountDao getInstance() {
        if (instance == null) {
            synchronized (AccountDaoSingleton.class) {
                if (instance == null) {
                    instance = new AccountDaoSingleton();
                }
            }
        }
        return instance.dao;
    }
}
