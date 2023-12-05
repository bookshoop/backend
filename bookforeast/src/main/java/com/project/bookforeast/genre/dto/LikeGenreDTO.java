package com.project.bookforeast.genre.dto;



import com.project.bookforeast.code.dto.CodeDTO;
import com.project.bookforeast.genre.entity.LikeGenre;
import com.project.bookforeast.user.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LikeGenreDTO {

	private Long likeGenreId;
	private UserDTO userDTO;
	private CodeDTO codeDTO;
	
	
	public LikeGenre toEntity() {
		LikeGenre.LikeGenreBuilder  entity = LikeGenre.builder();
		
		if(likeGenreId != 0) {
			entity.likeGenreId(likeGenreId);
		}
		
		if(userDTO != null) {
			entity.user(userDTO.toEntity());
		}
		
		if(codeDTO != null) {
			entity.code(codeDTO.toEntity());
		}
		
		return entity.build();
	}
}
