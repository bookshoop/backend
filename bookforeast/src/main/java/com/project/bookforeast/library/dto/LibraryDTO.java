package com.project.bookforeast.library.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.project.bookforeast.library.entity.Library;
import com.project.bookforeast.user.dto.UserDTO;

import io.swagger.v3.oas.annotations.Hidden;
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
	private LibraryDTO parentLibraryDTO;
	private Long upperLibraryId;
	private String libraryName;

	@Hidden
	private LocalDateTime registDt;

	@Hidden
	private UserDTO registUserDTO;
	private int depth;

	private List<LibraryDTO> childLibraryDTOs;
	
	
	public Library toEntity() {
		Library.LibraryBuilder builder = Library.builder()
													.libraryId(libraryId)
													.libraryName(libraryName)
													.depth(depth);
		
		if(registUserDTO != null) {
			builder.registUser(registUserDTO.toEntity());
		}

		if(childLibraryDTOs != null && !childLibraryDTOs.isEmpty()) {
			List<Library> childLibraries = childLibraryDTOs.stream()
			                                 .map(LibraryDTO::toEntity)
											 .filter(Objects::nonNull)
											 .collect(Collectors.toList());
		    builder.childLibraries(childLibraries);
		}
		
		return builder.build();
	}
}
