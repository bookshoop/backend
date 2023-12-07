package com.project.bookforeast.share.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.share.dto.ShareGroupMemberDTO;
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

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareGroupMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shareMemberId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "share_group_id")
	private ShareGroup shareGroup;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User member;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	public ShareGroupMemberDTO toDTO() {
		ShareGroupMemberDTO.ShareGroupMemberDTOBuilder shareGroupMemberBuilder = ShareGroupMemberDTO.builder();
		
		shareGroupMemberBuilder.shareMemberId(shareMemberId)
						   .registDt(registDt);
		
		if(shareGroup != null) {
			shareGroupMemberBuilder.shareGroupDTO(shareGroup.toDTO());
		}
		
		if(member != null) {
			shareGroupMemberBuilder.memberDTO(member.toDTO());
		}
		
		return shareGroupMemberBuilder.build();
	}
}
