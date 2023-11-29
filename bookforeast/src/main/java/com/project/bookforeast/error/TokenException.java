package com.project.bookforeast.error;

import com.project.bookforeast.error.result.TokenErrorResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenException extends RuntimeException {

	private final TokenErrorResult tokenErrorResult;
}
