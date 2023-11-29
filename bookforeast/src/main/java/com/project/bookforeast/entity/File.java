package com.project.bookforeast.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.project.bookforeast.dto.FileDTO;
import com.project.bookforeast.dto.FileGroupDTO;

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
	
	@ManyToOne
	@JoinColumn(name = "filegroup_id")
	@Setter(AccessLevel.NONE)
	private FileGroup fileGroup;
	
	private String path;
	private String name;
	private String realName;
	
	@CreatedDate
	private LocalDateTime registDt;
	private String type;
	private String extension;
	
	
	public void takeFileGroup(FileGroup fileGroup) {
		
		this.fileGroup = fileGroup;
		
		if(!fileGroup.getFileList().contains(this)) {
			fileGroup.getFileList().add(this);			
		}
	}
	
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
