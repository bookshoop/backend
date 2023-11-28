package com.project.bookforeast.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.project.bookforeast.dto.FileGroupDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class FileGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int filegroupId;
	
	@CreatedDate
	private LocalDateTime registDt;
	
	@OneToMany(mappedBy = "fileGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	List<File> fileList = new ArrayList<>();
	
	
	public void addFile(File file) {
		this.fileList.add(file);
		
		if(file.getFileGroup() != this) {
			file.takeFileGroup(this);
		}
	}
	
	
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
