package com.project.bookforeast.file.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.file.dto.FileDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "filegroup_id")
	private FileGroup fileGroup;
	
	private String path;
	private String name;
	private String realName;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	private String type;
	private String extension;
	
	
	
	public FileDTO toDTO() {
	
		
		FileDTO.FileDTOBuilder fileDTOBuilder = FileDTO.builder()
														.fileId(fileId)
														.path(path)
														.name(name)
														.realName(realName)
														.registDt(registDt)
														.type(type)
														.extension(extension);
		
		
		if(fileGroup != null) {
			fileDTOBuilder.fileGroupDTO(fileGroup.toDTO());
		}
		
		
		return fileDTOBuilder.build();
	}

	
}
