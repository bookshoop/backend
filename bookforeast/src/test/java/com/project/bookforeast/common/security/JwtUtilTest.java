package com.project.bookforeast.common.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bookforeast.dto.UserDTO;

import io.jsonwebtoken.Claims;

@SpringBootTest
public class JwtUtilTest {


	private JwtUtil jwtUtil;
	
	@Mock
	private Claims claims;
	
	@Autowired	
	public JwtUtilTest(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	
	@BeforeEach
	public void setup() {
		// mock필드 초기화
		MockitoAnnotations.openMocks(this);
	}
	
	
	
	@Test
	@DisplayName("토큰 생성")
	public void generateToken() {
		// given
		UserDTO userDTO = userDTOMaker();
		
		// when
		String token = jwtUtil.generateAccessToken(userDTO);
		
		// then
		assertThat(token != null);
		assertThat(token.startsWith("eyJ"));
		
	}

	@Test
	@DisplayName("토큰 유효성 검사")
	public void validateToken() {
		// given
		UserDTO userDTO = userDTOMaker();
		String token = jwtUtil.generateAccessToken(userDTO);
		
		// when
		boolean isValid = jwtUtil.validateToken(token, "testNickName", "KAKAO");
		
		// then
		assertTrue(isValid);
		
		
	}
	
	
	private UserDTO userDTOMaker() {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setNickname("testNickName");
		userDTO.setMobile("01012345678");
		userDTO.setSocialId("testSocialId");
		userDTO.setSocialProvider("KAKAO");
		
		return userDTO;
	}
}
