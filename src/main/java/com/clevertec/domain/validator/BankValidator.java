package com.clevertec.domain.validator;

import com.clevertec.domain.dto.BankDto;

import java.util.ArrayList;
import java.util.List;

public class BankValidator {
    public void validate(BankDto dto) {

        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("DTO is null");
        }
        if (dto.getName()==null || dto.getName().isBlank()) {
            errors.add("Name is not valid");
        }
        if (dto.getName().length()>30){
            errors.add("Name should contain 30 characters");
        }
//		if (errors.size() > 0) {
//			throw new ValidationException( );
//		}
    }
}

