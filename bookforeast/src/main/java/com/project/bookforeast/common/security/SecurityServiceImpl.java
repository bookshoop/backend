package com.project.bookforeast.common.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.project.bookforeast.dto.UserDTO;

public class SecurityServiceImpl implements SecurityService {

	@Override
	public void saveUserInSecurityContext(UserDTO userDTO) {
		Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(userDTO.getRole()));
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDTO, null, authorities);
		
		if(authentication != null) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
		}
	}
	
	public UserDTO getUserInfoInSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if(principal instanceof UserDTO) {
			return (UserDTO) principal;
		}
		
		return null;
	}

}
