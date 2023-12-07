package com.project.bookforeast.bookTree.dto;

import java.time.LocalDateTime;

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
@AllArgsConstructor
@NoArgsConstructor
public class BookTreeDTO {

	private Long bookTreeId;
	private String bookId;
	private int userAdded;
	private UserDTO registUserDTO;
	private UserDTO updateUserDTO;
	private FileGroupDTO contentFileGroupDTO;
	private int secret;
	private String title;
	private LocalDateTime registDt;
	private LocalDateTime updateDt;
	private int state;
	
}
