package com.project.bookforeast.common.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Size;
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
public class SearchDTO {
	
	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "고양이")
	@Size(min = 1)
	private String searchValue; // 검색어
}
