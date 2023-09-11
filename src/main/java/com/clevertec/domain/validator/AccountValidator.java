package com.clevertec.domain.validator;

import com.clevertec.domain.dto.AccountDto;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    public void validate(AccountDto dto) {
    List<String> errors = new ArrayList<>();

		if (dto == null) {
        errors.add("DTO is null");
    }
		if (dto.getIban()==null || dto.getIban().isBlank()) {
        errors.add("Name is not valid");
    }
		if (dto.getIban().length()!=28){
        errors.add("Invalid length");
    }
//		if (errors.size() > 0) {
//			throw new ValidationException( );
//		}
}
}