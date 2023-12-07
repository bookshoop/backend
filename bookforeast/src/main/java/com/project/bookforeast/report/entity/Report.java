package com.project.bookforeast.report.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.report.dto.ReportDTO;
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
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportId;
	
	@ManyToOne
	@JoinColumn(name = "reporter_id")
	private User reportUser;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reported_user_id")
	private User reportedUser;
	
	private int contentType; // 0책숲, 1 책나무, 2댓글
	private Long contentId;
	private String reason;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;

	
	public ReportDTO toDTO() {
		ReportDTO.ReportDTOBuilder reportDTOBuilder = ReportDTO.builder()
														.reportId(reportId)
														.contentType(contentType)
														.contentId(contentId)
														.reason(reason)
														.registDt(registDt);
		
		if(reportUser != null) {
			reportDTOBuilder.reportUserDTO(reportUser.toDTO());
		}
		
		if(reportedUser != null) {
			reportDTOBuilder.reportedUserDTO(reportedUser.toDTO());
		}
		
		return reportDTOBuilder.build();
	}
}
