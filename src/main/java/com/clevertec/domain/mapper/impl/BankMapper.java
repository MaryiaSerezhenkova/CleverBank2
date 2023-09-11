package com.clevertec.domain.mapper.impl;

import com.clevertec.domain.dto.BankDto;
import com.clevertec.domain.entity.Bank;

public class BankMapper extends BaseMapper<Bank, BankDto> {

    @Override
    protected Class<Bank> getEntityClass() {
        return Bank.class;
    }

    @Override
    protected Class<BankDto> getDtoClass() {
        return BankDto.class;
    }
}
