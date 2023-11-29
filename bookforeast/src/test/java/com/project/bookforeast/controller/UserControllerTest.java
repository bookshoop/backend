package com.project.bookforeast.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookforeast.common.security.SecurityServiceImpl;
import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.error.GlobalExceptionHandler;
import com.project.bookforeast.service.UserServiceImpl;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;
	
	@Mock
	UserServiceImpl userService;
	
	@Mock
	SecurityServiceImpl securityService;
	
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
					.setControllerAdvice(new GlobalExceptionHandler())
					.build();
		
		objectMapper = new ObjectMapper();
	}
	
	
	@Test
	@DisplayName("소셜 로그인 성공")
	@Transactional
	public void socialLoginSuccess() throws Exception {
		// given
		final String url = "/api/u/v1/users/social-login";
		List<Long> likeGenres = makeMockLikeGenres();
		MockMultipartFile profile = new MockMultipartFile("profile", "profile.jpg", MediaType.IMAGE_JPEG_VALUE, "profile image content".getBytes());
		
		// when 
		final ResultActions resultActions = mockMvc.perform(
										            multipart(url)
										            .file(profile)
										            .contentType(MediaType.MULTIPART_FORM_DATA)
										            .param("nickname", "testNickname")
										            .param("mobile", "01012345678")
										            .param("socialProvider", "KAKAO")
										            .param("socialId", "testSocialId")
										            .param("likeGenres", objectMapper.writeValueAsString(likeGenres))
										    );
		
		// then
		resultActions.andExpect(status().isOk());
	}
	
	
	@Test
	@DisplayName("회원가입 성공")
	@Transactional
	public void userSignUpSuccess() throws Exception {
		// given
		final String url = "/api/u/v1/users/signup";
		List<Long> likeGenres = makeMockLikeGenres();
		MockMultipartFile profile = new MockMultipartFile("profile", "profile.jpg", MediaType.IMAGE_JPEG_VALUE, "profile image content".getBytes());
		
		// when 
		final ResultActions resultActions = mockMvc.perform(
										            multipart(url)
										            .file(profile)
										            .contentType(MediaType.MULTIPART_FORM_DATA)
										            .param("nickname", "testNickname1")
										            .param("mobile", "01012345678")
										            .param("password", "testPassword123*")
										            .param("likeGenres", objectMapper.writeValueAsString(likeGenres))
										    );
		
		// then
		resultActions.andExpect(status().isOk());
	}
	
	
	@Test
	@DisplayName("로그인 성공")
	@Transactional
	public void userLoginSuccess() throws Exception {
		// given
		final String url = "/api/u/v1/users/login";
		Map<String, Object> map = new HashMap<>();
		map.put("mobile", "01012345678");
		map.put("password", "testPassword123");
		
		// when 
		final ResultActions resultActions = mockMvc.perform(post(url)
											 .contentType(MediaType.APPLICATION_JSON)
											 .content(objectMapper.writeValueAsString(map))
											);

		// then
		resultActions.andExpect(status().isOk());
	}
	
	
	private UserDTO userDTOMaker() {
		UserDTO userDTO = new UserDTO();
		List<Long> likeGenres = makeMockLikeGenres();
		
		userDTO.setNickname("testNickName");
		userDTO.setMobile("01012345678");
		userDTO.setSocialId("testSocialId");
		userDTO.setSocialProvider("KAKAO");
		userDTO.setLikeGenres(likeGenres);
		
		return userDTO;
	}

	private List<Long> makeMockLikeGenres() {
		List<Long> likeGenres = new ArrayList<>();
		likeGenres.add(2L);
		likeGenres.add(3L);
		likeGenres.add(4L);
		
		return likeGenres;
	}
}
