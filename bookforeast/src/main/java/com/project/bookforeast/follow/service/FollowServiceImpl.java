package com.project.bookforeast.follow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.follow.dto.FollowInfosDTO;
import com.project.bookforeast.follow.entity.Follow;
import com.project.bookforeast.follow.repository.FollowRepository;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.service.UserService;

@Service
public class FollowServiceImpl implements FollowService {
	
	private final FollowRepository followRepository;
	private final UserService userService;
	
	@Autowired
	public FollowServiceImpl(FollowRepository followRepository, UserService userService) {
		this.followRepository = followRepository;
		this.userService = userService;
	}

	@Override
	public FollowInfosDTO getMyFollowerInfos(String accessToken, int itemSize, String cursor) {
		User user = userService.findBySocialIdAndSocialProvider(accessToken);
		List<Follow> follower = followRepository.findByFollowingUser(user);

		
		return null;

	}

	@Override
	public FollowInfosDTO getMyFollowingInfos(String accessToken, int itemSize, String cursor) {
		User user = userService.findBySocialIdAndSocialProvider(accessToken);
		return null;
	}
	
	
}
