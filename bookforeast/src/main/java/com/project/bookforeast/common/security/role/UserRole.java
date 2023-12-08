package com.project.bookforeast.common.security.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

	MANAGER("MANAGER"), USER("USER");

	private String role;
}
