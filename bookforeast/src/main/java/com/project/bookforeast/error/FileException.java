package com.project.bookforeast.error;

import com.project.bookforeast.error.result.FileErrorResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileException extends RuntimeException {
	
	private final FileErrorResult fileErrorResult;
	
}
