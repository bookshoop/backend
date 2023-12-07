package com.project.bookforeast.readBook.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.readBook.entity.ReadBook;
import com.project.bookforeast.user.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadBookDTO {

	private Long readBookId;
	private String bookId;
	private int userAdded;
	private UserDTO registUserDTO;
	private int rate;
	private LocalDateTime registDt;
	
	
	public ReadBook toEntity() {
		ReadBook.ReadBookBuilder readBookBuilder = ReadBook.builder()
														.readBookId(readBookId)
														.bookId(bookId)
														.userAdded(userAdded)
														.rate(rate);
		
		if(registUserDTO != null) {
			readBookBuilder.registUser(registUserDTO.toEntity());
		}
		
		return readBookBuilder.build();
	}
}
