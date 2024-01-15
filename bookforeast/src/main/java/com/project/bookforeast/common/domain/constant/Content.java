package com.project.bookforeast.common.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Content {
    BOOK("book"),
    PROFILE("profile")
    ;

    private final String contentName;
}
