package com.project.bookforeast.user.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

import com.project.bookforeast.user.annotation.validator.SocialProviderValidator;

import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = SocialProviderValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SocialProvider {

	String message() default "지원하지 않는 socialProvider 입니다.";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default {}; 
	
}
