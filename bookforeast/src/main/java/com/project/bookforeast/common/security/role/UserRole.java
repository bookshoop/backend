package com.project.bookforeast.common.security.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

	MANAGER("ROLE_MANAGER"), USER("ROLE_USER");

	private String role;
}
