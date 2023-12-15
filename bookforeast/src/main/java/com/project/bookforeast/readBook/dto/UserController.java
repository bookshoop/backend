package com.project.bookforeast.readBook.dto;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.common.domain.dto.PagingInfoDTO;
import com.project.bookforeast.common.domain.dto.SearchDTO;
import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.user.dto.UserInfoDTO;
import com.project.bookforeast.user.dto.UserInfoListDTO;
import com.project.bookforeast.user.dto.UserUpdDTO;
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
	private final JwtUtil jwtUtil;
	
	private UserController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
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
	public ResponseEntity<UserInfoDTO> getUserInfo(HttpServletRequest request) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		UserInfoDTO userInfoDTO = userService.getUserInfo(accessToken);
		return ResponseEntity.ok(userInfoDTO);
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때",
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "500",
			 			 description = "1. 파일업로드 실패",
			 			 content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "유저 정보 업데이트 성공"),
			
		})
	@PutMapping("/user")
	public ResponseEntity<Void> updUserInfo(HttpServletRequest request, @RequestBody @Valid UserUpdDTO userUpdDTO) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		userService.updUserInfo(accessToken, userUpdDTO);
		return ResponseEntity.ok(null);
		
	}
	
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
				     description = "2. 파라미터가 부적절한 값일 때", 
				     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "200",
						 description = "유저 목록 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = UserInfoListDTO.class))),			
	})
	@GetMapping("/users")
	public ResponseEntity<UserInfoListDTO> getUserInfos(@RequestBody @Valid PagingInfoDTO paginationDTO, @RequestBody @Valid SearchDTO searchDTO) {
		return ResponseEntity.ok(null);
	}
	
	
//	@DeleteMapping("/user")
	
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
