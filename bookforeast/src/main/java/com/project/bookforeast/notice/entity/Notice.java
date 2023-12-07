package com.project.bookforeast.notice.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.notice.dto.NoticeDTO;
import com.project.bookforeast.user.entity.User;

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
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User registUser;
	
	private String title;
	private String content;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	

	public NoticeDTO toDTO() {
		NoticeDTO.NoticeDTOBuilder noticeDTOBuilder = NoticeDTO.builder()
														.noticeId(noticeId)
														.title(title)
														.content(content)
														.registDt(registDt);
		
		if(registUser != null) {
			noticeDTOBuilder.registUser(registUser.toDTO());
		}
		
		return noticeDTOBuilder.build();
	}
}
