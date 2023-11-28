package com.project.bookforeast.common.security;

import com.project.bookforeast.dto.UserDTO;

public interface SecurityService {

	public void saveUserInSecurityContext(UserDTO userDTO);

	public UserDTO getUserInfoInSecurityContext();
}
