package com.project.bookforeast.entity;

import com.project.bookforeast.dto.LikeGenreDTO;

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
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeGenre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeGenreId;;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "genre_id")
	private Code code;
	
	
	public LikeGenreDTO toDTO() {
		LikeGenreDTO.LikeGenreDTOBuilder dto = LikeGenreDTO.builder();
		
		if(likeGenreId != 0) {
			dto.likeGenreId(likeGenreId);
		}
		
		if(user != null) {
			dto.userDTO(user.toDTO());
		}
		
		if(code != null) {
			dto.codeDTO(code.toDTO());
		}
		
		return dto.build();
	}
}
