package com.project.bookforeast.common.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.error.TokenException;
import com.project.bookforeast.error.result.TokenErrorResult;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
	private final SecurityService securityService;
	
	@Autowired
	public JwtAuthorizationFilter(JwtUtil jwtUtil, SecurityService securityService) {
		this.jwtUtil = jwtUtil;
		this.securityService = securityService;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(checkAccessTokenValid(request)) {
			return;
		} else {
			throw new TokenException(TokenErrorResult.REFRESH_TOKEN_NEED);
		}
	}



	private boolean checkAccessTokenValid(HttpServletRequest request) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		UserDTO loginUser = securityService.getUserInfoInSecurityContext();
		String socialId = loginUser.getSocialId();
		String socialProvider = loginUser.getSocialProvider();
		
		if(accessToken != null && jwtUtil.validateAccessToken(accessToken, socialId, socialProvider)) {
			return true;			
		}
		
		return false;
	}
}