package com.project.bookforeast.follow.service;

import com.project.bookforeast.follow.dto.FollowInfosDTO;

public interface FollowService {

	FollowInfosDTO getMyFollowerInfos(String accessToken, int itemSize, String cursor);

	FollowInfosDTO getMyFollowingInfos(String accessToken, int itemSize, String cursor);

}
