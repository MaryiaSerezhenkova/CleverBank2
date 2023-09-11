package com.clevertec.service.impl;

import com.clevertec.dao.AccountDao;
import com.clevertec.domain.dto.AccountDto;
import com.clevertec.domain.entity.Account;
import com.clevertec.domain.mapper.impl.AccountMapper;
import com.clevertec.service.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountDao dao;
    private AccountMapper mapper;

    public AccountServiceImpl(AccountDao dao, AccountMapper accountMapper) {
        this.dao = dao;
    }

    @Override
    public AccountDto create(AccountDto item) {
        Account account = new Account();
        account.setDtCreate(item.getDtCreate());
        account.setDtUpdate(account.getDtCreate());
        account.setIban(item.getIban());
        account.setCurrency(item.getCurrency());
        account.setBankId(item.getBankId());
        account.setUserId(item.getUserId());
        account.setBalance(item.getBalance());
        return mapper.toDTO(dao.create(account));
    }

    @Override
    public AccountDto read(long id) {
        return mapper.toDTO(dao.get(id));
    }

    @Override
    public List<Account> userAccounts(long userId) {
        return dao.userAccounts(userId);
    }

    @Override
    public List<Account> bankAccounts(long bankId) {
        return dao.bankAccounts(bankId);
    }
}
