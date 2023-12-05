package com.project.bookforeast.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.common.security.service.SecurityService;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class UserController {
	
	private final UserService userService;
	private final SecurityService securityService;
	private final JwtUtil jwtUtil;
	
	private UserController(UserService userService, JwtUtil jwtUtil, SecurityService securityService) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.securityService = securityService;
	}
	
	
	@PostMapping("/api/u/v1/users/social-login")
	public ResponseEntity<Map<String, String>> socialLogin(@RequestParam Map<String, Object> requestParam) {
		UserDTO userDTO = userService.getUserDataInParameter(requestParam);		
		// 받아온 정보를 바당으로 db에 저장
		UserDTO savedOrFindUser = userService.socialLogin(userDTO);
		// security처리
		securityService.saveUserInSecurityContext(userDTO);
		// 토큰 발급
		Map<String, String> tokenMap = jwtUtil.initToken(savedOrFindUser);
	
		return ResponseEntity.ok(tokenMap);
	}
		
	
	@PostMapping("/api/u/v1/user/refreshToken")
	public ResponseEntity<Map<String, String>> checkRefreshToken(HttpServletRequest request) {
		String refreshToken = jwtUtil.extractTokenFromHeader(request);
		// jwtutils에서 refreshtoken검증
		jwtUtil.validateRefreshToken(refreshToken);
		
		// accessToken 발급
		UserDTO userDTO = userService.getUserInfoByUsingRefreshToken(refreshToken);
		Map<String, String> tokenMap = jwtUtil.refreshingAccessToken(userDTO, refreshToken);
		return ResponseEntity.ok(tokenMap);
	}
	
	
	
//	@PostMapping("/api/u/v1/users/signup")
//	public ResponseEntity<Void> signup(@RequestParam Map<String, Object> requestParam, @RequestPart("profile") MultipartFile profile) {
//		UserDTO userDTO = userService.getUserDataInParameter(requestParam);
//		UserDTO savedUser = userService.signUp(userDTO, "user", profile);
//		
//		return ResponseEntity.ok().build();
//	}
//	
//	@PostMapping("/api/u/v1/users/login")
//	public ResponseEntity<Map<String, String>> login(@RequestParam Map<String, Object> requestParam) {
//		UserDTO userDTO = userService.getUserDataInParameter(requestParam);
//		UserDTO findUser = userService.login(userDTO);
//		Map<String, String> tokenMap = jwtUtil.initToken(findUser);
//		
//		return ResponseEntity.ok(tokenMap);
//	}
	

}
