package com.clevertec.dao;

import com.clevertec.domain.entity.Bank;


import java.util.List;

public interface BankDao {

    Bank create (Bank bank);
    Bank get (long id);
    List<Bank> getAll();
}
