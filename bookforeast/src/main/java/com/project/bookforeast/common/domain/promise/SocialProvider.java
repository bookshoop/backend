package com.project.bookforeast.common.domain.promise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialProvider {

	KAKAO("KAKAO"), NAVER("NAVER"), APPLE("APPLE");
	
	private String provider;
}
