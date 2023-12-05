package com.project.bookforeast.user.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorResult {
	USEROBJECT_NOT_EXIST(HttpStatus.BAD_REQUEST, "유저 객체가 존재하지 않습니다."),
	ALREADY_EXIST(HttpStatus.CONFLICT, "이미 등록된 유저가 있습니다."),
	SOCIAL_VALUE_NOTEXIST(HttpStatus.BAD_REQUEST, "소셜 ID나 소셜 provider가 존재하지 않습니다."),
	DUPLICATED_USER_REGISTER(HttpStatus.BAD_REQUEST, "기존에 등록된 유저입니다."),
	ALREADY_USED_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니다."),
	NECESSARY_VALUE_NOTEXIST(HttpStatus.BAD_REQUEST, "회원가입을 위한 필수 값이 존재하지 않습니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 형식이 적절하지 않습니다."), 
	USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
	PASSWORD_EMPTY(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요."),
	MOBILE_EMPTY(HttpStatus.BAD_REQUEST, "휴대폰번호를 입력헤주세요.")
	;
	
	private final HttpStatus status;
	private final String message;
}