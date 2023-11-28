package com.project.bookforeast.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.common.security.JwtUtil;
import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.service.UserService;


@RestController
public class UserController {
	
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	private UserController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	
	@PostMapping("/api/u/v1/users/social-login")
	public  ResponseEntity<Map<String, String>> socialLogin(@RequestParam Map<String, Object> requestParam, @RequestPart("profile") MultipartFile profile) {
		UserDTO userDTO = userService.getUserDataInParameter(requestParam);
		UserDTO savedOrFindUser = userService.socialLogin(userDTO, "user", profile);
		Map<String, String> tokenMap = jwtUtil.initToken(savedOrFindUser);
	
		return ResponseEntity.ok(tokenMap);
	}
	
	
	@PostMapping("/api/u/v1/users/signup")
	public ResponseEntity<Void> signup(@RequestParam Map<String, Object> requestParam, @RequestPart("profile") MultipartFile profile) {
		UserDTO userDTO = userService.getUserDataInParameter(requestParam);
		UserDTO savedUser = userService.signUp(userDTO, "user", profile);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/api/u/v1/users/login")
	public ResponseEntity<Map<String, String>> login(@RequestParam Map<String, Object> requestParam) {
		UserDTO userDTO = userService.getUserDataInParameter(requestParam);
		UserDTO findUser = userService.login(userDTO);
		Map<String, String> tokenMap = jwtUtil.initToken(findUser);
		
		return ResponseEntity.ok(tokenMap);
	}
	

}
