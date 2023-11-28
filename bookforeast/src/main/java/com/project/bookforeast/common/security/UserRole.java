package com.project.bookforeast.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

	private String role;
}
