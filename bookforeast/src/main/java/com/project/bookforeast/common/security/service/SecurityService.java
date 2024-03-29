package com.project.bookforeast.common.security.service;

import com.project.bookforeast.user.dto.UserDTO;

public interface SecurityService {

	public void saveUserInSecurityContext(UserDTO userDTO);

	public void saveUserInSecurityContext(String accessToken);

	public UserDTO getUserInfoInSecurityContext();
}
