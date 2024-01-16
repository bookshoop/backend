package com.project.bookforeast.book.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookException extends RuntimeException {

    private final BookErrorResult bookErrorResult;
}
