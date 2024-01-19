package com.project.bookforeast.library.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.library.dto.LibraryDTO;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "upper_library_id")
	private Library parentLibrary;
	

	
	private String libraryName;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User registUser;
	
	private int depth; // 0서재 1책장 2책칸
	
	@OneToMany(mappedBy = "parentLibrary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Library> childLibraries;
	
	public LibraryDTO toDTO() {
		LibraryDTO.LibraryDTOBuilder builder = LibraryDTO.builder()
														.libraryId(libraryId)
														.libraryName(libraryName)
														.registDt(registDt)
														.depth(depth);
		
		if(registUser != null) {
			builder.registUserDTO(registUser.toDTO());
		}

		if(childLibraries != null && !childLibraries.isEmpty()) {
			List<LibraryDTO> childLibraryDTOs = childLibraries.stream()
													.map(Library::toDTO)
													.filter(Objects::nonNull)
													.collect(Collectors.toList());
			
			builder.childLibraryDTOs(childLibraryDTOs);
		}
		
		return builder.build();
	}
	
}
