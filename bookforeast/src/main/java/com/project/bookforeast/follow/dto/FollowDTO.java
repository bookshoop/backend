package com.project.bookforeast.follow.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.follow.entity.Follow;
import com.project.bookforeast.user.dto.UserDTO;

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
public class FollowDTO {
	
	private int followId;
	private UserDTO followingUserDTO;
	private UserDTO followerUserDTO;
	private LocalDateTime registDt;
	
	public Follow toEntity() {
		Follow.FollowBuilder followBuilder = Follow.builder()
												 .followId(followId);
		
		if(followingUserDTO != null) {
			followBuilder.followingUser(followingUserDTO.toEntity());
		}
		
		if(followerUserDTO != null) {
			followBuilder.followerUser(followerUserDTO.toEntity());
		}
		
		return followBuilder.build();
	}
	
}
