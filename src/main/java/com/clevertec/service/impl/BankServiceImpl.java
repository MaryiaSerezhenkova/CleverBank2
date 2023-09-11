package com.clevertec.service.impl;

import com.clevertec.dao.BankDao;
import com.clevertec.domain.dto.BankDto;
import com.clevertec.domain.entity.Bank;
import com.clevertec.domain.mapper.impl.BankMapper;
import com.clevertec.service.BankService;

import java.util.List;

public class BankServiceImpl implements BankService {
    private final BankDao bankDao;
    private BankMapper bankMapper;

    public BankServiceImpl(BankDao bankDao, BankMapper bankMapper) {
        this.bankDao = bankDao;
    }

    @Override
    public List<Bank> getAll() {
        return bankDao.getAll();
    }

    @Override
    public BankDto create(BankDto item) {
        Bank bank = new Bank();
        bank.setDtCreate(item.getDtCreate());
        bank.setName(item.getName());
        return bankMapper.toDTO(bankDao.create(bank));
    }

    @Override
    public BankDto read(long id) {
        return bankMapper.toDTO(bankDao.get(id));
    }
}
