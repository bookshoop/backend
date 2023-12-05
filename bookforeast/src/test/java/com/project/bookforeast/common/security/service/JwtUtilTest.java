package com.project.bookforeast.common.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.repository.UserRepository;

import io.jsonwebtoken.Claims;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties", properties = { "key=value" })
public class JwtUtilTest {


	@InjectMocks
	private JwtUtil jwtUtil;
	
	@Mock
	private Claims claims;
	
	@Mock
	private UserRepository userRepository;
	
	@Value("${jwt.secret-key}")
	private String SECRETKEY;
	
	
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
	@DisplayName("엑세스 토큰 유효성 검사")
	public void validateAccessToken() {
		// given
		UserDTO userDTO = userDTOMaker();
		String token = jwtUtil.generateAccessToken(userDTO);
		
		
		// when
		boolean isValid = jwtUtil.validateAccessToken(token, "testNickName", "KAKAO");
		
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
