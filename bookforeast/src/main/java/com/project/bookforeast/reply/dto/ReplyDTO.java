package com.project.bookforeast.reply.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.reply.entity.Reply;
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
public class ReplyDTO {

	private Long replyId;
	private Long upperReplyId;
	private UserDTO registUserDTO;
	private String content;
	private int contentType; // 0 책숲, 1 책나무
	private Long contentId;
	private LocalDateTime registDt;
	
	public Reply toEntity() {
		Reply.ReplyBuilder replyBuilder = Reply.builder()
											.replyId(replyId)
											.upperReplyId(upperReplyId)
											.content(content)
											.contentType(contentType)
											.contentId(contentId);
		
		if(registUserDTO != null) {
			replyBuilder.registUser(registUserDTO.toEntity());
		}
		
		return replyBuilder.build();
	}
}
