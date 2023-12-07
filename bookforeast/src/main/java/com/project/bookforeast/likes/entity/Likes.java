package com.project.bookforeast.likes.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.likes.dto.LikesDTO;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likesId;
	private Long contentId;
	private int contentType; // 0 책숲, 1 책나무
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User registUser;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	
	public LikesDTO toDTO() {
		LikesDTO.LikesDTOBuilder likesDTOBuilder = LikesDTO.builder()
														.likesId(likesId)
														.contentId(contentId)
														.contentType(contentType)
														.registDt(registDt);
		
		if(registUser != null) {
			likesDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		return likesDTOBuilder.build();
	}
	
}
