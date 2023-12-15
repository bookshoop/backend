package com.project.bookforeast.common.domain.annotation.validator;


import java.util.Arrays;
import java.util.List;

import com.project.bookforeast.common.domain.annotation.IsSearchOptional;
import com.project.bookforeast.common.domain.promise.SearchOptional;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SearchOptionalValidator implements ConstraintValidator<IsSearchOptional, String> {
	String recent = SearchOptional.RECENT.getOptional();
	String popular = SearchOptional.POPULAR.getOptional();
	String mine = SearchOptional.LIKE.getOptional();
	String like = SearchOptional.LIKE.getOptional();
	
	private List<String> ALLOWED_OPTIONAL = Arrays.asList(recent, popular, mine, like);
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// optional이 비었을때
		if(value == null || value.trim().isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("optional은 필수값입니다.").addConstraintViolation();
			return false;
		}
		
		// 형식이 잘못된 경우
		if(!ALLOWED_OPTIONAL.contains(value)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("부적절한 optional입니다.").addConstraintViolation();
			return false;
		}
		
		return true;
	}

}
