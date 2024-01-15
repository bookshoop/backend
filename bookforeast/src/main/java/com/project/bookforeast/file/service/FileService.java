package com.project.bookforeast.file.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.file.dto.FileDTO;
import com.project.bookforeast.file.entity.File;
import com.project.bookforeast.file.entity.FileGroup;


public interface FileService {

	
	public boolean fileUpload(MultipartFile[] files, String contentName);

	
	public boolean fileUpload(MultipartFile file, String contentName);

	
	public FileDTO fileUpload(MultipartFile file, FileGroup fileGroup, String string);

	
	public void deleteFiles(List<File> files);

	
	public void deleteFiles(FileGroup fileGroup);
	
	
	public void deleteFile(File file);

}
