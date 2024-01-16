package com.project.bookforeast.book.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookErrorResult {
    NOT_REGISTED_BOOK(HttpStatus.BAD_REQUEST, "등록된 책이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
