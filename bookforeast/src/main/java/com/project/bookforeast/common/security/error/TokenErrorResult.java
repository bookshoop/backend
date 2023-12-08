package com.project.bookforeast.common.security.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorResult {

	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
	TOKEN_NEED(HttpStatus.UNAUTHORIZED, "토큰이 필요합니다."),
	TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, "토큰을 전달해주세요")
	;
	
	private final HttpStatus status;
	private final String message;
}
