package com.project.bookforeast.bookforeast.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.project.bookforeast.bookforeast.dto.BookForeastDTO;
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
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookForeast {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookForeastId;
	
	private String title;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "content_file_id")
	private FileGroup contentFileGroup;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "main_image_id")
	private FileGroup mainImageFileGroup;
	
	private int state;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@Column(updatable = false)
	@JoinColumn(name = "user_id")
	private User registUser;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "updater_id")
	private User updateUser;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	@UpdateTimestamp
	private LocalDateTime updateDt;
	
	
	public BookForeastDTO toBookForeastDTO() {
		BookForeastDTO.BookForeastDTOBuilder bookForeastDTOBuilder = BookForeastDTO.builder();
		
		bookForeastDTOBuilder.bookForeastId(bookForeastId)
							 .title(title)
							 .state(state)
							 .registDt(registDt)
							 .updateDt(updateDt);
		
		if(contentFileGroup != null) {
			bookForeastDTOBuilder.contentFileGroupDTO(contentFileGroup.toDTO());
		}
		
		if(mainImageFileGroup != null) {
			bookForeastDTOBuilder.mainImageFileGroupDTO(mainImageFileGroup.toDTO());
		}
		
		if(registUser != null) {
			bookForeastDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		if(updateUser != null) {
			bookForeastDTOBuilder.updateUserDTO(updateUser.toDTO());
		}
		
		return bookForeastDTOBuilder.build();
	}
}
