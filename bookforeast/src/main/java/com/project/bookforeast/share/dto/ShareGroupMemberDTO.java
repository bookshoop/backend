package com.project.bookforeast.share.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.share.entity.ShareGroupMember;
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
public class ShareGroupMemberDTO {

	private Long shareMemberId;
	private ShareGroupDTO shareGroupDTO;
	private UserDTO memberDTO;
	private LocalDateTime registDt;
	
	public ShareGroupMember toEntity() {
		ShareGroupMember.ShareGroupMemberBuilder shareGroupMemberBuilder = ShareGroupMember.builder();
		
		shareGroupMemberBuilder.shareMemberId(shareMemberId);
		
		if(shareGroupDTO != null) {
			shareGroupMemberBuilder.shareGroup(shareGroupDTO.toEntity());
		}
		
		if(memberDTO != null) {
			shareGroupMemberBuilder.member(memberDTO.toEntity());
		}
		
		return shareGroupMemberBuilder.build();
	}
	
}
