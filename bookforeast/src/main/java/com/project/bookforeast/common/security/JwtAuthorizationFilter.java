package com.project.bookforeast.common.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
	
	public JwtAuthorizationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 로그인한 유저정보 바탕으로 토큰발급 및 spring security context에 유저 정보 저장
		
		final String token = request.getHeader("");

	}
}