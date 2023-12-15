package com.project.bookforeast.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public class DetailBookInfoDTO {

	@Schema(description = "isbn / isbn13 / id")
	private String id;
	
	@Schema(description = "책제목", requiredMode = RequiredMode.REQUIRED)
	private String title;
	
	@Schema(description = "알라딘 링크")
	private String link;
	
	@Schema(description = "저자", requiredMode = RequiredMode.REQUIRED)
	private String writer;
	
	@Schema(description = "출시일")
	private String pubDate;
	
	@Schema(description = "책 소개")
	private String description;
	
	@Schema(description = "isbn")
	private String isbn;
	
	@Schema(description = "isbn13")
	private String isbn13;
	
	@Schema(description = "책 가격")
	private int price;
	
	@Schema(description = "출판사")
	private String publisher;
	
}
