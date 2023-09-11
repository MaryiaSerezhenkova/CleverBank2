package com.clevertec.dao.impl;

import com.clevertec.dao.BankDao;
import com.clevertec.dbo.HikariCPDataSource;

public class BankDaoSingleton {
    private BankDao bankDao;
    private volatile static BankDaoSingleton instance;

    public BankDaoSingleton() {
        try {
            this.bankDao = new BankDaoImpl (HikariCPDataSource.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static BankDao getInstance() {
        if (instance == null) {
            synchronized (BankDaoSingleton.class) {
                if (instance == null) {
                    instance = new BankDaoSingleton();
                }
            }
        }
        return instance.bankDao;
    }

}
