package com.project.bookforeast.readBook.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.common.security.service.SecurityService;
import com.project.bookforeast.readBook.dto.MonthlyReadDTO;
import com.project.bookforeast.readBook.dto.RangeReadDTO;
import com.project.bookforeast.readBook.dto.ReadStatsDTO;
import com.project.bookforeast.user.service.UserService;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/u/v1/user")
public class ReadBookController {

	
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	
	private ReadBookController(UserService userService, JwtUtil jwtUtil, SecurityService securityService) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "월별 독서 그래프 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = MonthlyReadDTO.class))),
	})
	@GetMapping("/user/monthly-graph")
	public ResponseEntity<MonthlyReadDTO> getUserMonthlyReadInfo(HttpServletRequest request) {
		String accessToken = jwtUtil.extractTokenFromHeader(request);
		MonthlyReadDTO monthlyReadDTO = userService.getMonthlyReadInfo(accessToken);
		return ResponseEntity.ok(monthlyReadDTO);
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때", 
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "기간별 독서 그래프 가져오기 성공",
						 content = @Content(array = @ArraySchema(schema = @Schema(implementation = RangeReadDTO.class)))),
	})
	@GetMapping("/user/range-graph")
	public ResponseEntity<List<RangeReadDTO>> getUserRangeReadInfo(HttpServletRequest request, 
			 @RequestParam @Schema(example = "2023-12-13") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, 
			 @RequestParam @Schema(example = "2023-12-13") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		List<RangeReadDTO> rangeReadDTOList = new ArrayList<RangeReadDTO>();
		RangeReadDTO rangeReadDTO = new RangeReadDTO();
		for(int i = 0; i < 3; i++) {
			rangeReadDTOList.add(rangeReadDTO);
		}
		return ResponseEntity.ok(rangeReadDTOList);
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
				@ApiResponse(responseCode = "400", 
						     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때", 
						     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
				@ApiResponse(responseCode = "401",
							 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
							 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
				@ApiResponse(responseCode = "200",
							 description = "독서통계 가져오기 성공",
							 content = @Content(schema = @Schema(implementation = ReadStatsDTO.class))),			
	})
	@GetMapping("/user/read-stats")
	public ResponseEntity<ReadStatsDTO> getUserReadStatsInfo() {
		ReadStatsDTO readStatsDTO = new ReadStatsDTO();
		return ResponseEntity.ok(readStatsDTO);
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때", 
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "읽은 책 등록 성공",
						 content = @Content(schema = @Schema(implementation = ReadStatsDTO.class))),			
	})
	@PostMapping("/user/readed-books/{id}")
	public ResponseEntity<Void> insReadBook(@PathVariable @Schema(description = "readbookId") int id, @RequestParam @Schema(description = "별점") int rate) {
		return ResponseEntity.ok(null);
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때", 
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "읽은 책 삭제 성공",
						 content = @Content(schema = @Schema(implementation = ReadStatsDTO.class))),			
	})
	@DeleteMapping("/user/readed-books/{id}")
	public ResponseEntity<Void> delReadBook(@PathVariable @Schema(description = "readbookId") int id) {
		return ResponseEntity.ok(null);
	}
}
