package com.project.bookforeast.book.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.project.bookforeast.book.dto.BookDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.file.entity.File;
import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;
	private String isbn;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", updatable = false)
	private User registUser;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private FileGroup thumbnailFileGroup;
	
	private String title;
	private String publisher;
	private String writer;
	private String description;
	private int price;

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime registDt;
	
	
	public BookDTO toDTO() {
		BookDTO.BookDTOBuilder bookDTOBuilder = BookDTO.builder()
													.bookId(bookId)
													.isbn(isbn)
													.title(title)
													.publisher(publisher)
													.writer(writer)
													.description(description)
													.registDt(registDt)
													.price(price)
													;
		
		if(registUser != null) {
			bookDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		if(thumbnailFileGroup != null) {
			bookDTOBuilder.thumbnailFileGroupDTO(thumbnailFileGroup.toDTO());
		}
		
		return bookDTOBuilder.build();
	}


    public DetailBookInfoDTO toDetailBookInfoDTO() {
		DetailBookInfoDTO.DetailBookInfoDTOBuilder builder = DetailBookInfoDTO.builder();

		builder.id(isbn)
			   .title(title)
			   .writer(writer)
			   .publisher(publisher)
			   .description(description)
			   .isbn(isbn)
			   .isbn13(isbn)
			   .price(price);

		if(thumbnailFileGroup != null) {
			File file = thumbnailFileGroup.getFileList().get(0);
			
			String link = file.getPath() + "/" + file.getName() + "." + file.getExtension();
			builder.link(link);
		}		


		if(registDt != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			builder.pubDate(format.format(registDt));
		}
			   

        return builder.build();
    }
}
