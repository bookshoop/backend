package com.project.bookforeast.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.project.bookforeast.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO {
	
	private Long userId;
	
	@NotNull
	private String nickname;
	
	@NotNull
	@Pattern(regexp = "^[0-9]\\d*$")
	private String mobile;
	
	private String password;
	
	private Date birthday;
	private CodeDTO codeDTO;
	private String accessRoute;
	private String socialProvider;
	private String socialId;
	private LocalDateTime registDt;
	private LocalDateTime updateDt;
	private LocalDateTime deleteDt;
	private FileGroupDTO fileGroupDTO;
	private String role;
	private List<Long> likeGenres;
	private String pushToken;
	private String refreshToken;
	
	public User toEntity() {
		User.UserBuilder entity = 	User.builder()
										.userId(userId)
										.nickname(nickname)
										.mobile(mobile)
										.password(password)
										.birthday(birthday)
										.accessRoute(accessRoute)
										.socialProvider(socialProvider)
										.socialId(socialId)
										.deleteDt(deleteDt)
										.role(role)
										.pushToken(pushToken)
										.refreshToken(refreshToken)
										;
									
		if(codeDTO != null) {
			entity.code(codeDTO.toEntity());
		}
		
		if(fileGroupDTO != null) {
			entity.fileGroup(fileGroupDTO.toEntity());
		}
		
		User user = entity.build();
		
		return user;
	}
}
