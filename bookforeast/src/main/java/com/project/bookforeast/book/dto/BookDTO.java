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
	private int isbn;
	private UserDTO registUserDTO;
	private FileGroupDTO thumbnailFileGroupDTO;
	private String bookName;
	private String publisher;
	private String writer;
	private String introduction;
	private LocalDateTime registDt;
	
	
	public Book toEntity() {
		Book.BookBuilder bookBuilder = Book.builder()
										.bookId(bookId)
										.isbn(isbn)
										.bookName(bookName)
										.publisher(publisher)
										.writer(writer)
										.introduction(introduction);
		
		if(registUserDTO != null) {
			bookBuilder.registUser(registUserDTO.toEntity());
		}
		
		if(thumbnailFileGroupDTO != null) {
			bookBuilder.thumbnailFileGroup(thumbnailFileGroupDTO.toEntity());
		}
		
		return bookBuilder.build();
	}
}
