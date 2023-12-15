package com.project.bookforeast.common.domain.dto;

import com.project.bookforeast.common.domain.annotation.IsSearchOptional;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchOptioanlDTO extends SearchDTO {
	
	@Schema(requiredMode = RequiredMode.REQUIRED, description = "recent | popular | mine | like", defaultValue = "recent")
	@IsSearchOptional
	private String optional;
	
}
