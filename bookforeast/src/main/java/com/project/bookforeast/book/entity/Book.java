package com.project.bookforeast.book.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.book.dto.BookDTO;
import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
	
	private Long bookId;
	private int isbn;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@Column(updatable = false)
	private User registUser;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private FileGroup thumbnailFileGroup;
	
	private String bookName;
	private String publisher;
	private String writer;
	private String introduction;

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime registDt;
	
	
	public BookDTO toDTO() {
		BookDTO.BookDTOBuilder bookDTOBuilder = BookDTO.builder()
													.bookId(bookId)
													.isbn(isbn)
													.bookName(bookName)
													.publisher(publisher)
													.writer(writer)
													.introduction(introduction)
													.registDt(registDt)
													;
		
		if(registUser != null) {
			bookDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		if(thumbnailFileGroup != null) {
			bookDTOBuilder.thumbnailFileGroupDTO(thumbnailFileGroup.toDTO());
		}
		
		return bookDTOBuilder.build();
	}
}
