package com.project.bookforeast.recommendforeast.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.repository.query.parser.Part.IgnoreCaseType;

import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.recommendforeast.dto.RecommendForeastDTO;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendForeast {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recommendForeastId;
	
	private String title;
	private int state;
	private String address;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "content_file_id")
	private FileGroup contentFileGroup;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "main_image_id")
	private FileGroup mainImageFileGroup;
	
	private int lat; // 위도
	private int lng; // 경도
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "manager_id", updatable = false)
	private User managerUser;
	
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "updater_id")
	private User updateUser;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	@UpdateTimestamp
	private LocalDateTime updateDt;
	
	
	public RecommendForeastDTO toDTO() {
		RecommendForeastDTO.RecommendForeastDTOBuilder recommendForeastBuilder = RecommendForeastDTO.builder();
		
		
		recommendForeastBuilder.recommendForeastId(recommendForeastId)
								.title(title)
								.state(state)
								.address(address)
								.lat(lat)
								.lng(lng)
								.registDt(registDt)
								.updateDt(updateDt);
		
		if(contentFileGroup != null) {
			recommendForeastBuilder.contentFileGroupDTO(contentFileGroup.toDTO());
		}
		
		if(mainImageFileGroup != null) {
			recommendForeastBuilder.mainImageFileGroupDTO(mainImageFileGroup.toDTO());
		}
		
		if(managerUser != null) {
			recommendForeastBuilder.managerUserDTO(managerUser.toDTO());
		}
		
		if(updateUser != null) {
			recommendForeastBuilder.updateUserDTO(updateUser.toDTO());
		}
		
		return recommendForeastBuilder.build();

	}
}
