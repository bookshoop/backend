package com.project.bookforeast.library.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.library.entity.Library;
import com.project.bookforeast.user.dto.UserDTO;

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
public class LibraryDTO {

	private Long libraryId;
	private int upperLibraryId;
	private String libraryName;
	private LocalDateTime registDt;
	private UserDTO userDTO;
	private int depth;
	
	
	public Library toEntity() {
		Library.LibraryBuilder libraryBuilder = Library.builder()
													.libraryId(libraryId)
													.upperLibraryId(upperLibraryId)
													.libraryName(libraryName)
													.depth(depth);
		
		if(userDTO != null) {
			libraryBuilder.user(userDTO.toEntity());
		}
		
		return libraryBuilder.build();
	}
}
