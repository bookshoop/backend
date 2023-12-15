package com.project.bookforeast.user.dto;

import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;

import com.project.bookforeast.user.annotation.IsSocialProvider;
import com.project.bookforeast.user.entity.User;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class SocialLoginDTO {
	
	@Schema(requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "socialId는 빈 값일 수 없습니다.")
	private String socialId;

	@Schema(requiredMode = RequiredMode.REQUIRED, description = "KAKAO / NAVER / APPLE", example = "KAKAO")
	@IsSocialProvider
	private String socialProvider;
	
	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "01012345678")
	@Pattern(regexp = "^010\\d{7,8}$", message = "휴대폰번호가 지정된 형식이 아닙니다.")
	private String mobile;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "2023-12-08 / 20231208, 두 형태 중 어떤걸로줘도 상관없어")
	private Date birthday;
	
	@Schema(requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "pushToken은 빈 값일 수 없습니다.")
	private String pushToken;
	
	@Hidden
	private String role;
	
	@Hidden
	private String nickname;
	
	
	public User toEntity() {
		User entity = User.builder()
						  .socialId(socialId)
						  .socialProvider(socialProvider)
						  .mobile(mobile)
						  .birthday(birthday)
						  .pushToken(pushToken)
						  .role(role)
						  .nickname(nickname)
						  .build();

		return entity;
	}
}