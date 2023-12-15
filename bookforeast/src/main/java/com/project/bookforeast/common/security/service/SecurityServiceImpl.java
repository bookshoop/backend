package com.project.bookforeast.common.security.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.bookforeast.common.security.dto.UserDetailsImpl;
import com.project.bookforeast.common.security.error.TokenErrorResult;
import com.project.bookforeast.common.security.error.TokenException;
import com.project.bookforeast.user.dto.SocialLoginDTO;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.repository.UserRepository;

import io.jsonwebtoken.Claims;

@Service
public class SecurityServiceImpl implements SecurityService{
	
	
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	
	@Autowired
	private SecurityServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}
	
	
	@Override
	public void saveUserInSecurityContext(SocialLoginDTO socialLoginDTO) {
		String socialId = socialLoginDTO.getSocialId();
		String socialProvider = socialLoginDTO.getSocialProvider();
		saveUserInSecurityContext(socialId, socialProvider);
	}
	
	
	public void saveUserInSecurityContext(String accessToken) {
		String socialId = jwtUtil.extractClaim(accessToken,  Claims::getSubject);
		String socialProvider = jwtUtil.extractClaim(accessToken, Claims::getIssuer);
		saveUserInSecurityContext(socialId, socialProvider);
	}
	
	
	private void saveUserInSecurityContext(String socialId, String socialProvider) {
		UserDetails userDetails = loadUserBySocialIdAndSocialProvider(socialId, socialProvider);
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
		
		if(authentication != null) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
		}
	}
	
	
	public UserDetails getUserInfoInSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		return principal;
	}

	
	private UserDetails loadUserBySocialIdAndSocialProvider(String socialId, String socialProvider) {
		User user = userRepository.findBySocialIdAndSocialProvider(socialId, socialProvider);
		
		if(user == null) {
			throw new TokenException(TokenErrorResult.TOKEN_EXPIRED);
		} else {
			UserDetailsImpl userDetails = new UserDetailsImpl();
			userDetails.setUser(user);
			return userDetails;
		}
		
	}
	
	

}
