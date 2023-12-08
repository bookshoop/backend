package com.project.bookforeast.genre.dto;


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
public class SimpleLikeGenreDTO {

	private Long likeGenreId;
	private String name;
}
