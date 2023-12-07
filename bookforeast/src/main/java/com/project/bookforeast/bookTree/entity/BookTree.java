package com.project.bookforeast.bookTree.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.project.bookforeast.bookTree.dto.BookTreeDTO;
import com.project.bookforeast.file.entity.FileGroup;
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
public class BookTree {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookTreeId;
	
	private String bookId;
	private int userAdded;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@Column(updatable = false)
	private User registUser;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "updater_id")
	private User updateUser;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "content_file_id")
	private FileGroup contentFileGroup;
	
	private int secret; // 공개여부 : 0공개, 1비공개
	private String title;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	@UpdateTimestamp
	private LocalDateTime updateDt;
	
	private int state; // 상태 : 0임시저장, 1등록
	
	
	public BookTreeDTO toDTO() {
		BookTreeDTO.BookTreeDTOBuilder bookTreeDTOBuilder = BookTreeDTO.builder();
		
		bookTreeDTOBuilder.bookTreeId(bookTreeId)
						  .bookId(bookId)
						  .userAdded(userAdded)
						  .title(title)
						  .registDt(registDt)
						  .updateDt(updateDt)
						  .state(state);
		
		if(registUser != null) {
			bookTreeDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		if(updateUser != null) {
			bookTreeDTOBuilder.updateUserDTO(updateUser.toDTO());
		}
		
		if(contentFileGroup != null) {
			bookTreeDTOBuilder.contentFileGroupDTO(contentFileGroup.toDTO());
		}
		
		
		return bookTreeDTOBuilder.build();
	}
}
