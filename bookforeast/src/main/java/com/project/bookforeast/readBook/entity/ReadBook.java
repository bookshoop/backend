package com.project.bookforeast.readBook.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.readBook.dto.ReadBookDTO;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long readBookId;
	
	private String bookId; 
	private int userAdded;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User registUser;
	private int rate; // 별정
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	
	public ReadBookDTO toDTO() {
		ReadBookDTO.ReadBookDTOBuilder readBookDTOBuilder = ReadBookDTO.builder()
																.readBookId(readBookId)
																.bookId(bookId)
																.userAdded(userAdded)
																.rate(rate)
																.registDt(registDt);
		
		if(registUser != null) {
			readBookDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		return readBookDTOBuilder.build();
	}
}
