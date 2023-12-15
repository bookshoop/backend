package com.project.bookforeast.common.domain.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.bookforeast.common.security.error.TokenErrorResult;
import com.project.bookforeast.common.security.error.TokenException;
import com.project.bookforeast.user.error.UserErrorResult;
import com.project.bookforeast.user.error.UserException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// 클라이언트에서 파라미터 잘못 전달한 경우
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		// exception으로 부터 에러를 가져와서 list에 담는다.
		final List<String> errorList = ex.getBindingResult()
										 .getAllErrors()
										 .stream()
										 .map(DefaultMessageSourceResolvable::getDefaultMessage)
										 .collect(Collectors.toList());
										 
		// 해당 에러메세지 로그를 찍는다.
		log.warn("클라이언트로부터 잘못된 파라미터 전달됨 : {}", errorList);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList.toString()));		
	}

	
	// 사용자 정의 excepion이 발생한 경우
	// userException
	@ExceptionHandler({UserException.class})
	public ResponseEntity<ErrorResponse> handleUserException(final UserException exception) {
		log.warn("UserException occur:" + exception);
		UserErrorResult errorResult = exception.getUserErrorResult();
		return ResponseEntity.status(errorResult.getStatus())
				.body(new ErrorResponse(errorResult.getStatus().value(), errorResult.getMessage()));
		
	}
	
	// tokenException
	@ExceptionHandler({TokenException.class})
	public ResponseEntity<ErrorResponse> handleTokenException(final TokenException exception) {
		log.warn("TokenException occur:" + exception);
		TokenErrorResult errorResult = exception.getTokenErrorResult();
		return ResponseEntity.status(errorResult.getStatus())
				.body(new ErrorResponse(errorResult.getStatus().value(), errorResult.getMessage()));
	}
	

	
	@RequiredArgsConstructor
	@Getter
	static class ErrorResponse {
		private final int code;
		private final String message;
	}
	
}


