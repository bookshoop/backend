package com.project.bookforeast.common.security.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenException extends RuntimeException {

	private final TokenErrorResult tokenErrorResult;
}
