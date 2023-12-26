package com.project.bookforeast.common.domain.error;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookforeast.common.domain.error.GlobalExceptionHandler.ErrorResponse;
import com.project.bookforeast.common.security.error.TokenErrorResult;
import com.project.bookforeast.common.security.error.TokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (TokenException tokenException) {
			log.warn("TokenException occur:");
			TokenErrorResult errorResult = tokenException.getTokenErrorResult();
			setErrorResponse(errorResult.getStatus(), errorResult.getMessage() ,response);
		}
	}

	private void setErrorResponse(HttpStatus status, String message, HttpServletResponse response) {
		response.setStatus(status.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
		
		try {
			String json = new ObjectMapper().writeValueAsString(errorResponse);
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
