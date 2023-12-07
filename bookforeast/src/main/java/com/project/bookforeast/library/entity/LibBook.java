package com.project.bookforeast.library.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.library.dto.LibBookDTO;

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
@AllArgsConstructor
@NoArgsConstructor
public class LibBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long libBookId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "library_id")
	private Library library;
	
	private String bookId; 
	private int userAdded;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	
	public LibBookDTO toDTO() {
		LibBookDTO.LibBookDTOBuilder libBookDTOBuilder = LibBookDTO.builder()
															.libBookId(libBookId)
															.bookId(bookId)
															.userAdded(userAdded)
															.registDt(registDt);
		
		if(library != null) {
			libBookDTOBuilder.libraryDTO(library.toDTO());
		}
		
		return libBookDTOBuilder.build();
	}
}
