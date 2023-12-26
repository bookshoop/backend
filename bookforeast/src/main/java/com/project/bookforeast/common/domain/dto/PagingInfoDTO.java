package com.project.bookforeast.common.domain.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class PagingInfoDTO {
	
	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "10", defaultValue = "12")
	@Builder.Default
	@Positive
	private int itemSize = 12; // 한번에 불러오는 데이터양
	
	@Schema(requiredMode = RequiredMode.NOT_REQUIRED)
	@NotBlank
	private String cursor; // 커서
	
}
