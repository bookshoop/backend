package com.project.bookforeast.bookforeast.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class SimpleBookForeastDTO {

	@Schema(description = "책숲id")
	private int id;
	
	@Schema(description = "책숲 제목")
	private String title;
	
	@Schema(description = "썸네일")
	private String thumbnail;
	
}
