package com.project.bookforeast.bookforeast.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.bookforeast.entity.BookForeast;
import com.project.bookforeast.file.dto.FileGroupDTO;
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
public class BookForeastDTO {

	private Long bookForeastId;
	private String title;
	private FileGroupDTO contentFileGroupDTO;
	private FileGroupDTO mainImageFileGroupDTO;
	private int state;
	private UserDTO registUserDTO;
	private UserDTO updateUserDTO;
	private LocalDateTime registDt;
	private LocalDateTime updateDt;
	
	
	public BookForeast toEntity() {
		BookForeast.BookForeastBuilder bookForeastBuilder = BookForeast.builder();
		
		bookForeastBuilder.bookForeastId(bookForeastId)
						  .title(title)
						  .state(state);
		
		if(contentFileGroupDTO != null) {
			bookForeastBuilder.contentFileGroup(contentFileGroupDTO.toEntity());
		}
		
		if(mainImageFileGroupDTO != null) {
			bookForeastBuilder.mainImageFileGroup(mainImageFileGroupDTO.toEntity());
		}
		
		if(registUserDTO != null) {
			bookForeastBuilder.registUser(registUserDTO.toEntity());
		}
		
		if(updateUserDTO != null) {
			bookForeastBuilder.updateUser(updateUserDTO.toEntity());
		}
		
		return bookForeastBuilder.build();
	}
	
}
