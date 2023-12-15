package com.project.bookforeast.user.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UserUpdDTO {

	private MultipartFile profile;

	@Size(min = 1, max = 10, message = "닉네임은 1자 이상 10자 이하여야 합니다.")
	@Nullable
	private String nickname;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "2023-12-08 / 20231208, 두 형태 중 어떤걸로줘도 상관없어")
	@Nullable
	private Date birthday;
	
	@Nullable
	private List<Integer> likeGenre;
	
	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, example = "01012345678")
	@Pattern(regexp = "^010\\d{7,8}$", message = "휴대폰번호가 지정된 형식이 아닙니다.")
	@Nullable
	private String mobile;
}
