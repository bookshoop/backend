package com.project.bookforeast.user.dto;

import java.util.Date;
import java.util.List;

import com.project.bookforeast.file.dto.ProfileDTO;
import com.project.bookforeast.genre.dto.SimpleLikeGenreDTO;

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
public class UserInfoDTO {
	private Long userId;
	private String nickname;
	private String mobile;
	private ProfileDTO profile;
	private Date birthday;
	private int followerCount;
	private int followingCount;
	private int wishListCount;
	private int readedBooksCount;
	private int bookTreesCount;
	private List<SimpleLikeGenreDTO> likeGenre;
}
