package com.project.bookforeast.follow.dto;

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
public class FollowInfosDTO {
	List<SimpleFollowDTO> content;
	
	@Schema(description = "다음데이터 있는지")
	private boolean hasMore;
	
	@Schema(description = "follower나 following유저 total 수")
	private int totalCount;
}
