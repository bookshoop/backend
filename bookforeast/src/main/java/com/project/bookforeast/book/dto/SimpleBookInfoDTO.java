package com.project.bookforeast.book.dto;

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
public class SimpleBookInfoDTO {

	@Schema(description = "isbn 13자리 or isbn10자리")
	private int id;
	
	@Schema(description = "책 제목")
	private String title;
	
	@Schema(description = "작가")
	private String writer;
	
	@Schema(description = "썸네일")
	private String thumbnail;
	
	@Schema(description = "카테고리")
	private String category;
	
	@Schema(description = "페이징 커서")
	private String cursor;
	
}
