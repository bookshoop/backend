package com.project.bookforeast.common.domain.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParsingErrorResult {
	PARSING_FAIL(HttpStatus.BAD_REQUEST, "데이터 변환에 실패하였씁니다."),
	;
	
	private final HttpStatus status;
	private final String message;
}
