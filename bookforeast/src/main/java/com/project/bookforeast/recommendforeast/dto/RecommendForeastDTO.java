package com.project.bookforeast.recommendforeast.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.file.dto.FileGroupDTO;
import com.project.bookforeast.recommendforeast.entity.RecommendForeast;
import com.project.bookforeast.user.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendForeastDTO {

	private Long recommendForeastId;
	private String title;
	private int state;
	private String address;
	private FileGroupDTO contentFileGroupDTO;
	private FileGroupDTO mainImageFileGroupDTO;
	private int lat;
	private int lng;
	private UserDTO managerUserDTO;
	private UserDTO updateUserDTO;
	private LocalDateTime registDt;
	private LocalDateTime updateDt;
	
	public RecommendForeast toEntity() {
		RecommendForeast.RecommendForeastBuilder recommendForeastBuilder = RecommendForeast.builder();
		
		recommendForeastBuilder.recommendForeastId(recommendForeastId)
								.title(title)
								.state(state)
								.address(address)
								.lat(lat)
								.lng(lng);
		
		
		if(contentFileGroupDTO != null) {
			recommendForeastBuilder.contentFileGroup(contentFileGroupDTO.toEntity());		
		}
		
		if(mainImageFileGroupDTO != null) {
			recommendForeastBuilder.mainImageFileGroup(mainImageFileGroupDTO.toEntity());
		}
		
		if(managerUserDTO != null) {
			recommendForeastBuilder.managerUser(managerUserDTO.toEntity());
		}
		
		if(updateUserDTO != null) {
			recommendForeastBuilder.updateUser(updateUserDTO.toEntity());
		}
		
		return recommendForeastBuilder.build();
	}
}
