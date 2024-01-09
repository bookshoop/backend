package com.project.bookforeast.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.service.BookService;
import com.project.bookforeast.book.service.CategoryService;
import com.project.bookforeast.common.domain.dto.PagingInfoDTO;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
public class BookController {

	
	private final BookService bookService;
	private final CategoryService categoryService;
	
	
	@Autowired
	public BookController(BookService bookService, CategoryService categoryService) {
		this.bookService = bookService;
		this.categoryService = categoryService;
	}
	
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때",
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "책 목록 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = BookInfosDTO.class)))		
		})
	@GetMapping("/api/u/v1/books")
	public ResponseEntity<BookInfosDTO> getBookInfo(@Schema(requiredMode = RequiredMode.NOT_REQUIRED, defaultValue = "10") @RequestParam(defaultValue = "10") int itemSize, 
													@Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "없을경우 첫페이지야") @RequestParam String cursor, 
													@Schema(requiredMode = RequiredMode.NOT_REQUIRED) @RequestParam String searchValue) {
		BookInfosDTO bookInfosDTO = bookService.getBookInfo(itemSize, cursor, searchValue);
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
	@GetMapping("/api/u/v1/books/best-seller")
	public ResponseEntity<BookInfosDTO> getBookBestSellerInfos(@Schema(defaultValue = "10") @RequestParam(defaultValue = "10", required = false) int itemSize, 
															   @Schema(description = "없을경우 첫페이지야") @RequestParam(required = false) String cursor) {
		
		BookInfosDTO bookInfosDTO = bookService.getBookBestSellerInfos(itemSize, cursor);
		
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
	@GetMapping("/api/u/v1/books/recommended")
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
	@GetMapping("/api/u/v1/books/{id}")
	public ResponseEntity<DetailBookInfoDTO> getBookInfo(@PathVariable @Valid @Schema(description = "isbn / isbn13 / id") @NotBlank String id) {
		return ResponseEntity.ok(null);
	}
	
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때",
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "책 상세 정보 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = DetailBookInfoDTO.class)))		
		})
	@GetMapping("/api/n/v1/testCategory/{category}")
	public ResponseEntity<DetailBookInfoDTO> testCategory(@PathVariable @Valid @NotBlank String category) {
		categoryService.classifyCatg(category);
		return ResponseEntity.ok(null);
	}
	
}
