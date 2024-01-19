package com.project.bookforeast.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.library.dto.LibraryDTO;
import com.project.bookforeast.library.service.LibraryService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/u/v1")
public class LibraryController {
    
    private final LibraryService libraryService;
    private final JwtUtil jwtUtil;

    public LibraryController(LibraryService libraryService, JwtUtil jwtUtil) {
        this.libraryService = libraryService;
        this.jwtUtil = jwtUtil;
    }


    @SecurityRequirement(name = "Bearer Authentication")
	@PostMapping("/library")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "401",
						 description = "1. 엑세스 토큰이 없을 때 \t\n 2. 엑세스 토큰이 만료되었을 때 \t\n 3. 엑세스 토큰으로 유저를 찾을 수 없을 때",
						 content = @Content(schema = @Schema(example = "{\"code\" : \"401 UNAUTHORIZED\", \"message\" : \"message\"}"))),
			@ApiResponse(responseCode = "200",
						 description = "서재등록하기 성공"),
	})
    public ResponseEntity<Void> insLibrary(HttpServletRequest request,  @RequestBody @Valid LibraryDTO libraryDTO) {
        libraryService.insLibrary(libraryDTO);
        return ResponseEntity.ok(null);
    }
    

    

    
}
