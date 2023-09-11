package com.clevertec.service;

import com.clevertec.domain.dto.BankDto;
import com.clevertec.domain.entity.Bank;

import java.util.List;

public interface BankService extends IService<Bank, BankDto> {
    List<Bank> getAll();
}
