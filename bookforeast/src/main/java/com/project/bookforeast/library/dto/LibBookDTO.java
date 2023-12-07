package com.project.bookforeast.library.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibBookDTO {

	private Long libBookId;
	private LibraryDTO libraryDTO;
	private String bookId; // 책 id or isbn api에서 가지고오는 로직 추가해야한다.
	private int userAdded;
	private LocalDateTime registDt;
	
}
