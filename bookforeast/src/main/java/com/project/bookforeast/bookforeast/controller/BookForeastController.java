package com.project.bookforeast.bookforeast.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.bookforeast.dto.BookForeastInfosDTO;
import com.project.bookforeast.common.domain.dto.PagingInfoDTO;
import com.project.bookforeast.common.domain.dto.SearchDTO;
import com.project.bookforeast.common.domain.dto.SearchOptioanlDTO;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/u/v1")
public class BookForeastController {

	
	@SecurityRequirement(name = "Bearer Authenticaion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "2. 파라미터가 부적절한 값일 때", 
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "내가 쓴 책숲 정보 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = BookForeastInfosDTO.class))),		
	})
	@GetMapping("/user/bookforeasts")
	public ResponseEntity<BookForeastInfosDTO> getUserMyBookforeastInfos() {
		BookForeastInfosDTO mybookForeastInfosDTO = new BookForeastInfosDTO();
		return ResponseEntity.ok(mybookForeastInfosDTO);
	}
	
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "2. 파라미터가 부적절한 값일 때", 
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "책숲 정보 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = BookForeastInfosDTO.class))),		
	})
	@GetMapping("/bookforeast")
	public ResponseEntity<BookForeastInfosDTO> getBookforeastInfos(@RequestBody @Valid PagingInfoDTO pagingInfoDTO, @RequestBody @Valid SearchOptioanlDTO searchOptionalDTO) {
		BookForeastInfosDTO bookForeastInfosDTO = new BookForeastInfosDTO();
		return ResponseEntity.ok(bookForeastInfosDTO);
	}
	
}
