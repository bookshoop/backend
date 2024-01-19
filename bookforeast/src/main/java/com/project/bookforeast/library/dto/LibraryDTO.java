package com.project.bookforeast.library.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.library.entity.Library;
import com.project.bookforeast.user.dto.UserDTO;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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

	@Hidden
	private Long libraryId;

	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "서재일 경우 상위 id를 안줘도되고, 책장이랑 책칸은 필요해")
	private Long upperLibraryId;
	private String libraryName;

	@Hidden
	private LocalDateTime registDt;

	@Hidden
	private UserDTO userDTO;

	@Schema(requiredMode = RequiredMode.REQUIRED, description = "0:서재, 1:책장, 2:책칸")
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
