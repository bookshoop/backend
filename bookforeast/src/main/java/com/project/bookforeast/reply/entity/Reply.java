package com.project.bookforeast.reply.entity;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.reply.dto.ReplyDTO;
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
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long replyId;
	private Long upperReplyId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User registUser;
	
	private String content;
	private int contentType; // 0 책숲, 1 책나무
	private Long contentId;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	
	public ReplyDTO toDTO() {
		ReplyDTO.ReplyDTOBuilder replyDTOBuilder = ReplyDTO.builder()
														.replyId(replyId)
														.upperReplyId(upperReplyId)
														.content(content)
														.contentType(contentType)
														.contentId(contentId)
														.registDt(registDt);
		
		if(registUser != null) {
			replyDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		return replyDTOBuilder.build();
	}
}
