package com.project.bookforeast.user.dto;

import com.project.bookforeast.file.dto.ProfileDTO;

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
public class SimpleUserInfoDTO {
	private Long id;
	private String nickname;
	private int followerCount;
	private int followingCount;
	private Boolean isFollow;
	private ProfileDTO ProfileDTO;
	private String cursor;
}
