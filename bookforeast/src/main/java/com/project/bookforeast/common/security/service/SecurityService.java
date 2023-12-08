package com.project.bookforeast.common.security.service;

import com.project.bookforeast.user.dto.SocialLoginDTO;
import com.project.bookforeast.user.dto.UserDTO;

public interface SecurityService {

	public void saveUserInSecurityContext(SocialLoginDTO socialLoginDTO);

	public UserDTO getUserInfoInSecurityContext();
}
