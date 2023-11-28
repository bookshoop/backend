package com.project.bookforeast.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.bookforeast.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	
	private final UserRepository userRepository;
	
	
	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
		// 일반로그인인 경우
		
		return null;
	}

}
