package com.project.bookforeast.error;


import com.project.bookforeast.error.result.ParsingErrorResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParsingException extends RuntimeException {
	
	private final ParsingErrorResult parsingErrorResult;
}
