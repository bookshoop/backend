package com.project.bookforeast.share.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.bookforeast.library.dto.LibraryDTO;
import com.project.bookforeast.share.entity.ShareGroup;
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
@NoArgsConstructor
@AllArgsConstructor
public class ShareGroupDTO {

	private Long shareGroupId;
	private LibraryDTO libraryDTO;
	private String groupName;
	private LocalDateTime registDt;
	private UserDTO makerDTO;
	private List<ShareGroupMemberDTO> shareGroupMemberDTOList;
	
	
	public ShareGroup toEntity() {
		ShareGroup.ShareGroupBuilder shareGroupBuilder = ShareGroup.builder()
															.shareGroupId(shareGroupId)
															.groupName(groupName);
		
		if(libraryDTO != null) {
			shareGroupBuilder.library(libraryDTO.toEntity());
		}
		
		if(makerDTO != null) {
			shareGroupBuilder.maker(makerDTO.toEntity());
		}
		
		if(shareGroupMemberDTOList != null && shareGroupMemberDTOList.size() > 0) {
			List<ShareGroupMember> shareGroupMemberList = new ArrayList<>();
			shareGroupMemberDTOList.forEach((shareGroupMemberDTO) -> {
				shareGroupMemberList.add(shareGroupMemberDTO.toEntity());
			});
		}
		
		return shareGroupBuilder.build();
	}
}
