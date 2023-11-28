package com.project.bookforeast.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.dto.FileGroupDTO;
import com.project.bookforeast.entity.File;
import com.project.bookforeast.entity.FileGroup;
import com.project.bookforeast.error.FileException;
import com.project.bookforeast.error.result.FileErrorResult;
import com.project.bookforeast.repository.FileGroupRepository;
import com.project.bookforeast.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;
	private final FileGroupRepository fileGroupRepository;
	private final NetworkService networkService;

	public FileServiceImpl(FileRepository fileRepository, FileGroupRepository fileGroupRepository, NetworkService networkService) {
		this.fileRepository = fileRepository;
		this.networkService = networkService;
		this.fileGroupRepository = fileGroupRepository;
	}

	@Transactional
	public boolean fileUpload(MultipartFile[] files, String contentName) {
		if (files == null || files.length <= 0) {
			throw new FileException(FileErrorResult.EMPTY_FILE);
		}

		FileGroup fileGroup = new FileGroup();
		try {
			for(MultipartFile file : files) {
				File fileEntity = setFileFileEntity(file, contentName, fileGroup);
				fileGroup.addFile(fileEntity);
				uploadFilesInServer(fileEntity, file);
				fileGroupRepository.save(fileGroup);
			}		
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			deleteFiles(fileGroup.getFileList());
			new FileUploadException("File upload failed");

		}
		
		return true;

	}


	private File setFileFileEntity(MultipartFile file, String contentName, FileGroup fileGroup) {
		String realName = file.getOriginalFilename();
		String extension = getFileExtension(realName);
		String path = makeuploadFilePath(contentName, fileGroup.getFilegroupId());
		String name = makeFileName(extension);
		String type = getFileType(name);

		File fileEntity = new File();
		fileEntity.setRealName(realName);
		fileEntity.setExtension(extension);
		fileEntity.setPath(path);
		fileEntity.setName(name);
		fileEntity.setType(type);
	
		return fileEntity;
	} 


	
	
	private String getFileExtension(String realName) {
		int lastDotIndex = realName.lastIndexOf(".");
		return realName.substring(lastDotIndex + 1);
	}

	
	private String makeuploadFilePath(String contentName, int fileGroupId) {
		String uploadDir;
		if (networkService.isLocal()) {
			uploadDir = "C:/bookforeastdownloadfile/";
		} else {
			uploadDir = "";
		}
		return uploadDir + "/" + contentName + "/" + fileGroupId;
	}

	
	private String makeFileName(String extension) {
		UUID uuid = UUID.randomUUID();
		return uuid.toString() + "." + extension;
	}

	
	private String getFileType(String extension) {
		extension = extension.toLowerCase();
		if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
			return "images";
		} else {
			return "octet_stream";
		}
	}

	
	private void uploadFilesInServer(File fileInfo, MultipartFile file) throws IllegalStateException, IOException {
		String path = fileInfo.getPath();
		createDirIfNotExists(path);

		String name = fileInfo.getName();
		java.io.File uploadFile = new java.io.File(path, name);
		file.transferTo(uploadFile);
	}

	
	private void createDirIfNotExists(String path) {
		java.io.File file = new java.io.File(path);

		if (!file.exists()) {
			file.mkdir();
		}
	}

	
	public void deleteFiles(List<File> fileInfos) {
		if (fileInfos == null || fileInfos.isEmpty()) {
			return;
		}

		for (File fileInfo : fileInfos) {
			String path = fileInfo.getPath() + "/" + fileInfo.getName();
			java.io.File fileToDelete = new java.io.File(path);

			if (fileToDelete.exists() && fileToDelete.isFile()) {
				fileToDelete.delete();
			}
		}

		deleteFolder(fileInfos);
	}

	
	private void deleteFolder(List<File> fileInfos) {
		String folderPath = fileInfos.get(0).getPath();
		java.io.File folderToDelete = new java.io.File(folderPath);
		java.io.File[] files = folderToDelete.listFiles();
		if (files == null || files.length == 0) {
			folderToDelete.delete();
		}
	}

}
