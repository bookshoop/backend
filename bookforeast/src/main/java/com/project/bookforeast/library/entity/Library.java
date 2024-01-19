package com.project.bookforeast.library.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.library.dto.LibraryDTO;
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
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long libraryId;
	private Long upperLibraryId;
	private String libraryName;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	private int depth; // 0서재 1책장 2책칸
	
	
	public LibraryDTO toDTO() {
		LibraryDTO.LibraryDTOBuilder libraryDTOBuilder = LibraryDTO.builder()
														.libraryId(libraryId)
														.upperLibraryId(upperLibraryId)
														.libraryName(libraryName)
														.registDt(registDt)
														.depth(depth);
		
		if(user != null) {
			libraryDTOBuilder.userDTO(user.toDTO());
		}
		
		return libraryDTOBuilder.build();
	}
	
}
