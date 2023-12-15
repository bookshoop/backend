package com.project.bookforeast.common.security.service;


import com.project.bookforeast.user.dto.SocialLoginDTO;

public interface SecurityService {

	public void saveUserInSecurityContext(SocialLoginDTO socialLoginDTO);

	public void saveUserInSecurityContext(String accessToken);

	// public UserDetails getUserInfoInSecurityContext();
}
