package com.project.bookforeast.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@PostMapping("/api/u/v1/social-login")
	public ResponseEntity<Map<String, String>> socialLogin(@RequestParam UserDTO userDTO) {

		// 받아온 정보를 바당으로 db에 저장
		UserDTO savedOrFindUser = userService.socialLogin(userDTO);
		// security처리
		securityService.saveUserInSecurityContext(userDTO);
		// 토큰 발급
		Map<String, String> tokenMap = jwtUtil.initToken(savedOrFindUser);
	
		return ResponseEntity.ok(tokenMap);
	}
		
	
	@PostMapping("/api/u/v1/token")
	public ResponseEntity<Map<String, String>> checkRefreshToken(HttpServletRequest request) {
		String refreshToken = jwtUtil.extractTokenFromHeader(request);
		// jwtutils에서 refreshtoken검증
		jwtUtil.validateRefreshToken(refreshToken);
		
		// accessToken 발급
		UserDTO userDTO = jwtUtil.getUserInfoByUsingRefreshToken(refreshToken);
		Map<String, String> tokenMap = jwtUtil.refreshingAccessToken(userDTO, refreshToken);
		return ResponseEntity.ok(tokenMap);
	}
	
	
	
	@GetMapping("/api/u/v1/user")
	public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest request) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		UserDTO userDTO = jwtUtil.getUserInfoByUsingAccessToken(accessToken);
		return ResponseEntity.ok(userDTO);
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
