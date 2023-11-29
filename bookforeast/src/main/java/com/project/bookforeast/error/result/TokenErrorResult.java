package com.project.bookforeast.error.result;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorResult {

	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
	REFRESH_TOKEN_NEED(HttpStatus.UNAUTHORIZED, "리프레쉬 토큰이 필요합니다.")
	;
	
	private final HttpStatus status;
	private final String message;
}
