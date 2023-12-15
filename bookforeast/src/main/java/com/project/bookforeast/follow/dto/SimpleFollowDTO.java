package com.project.bookforeast.follow.dto;

import com.project.bookforeast.file.dto.ProfileDTO;

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
public class SimpleFollowDTO {

	@Schema(description = "followId")
	private int id;
	private String nickname;
	private ProfileDTO profileDTO;
	
	@Schema(description = "내가 follow하고 있는지", hidden = true)
	private boolean isFollow;
}
