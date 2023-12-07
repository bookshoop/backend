package com.project.bookforeast.report.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.report.entity.Report;
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
public class ReportDTO {

	private Long reportId;
	private UserDTO reportUserDTO;
	private UserDTO reportedUserDTO;
	private int contentType;
	private Long contentId;
	private String reason;
	private LocalDateTime registDt;
	
	public Report toEntity() {
		Report.ReportBuilder reportBuilder = Report.builder()
												.reportId(reportId)
												.contentType(contentType)
												.contentId(contentId)
												.reason(reason);
		
		if(reportUserDTO != null) {
			reportBuilder.reportUser(reportUserDTO.toEntity());
		}
		
		if(reportedUserDTO != null) {
			reportBuilder.reportedUser(reportedUserDTO.toEntity());
		}
		
		return reportBuilder.build();
	}
}
