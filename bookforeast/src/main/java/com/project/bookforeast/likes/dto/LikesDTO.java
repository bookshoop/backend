package com.project.bookforeast.likes.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.likes.entity.Likes;
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
public class LikesDTO {

	private Long likesId;
	private Long contentId;
	private int contentType;
	private UserDTO registUserDTO;
	private LocalDateTime registDt;
	
	
	public Likes toEntity() {
		Likes.LikesBuilder likesBuilder = Likes.builder()
											.likesId(likesId)
											.contentId(contentId)
											.contentType(contentType);
		
		if(registUserDTO != null) {
			likesBuilder.registUser(registUserDTO.toEntity());
		}
		
		return likesBuilder.build();
	}
}
