package com.project.bookforeast.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.follow.dto.FollowInfosDTO;
import com.project.bookforeast.follow.service.FollowService;
import com.project.bookforeast.readBook.dto.ReadStatsDTO;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/u/v1")
public class FollowController {
	
	
	private final FollowService followService;
	private final JwtUtil jwtUtil;
	
	@Autowired
	public FollowController(FollowService followService, JwtUtil jwtUtil) {
		this.followService = followService;
		this.jwtUtil =  jwtUtil;
	}
	
	
	@SecurityRequirement(name = "Bearer Authenticaion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
			         description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때", 
			         content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "나의 팔로워 목록 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = ReadStatsDTO.class))),			
	})
	@GetMapping("/user/follower")
	public ResponseEntity<FollowInfosDTO> getMyFollowerInfos(@Schema(requiredMode = RequiredMode.NOT_REQUIRED, defaultValue = "10") @RequestParam(defaultValue = "10") int itemSize, 
			 												 @Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "없을경우 첫페이지야") @RequestParam String cursor,
			 												 HttpServletRequest request) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		FollowInfosDTO followerInfosDTO = followService.getMyFollowerInfos(accessToken, itemSize, cursor);
		return ResponseEntity.ok(followerInfosDTO);
	}
	
	
	@SecurityRequirement(name = "Bearer Authenticaion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
			         	 description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때", 
			         	 content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "나의 팔로잉 목록 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = ReadStatsDTO.class))),			
	})
	@GetMapping("/user/following")
	public ResponseEntity<FollowInfosDTO> getMyFollowingInfos(@Schema(requiredMode = RequiredMode.NOT_REQUIRED, defaultValue = "10") @RequestParam(defaultValue = "10") int itemSize, 
     		 												  @Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "없을경우 첫페이지야") @RequestParam String cursor,
     		 												  HttpServletRequest request) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		FollowInfosDTO followingInfosDTO = followService.getMyFollowingInfos(accessToken, itemSize, cursor);
		return ResponseEntity.ok(followingInfosDTO);
	}
	
	
	@SecurityRequirement(name = "Bearer Authenticaion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
			         	 description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때 \t\n 3. 파라미터에 해당하는 유저가 없을 때 ", 
			         	 content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "팔로우 성공")
	})
	@PostMapping("/users/{id}/follow")
	public ResponseEntity<Void> userFollow(@PathVariable @Schema(description = "followId") int id) {
		return ResponseEntity.ok(null);
	}
	
	@SecurityRequirement(name = "Bearer Authenticaion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
			         description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때 \t\n 3. 파라미터에 해당하는 유저가 없을 때 ", 
			         content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "언팔로우 성공")
	})
	@DeleteMapping("/users/{id}/follow")
	public ResponseEntity<Void> userUnFollow(@PathVariable @Schema(description = "followId") int id) {
		return ResponseEntity.ok(null);
	}
}
