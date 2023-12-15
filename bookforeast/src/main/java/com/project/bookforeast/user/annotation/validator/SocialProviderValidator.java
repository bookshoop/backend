package com.project.bookforeast.user.annotation.validator;


import java.util.Arrays;
import java.util.List;

import com.project.bookforeast.common.domain.promise.SocialProvider;
import com.project.bookforeast.user.annotation.IsSocialProvider;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SocialProviderValidator implements ConstraintValidator<IsSocialProvider, String> {
	String kakao = SocialProvider.KAKAO.getProvider();
	String naver = SocialProvider.NAVER.getProvider();
	String apple = SocialProvider.APPLE.getProvider();
	private List<String> ALLOWED_PROVIDERS = Arrays.asList(kakao, naver, apple);
	
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 소셜 프로바이더가 비어있을때
		if(value == null || value.trim().isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("social provider는 필수값입니다.").addConstraintViolation();
			return false;
		}
		
		// 형식이 잘못된 경우
		if(!ALLOWED_PROVIDERS.contains(value)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
			return false;
		}
		
		return true;
	}

}
