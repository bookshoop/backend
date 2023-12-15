package com.project.bookforeast.common.domain.promise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchOptional {

	RECENT("RECENT"), POPULAR("POPULAR"), MINE("MINE"), LIKE("LIKE");
	
	private String optional;
}
