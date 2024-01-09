package com.project.bookforeast.code.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CodeException {

	private final CodeErrorResult codeErrorResult;
}
