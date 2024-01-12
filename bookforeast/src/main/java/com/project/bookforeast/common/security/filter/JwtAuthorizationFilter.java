package com.project.bookforeast.common.security.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.common.security.service.SecurityService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
			filterChain.doFilter(request, response);
		} else {
			throw new RuntimeException("알 수 없는 오류가 발생했습니다.");
		}
	}



	private boolean checkAccessTokenValid(HttpServletRequest request) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		if(!jwtUtil.validateAccessToken(accessToken)) {
			securityService.saveUserInSecurityContext(accessToken);
		}
		return true;
	}
	
	
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/api-docs/json",
                "/api-docs",
                "/api/u/v1/social-login",
                "/api/u/v1/token",
                "/swagger-ui/",
                "/swagger-config",
                "/error",
                "/api/n/v1/",
                "/api/u/v1/books/best-seller",
				"/api/u/v1/book/"
        		};
        String path = request.getRequestURI();
        boolean shouldNotFilter = Arrays.stream(excludePath).anyMatch(path::startsWith);
        
        return shouldNotFilter;
    }
}