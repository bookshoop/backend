package com.project.bookforeast.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.common.security.service.SecurityService;
import com.project.bookforeast.read.dto.MonthlyReadDTO;
import com.project.bookforeast.user.dto.SocialLoginDTO;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.dto.UserInfoDTO;
import com.project.bookforeast.user.service.UserService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/u/v1")
public class UserController {
	
	private final UserService userService;
	private final SecurityService securityService;
	private final JwtUtil jwtUtil;
	
	private UserController(UserService userService, JwtUtil jwtUtil, SecurityService securityService) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.securityService = securityService;
	}
	
	
	@PostMapping("/social-login")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
					 description = "소셜로그인 성공 시",
					 content = @Content(schema = @Schema(example = "{\"accessToken\" : \"accessToken\", \"refreshToken\" : \"refreshToken\"}"))),
		@ApiResponse(responseCode = "400", 
					 description = "1. 값이 없을때 \t\n 2. 부적절한 값일 때", 
					 content = @Content(schema = @Schema(example = "{\"code\" : \"400 BAD_REQUEST\", \"message\" : \"message\"}")))
	})
	public ResponseEntity<Map<String, String>> socialLogin(@RequestBody @Valid SocialLoginDTO socialLoginDTO )
	{
		
		UserDTO savedOrFindUser = userService.socialLogin(socialLoginDTO);
		securityService.saveUserInSecurityContext(socialLoginDTO);
		Map<String, String> tokenMap = jwtUtil.initToken(savedOrFindUser);
	
		return ResponseEntity.ok(tokenMap);
	}

	

	@SecurityRequirement(name = "Bearer Authentication")
	@PostMapping("/token")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
					 description = "리프레쉬 토큰으로 엑세스 토큰 재발급 성공",
					 content = @Content(schema = @Schema(example = "{\"accessToken\" : \"accessToken\", \"refreshToken\" : \"refreshToken\"}"))),
		@ApiResponse(responseCode = "401",
					 description = "1. 리프레쉬 토큰이 없을 때 \t\n 2. 리프레쉬 토큰이 만료되었을 때",
					 content = @Content(schema = @Schema(example = "{\"code\" : \"401 UNAUTHORIZED\", \"message\" : \"message\"}")))
	})
	public ResponseEntity<Map<String, String>> refreshingAccessToken(HttpServletRequest request) {
		String refreshToken = jwtUtil.extractTokenFromHeader(request);
		jwtUtil.validateRefreshToken(refreshToken);		
		UserDTO userDTO = jwtUtil.getUserInfoByUsingRefreshToken(refreshToken);
		Map<String, String> tokenMap = jwtUtil.refreshingAccessToken(userDTO, refreshToken);
		return ResponseEntity.ok(tokenMap);
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping("/user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401 UNAUTHORIZED\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "유저 정보 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = UserInfoDTO.class))),
	})
	public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest request) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		UserDTO userDTO = jwtUtil.getUserInfoByUsingAccessToken(accessToken);
		return ResponseEntity.ok(userDTO);
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping("/user/monthly-graph")
	public ResponseEntity<MonthlyReadDTO> getUserMonthlyReadInfo() {
		MonthlyReadDTO monthlyReadDTO = null;
		return ResponseEntity.ok(monthlyReadDTO);
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
