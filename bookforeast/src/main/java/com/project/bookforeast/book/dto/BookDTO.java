package com.project.bookforeast.book.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.book.entity.Book;
import com.project.bookforeast.file.dto.FileGroupDTO;
import com.project.bookforeast.user.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

	private Long bookId;
	private String isbn;
	private UserDTO registUserDTO;
	private FileGroupDTO thumbnailFileGroupDTO;
	private String title;
	private String publisher;
	private String writer;
	private String description;
	private LocalDateTime registDt;
	private int price;
	
	
	public Book toEntity() {
		Book.BookBuilder bookBuilder = Book.builder()
										.bookId(bookId)
										.isbn(isbn)
										.title(title)
										.publisher(publisher)
										.writer(writer)
										.description(description)
										.price(price);
		
		if(registUserDTO != null) {
			bookBuilder.registUser(registUserDTO.toEntity());
		}
		
		if(thumbnailFileGroupDTO != null) {
			bookBuilder.thumbnailFileGroup(thumbnailFileGroupDTO.toEntity());
		}
		
		return bookBuilder.build();
	}
}
