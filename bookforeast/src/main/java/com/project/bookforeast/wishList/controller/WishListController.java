package com.project.bookforeast.wishList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.common.domain.dto.PagingInfoDTO;
import com.project.bookforeast.wishList.dto.WishListInfoDTO;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/u/v1")
public class WishListController {
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때", 
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "위시리스트 가져오기 성공",
						 content = @Content(schema = @Schema(implementation = WishListInfoDTO.class))),			
		})
	@GetMapping("/user/wish-list")
	public ResponseEntity<WishListInfoDTO> getUserWishListInfos(@RequestBody @Valid PagingInfoDTO pagingInfoDTO) {
		return ResponseEntity.ok(null);
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때 \t\n 3. bookId에 해당하는 책이 없을 때", 
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "위시리스트 추가 성공")
		})
	@PostMapping("/user/wish-list/{id}")
	public ResponseEntity<Void> insWishList(@PathVariable @Schema(description = "wishListId") @NotBlank int id) {
		return ResponseEntity.ok(null);
	}
	
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", 
					     description = "1. 파라미터 값이 없을때 \t\n 2. 파라미터가 부적절한 값일 때 \t\n 3. bookId에 해당하는 책이 없을 때", 
					     content = @Content(schema = @Schema(example = "{\"code\" : \"400\", \"message\" : \"message\"}"))),	
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "위시리스트 삭제 성공")
		})
	@DeleteMapping("/user/wish-list/{id}")
	public ResponseEntity<Void> delWishList(@PathVariable @Schema(description = "wishListId") @NotBlank int id) {
		return ResponseEntity.ok(null);
	}
}
