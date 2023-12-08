package com.project.bookforeast.genre.entity;

import com.project.bookforeast.code.entity.Code;
import com.project.bookforeast.genre.dto.LikeGenreDTO;
import com.project.bookforeast.genre.dto.SimpleLikeGenreDTO;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeGenre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeGenreId;;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User registUser;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "genre_id")
	private Code code;
	
	
	public LikeGenreDTO toDTO() {
		LikeGenreDTO.LikeGenreDTOBuilder likeGenreDTOBuilder = LikeGenreDTO.builder();
		
		if(likeGenreId != 0) {
			likeGenreDTOBuilder.likeGenreId(likeGenreId);
		}
		
		if(registUser != null) {
			likeGenreDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		if(code != null) {
			likeGenreDTOBuilder.codeDTO(code.toDTO());
		}
		
		return likeGenreDTOBuilder.build();
	}
	
	public SimpleLikeGenreDTO toSimpleLikeGenreDTO() {
		SimpleLikeGenreDTO.SimpleLikeGenreDTOBuilder simpleLikeGenreDTOBuilder = SimpleLikeGenreDTO.builder()
																					.likeGenreId(likeGenreId);
		
		if(code != null) {
			simpleLikeGenreDTOBuilder.name(code.getCodename());
		}
		
		return simpleLikeGenreDTOBuilder.build();
	}
}
