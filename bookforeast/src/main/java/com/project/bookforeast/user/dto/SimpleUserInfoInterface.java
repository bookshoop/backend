package com.project.bookforeast.user.dto;

import com.project.bookforeast.file.dto.ProfileDTO;

public interface SimpleUserInfoInterface {

	Long getId();
	String getNickname();
	Integer getFollowerCount();
	Integer getFollowingCount();
	Integer getIsFollowCnt();
	Long getFileId();
	String getPath();
	String getExtension();
	String getCs();
	
	
	default SimpleUserInfoDTO toSimpleUserInfoDTO() {
		SimpleUserInfoDTO.SimpleUserInfoDTOBuilder builder = SimpleUserInfoDTO.builder();
	
		
		builder.id(getId())
				.nickname(getNickname())
				.followerCount(getFollowerCount() == null ? 0: getFollowerCount())
				.followingCount(getFollowingCount() == null ? 0: getFollowingCount())
				.cursor(getCs())
				.isFollow(getIsFollowCnt() != null && getIsFollowCnt() == 1 ? true : false);
	
		
		builder.ProfileDTO(buildProfileDTO());
		
		return builder.build();
	}
	
	
	default ProfileDTO buildProfileDTO() {
		ProfileDTO.ProfileDTOBuilder profileBuilder = ProfileDTO.builder();
		
		profileBuilder.fileId(getFileId())
					  .path(getPath())
					  .extension(getExtension());
		
		return profileBuilder.build();
	}
}
