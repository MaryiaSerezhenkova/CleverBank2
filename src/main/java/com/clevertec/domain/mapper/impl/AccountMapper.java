package com.clevertec.domain.mapper.impl;

import com.clevertec.domain.dto.AccountDto;
import com.clevertec.domain.entity.Account;

public class AccountMapper extends BaseMapper<Account, AccountDto> {
    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    protected Class<AccountDto> getDtoClass() {
        return AccountDto.class;
    }
}
