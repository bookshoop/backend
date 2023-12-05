package com.project.bookforeast.file.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.file.entity.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FileDTO {
	private Long fileId;
	private FileGroupDTO fileGroupDTO;
	private String path;
	private String name;
	private String realName;
	private LocalDateTime registDt;
	private String type;
	private String extension;
	
	
	public File toEntity() {
		File.FileBuilder fileBuilder = File.builder()
										.fileId(fileId)
										.path(path)
										.name(name)
										.realName(realName)
										.registDt(registDt)
										.type(type)
										.extension(extension);
		
		
		if(fileGroupDTO != null) {
			fileBuilder.fileGroup(fileGroupDTO.toEntity());
		}
		
		return fileBuilder.build();
	}
}
