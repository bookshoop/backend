package com.project.bookforeast.book.dto;

import com.project.bookforeast.book.entity.Book;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailBookInfoDTO {

	@Schema(description = "우리 db에 있는 책의 경우 주어지는 bookId")
	private Long bookId;
	
	@Schema(description = "책제목", requiredMode = RequiredMode.REQUIRED)
	private String title;
	
	@Schema(description = "썸네일 경로")
	private String thumbnailLink;
	
	@Schema(description = "카테고리")
	private String category;
	
	@Schema(description = "저자", requiredMode = RequiredMode.REQUIRED)
	private String writer;
	
	@Schema(description = "출시일")
	private String pubDate;
	
	@Schema(description = "책 소개")
	private String description;
	
	@Schema(description = "isbn13이 없을경우 제공되는 isbn")
	private String isbn;
	
	@Schema(description = "isbn13")
	private String isbn13;
	
	@Schema(description = "책 가격")
	private int price;
	
	@Schema(description = "출판사")
	private String publisher;
	

	public Book toEntity() {
		Book.BookBuilder builder = Book.builder();

		builder.bookId(bookId)
			   .title(title)
			   .publisher(publisher)
			   .writer(writer)
			   .description(description)
			   .price(price)
			   .thumbnailLink(thumbnailLink);

 		if(isbn13 != null || !isbn13.equals("")) {
			builder.isbn(isbn13);
		} else {
			builder.isbn(isbn);
		}


		return builder.build();
	}
} 