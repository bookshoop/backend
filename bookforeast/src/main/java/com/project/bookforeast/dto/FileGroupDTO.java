package com.project.bookforeast.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.bookforeast.entity.FileGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileGroupDTO {
	
	private Long filegroupId;
	private LocalDateTime registDt;
	List<FileDTO> fileList;
	
	
	
	public FileGroup toEntity() {
		FileGroup entity = FileGroup.builder()
							.filegroupId(filegroupId)
							.fileList(new ArrayList<>())
							.build();
		
		
		if(registDt != null) {
			entity.setRegistDt(registDt);
		}
		
		if(fileList != null) {
			for(FileDTO fileDTO : fileList) {
				entity.getFileList().add(fileDTO.toEntity());
			}
		}
		
		return entity;
	}
	
}
