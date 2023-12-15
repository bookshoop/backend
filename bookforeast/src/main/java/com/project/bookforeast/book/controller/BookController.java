package com.project.bookforeast.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.dto.SimpleBookInfoDTO;
import com.project.bookforeast.common.domain.dto.PagingInfoDTO;
import com.project.bookforeast.common.domain.dto.SearchDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/u/v1")
public class BookController {

	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때",
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "책 전체 정보 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = BookInfosDTO.class)))		
		})
	@GetMapping("/books")
	public ResponseEntity<BookInfosDTO> getBookInfos(@RequestBody @Valid PagingInfoDTO pagingInfoDTO, @RequestBody @Valid SearchDTO searchDTO) {
		return ResponseEntity.ok(null);
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때",
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "베스트셀러 정보 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = BookInfosDTO.class)))		
		})
	@GetMapping("/books/best-seller")
	public ResponseEntity<BookInfosDTO> getBookBestSellerInfos(@RequestBody @Valid PagingInfoDTO pagingInfoDTO, @RequestBody @Valid SearchDTO searchDTO) {
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
						 description = "추천 책 정보 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = BookInfosDTO.class)))		
		})
	@GetMapping("/books/recommended")
	public ResponseEntity<BookInfosDTO> getBookRecommendedInfos(@RequestBody @Valid PagingInfoDTO pagingInfoDTO) {
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
						 description = "책 상세 정보 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = DetailBookInfoDTO.class)))		
		})
	@GetMapping("/books/{id}")
	public ResponseEntity<DetailBookInfoDTO> getBookInfo(@PathVariable @Valid @Schema(description = "isbn / isbn13 / id") @NotBlank String id) {
		return ResponseEntity.ok(null);
	}
	
	
}
