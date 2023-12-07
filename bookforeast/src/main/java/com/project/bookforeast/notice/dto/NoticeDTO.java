package com.project.bookforeast.notice.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.notice.entity.Notice;
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
public class NoticeDTO {

	private Long noticeId;
	private UserDTO registUser;
	private String title;
	private String content;
	private LocalDateTime registDt;
	
	
	public Notice toEntity() {
		Notice.NoticeBuilder noticeBuilder = Notice.builder()
												.noticeId(noticeId)
												.title(title)
												.content(content);
		
		if(registUser != null) {
			noticeBuilder.registUser(registUser.toEntity());
		}
		
		return noticeBuilder.build();
	}
}
