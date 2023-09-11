package com.clevertec.domain.validator;

import com.clevertec.domain.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

	public void validate(UserDto dto) {

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