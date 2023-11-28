package com.project.bookforeast.error;

import com.project.bookforeast.error.result.UserErrorResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
	
	private final UserErrorResult userErrorResult;
}
