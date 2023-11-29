package com.project.bookforeast.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.project.bookforeast.dto.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String nickname;
	private String mobile;
	private String password;
	private Date birthday;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "access_route_id")
	private Code code;
	
	private String accessRoute;
	private String socialProvider;
	private String socialId;
	
	@CreatedDate
	private LocalDateTime registDt;
	
	@UpdateTimestamp
	private LocalDateTime updateDt;
	
	private LocalDateTime deleteDt;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "profile_id")
	private FileGroup fileGroup;
	
	private String role;
	private String pushToken;
	private String refreshToken;
	
	public UserDTO toDTO() {
		
		UserDTO.UserDTOBuilder userDTOBuilder = UserDTO.builder()
													.userId(userId)
													.nickname(nickname)
													.mobile(mobile)
													.password(password)
													.birthday(birthday)													
													.socialProvider(socialProvider)
													.socialId(socialId)
													.registDt(registDt)
													.updateDt(updateDt)
													.deleteDt(deleteDt)
													.role(role)
													.pushToken(pushToken)
													.refreshToken(refreshToken)
													; 
		
		if(code != null) {
			userDTOBuilder.codeDTO(code.toDTO());
		}
		
		if(fileGroup != null) {
			userDTOBuilder.fileGroupDTO(fileGroup.toDTO());
		}
		
		return userDTOBuilder.build();				
	}
}
