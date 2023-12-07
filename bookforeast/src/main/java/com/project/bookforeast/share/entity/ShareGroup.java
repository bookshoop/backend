package com.project.bookforeast.share.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.library.entity.Library;
import com.project.bookforeast.share.dto.ShareGroupDTO;
import com.project.bookforeast.share.dto.ShareGroupMemberDTO;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shareGroupId;
	
	@ManyToOne
	@JoinColumn(name = "library_id")
	private Library library;
	
	private String groupName;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	@ManyToOne
	@JoinColumn(name = "maker_id")
	private User maker;
	
	@OneToMany(mappedBy = "shareGroup")
	private List<ShareGroupMember> shareGroupMemberList;

	
	public ShareGroupDTO toDTO() {
		ShareGroupDTO.ShareGroupDTOBuilder shareGroupDTOBuilder = ShareGroupDTO.builder();
		
		shareGroupDTOBuilder.shareGroupId(shareGroupId)
							.groupName(groupName)
							.registDt(registDt);
		
		if(library != null) {
			shareGroupDTOBuilder.libraryDTO(library.toDTO());
		}
		
		if(maker != null) {
			shareGroupDTOBuilder.makerDTO(maker.toDTO());
		}
		
		if(shareGroupMemberList != null && shareGroupMemberList.size() > 0) {
			List<ShareGroupMemberDTO> shareGroupMemberDTOList = new ArrayList<>();
			
			shareGroupMemberList.forEach((shareGroupMember) -> {
				shareGroupMemberDTOList.add(shareGroupMember.toDTO());
			});
			
			shareGroupDTOBuilder.shareGroupMemberDTOList(shareGroupMemberDTOList);
		}
		
		return shareGroupDTOBuilder.build();
	}
	
	
}
