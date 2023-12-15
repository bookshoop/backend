package com.project.bookforeast.common.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

import com.project.bookforeast.common.domain.annotation.validator.SearchOptionalValidator;

import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = SearchOptionalValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsSearchOptional {
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default {}; 
}
