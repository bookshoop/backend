package com.project.bookforeast.file.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.file.dto.FileGroupDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long filegroupId;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	@OneToMany(mappedBy = "fileGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	List<File> fileList = new ArrayList<>();
	
	
	public FileGroupDTO toDTO() {
		FileGroupDTO dto =  FileGroupDTO.builder()
									.filegroupId(filegroupId)
									.registDt(registDt)
									.build();
		
		if(fileList.size() > 0) {
			for(File file : fileList) {
				dto.getFileList().add(file.toDTO());
			}
		}
		
		return dto;
	}
}
