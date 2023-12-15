package com.project.bookforeast.user.dto;

import java.util.List;

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
public class UserInfoListDTO {

	private List<UserInfoDTO> content;
	
	@Schema(description = "다음데이터 있는지")
	private boolean hasMore;
}
