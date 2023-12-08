package com.project.bookforeast.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {
	private Long fileId;
	
	@Schema(description = "경로/서버에 저장된 파일 이름")
	private String path;
	
	private String extension;
	
	
}
