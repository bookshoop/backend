package com.project.bookforeast.file.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.common.domain.service.NetworkService;
import com.project.bookforeast.file.dto.FileDTO;
import com.project.bookforeast.file.dto.FileGroupDTO;
import com.project.bookforeast.file.entity.File;
import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.file.error.FileErrorResult;
import com.project.bookforeast.file.error.FileException;
import com.project.bookforeast.file.repository.FileGroupRepository;
import com.project.bookforeast.file.repository.FileRepository;

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

		FileGroupDTO fileGroupDTO = new FileGroupDTO();
		FileGroup savedFileGroup = fileGroupRepository.save(fileGroupDTO.toEntity());
		List<File> fileList = new ArrayList<>();
		try {
			for(MultipartFile file : files) {
				FileDTO fileDTO = setFileInfo(file, savedFileGroup, contentName);
				uploadFilesInServer(fileDTO, file);
				fileList.add(fileDTO.toEntity());
			}		
			fileRepository.saveAll(fileList);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			deleteFiles(fileList);
			deleteFolder(fileList.get(0).getPath());
			throw new FileException(FileErrorResult.UPLOAD_FAIL);

		}
		
		return true;
	}

	
	@Override
	@Transactional
	public boolean fileUpload(MultipartFile file, String contentName) {
		if(file == null) {
			throw new FileException(FileErrorResult.EMPTY_FILE);
		}
		
		FileGroupDTO fileGroupDTO = new FileGroupDTO();
		return false;
	}
	

	@Override
	@Transactional
	public File fileUpload(MultipartFile file, FileGroup fileGroup, String contentName) {
		boolean isFolderCreated = false;
		if(fileGroup == null) {
			FileGroupDTO fileGroupDTO = new FileGroupDTO();
			fileGroup = fileGroupRepository.save(fileGroupDTO.toEntity());
			isFolderCreated = true;
		}
		
		FileDTO fileDTO = setFileInfo(file, fileGroup, contentName);
		try {
			uploadFilesInServer(fileDTO, file);
			File savedFile = fileRepository.save(fileDTO.toEntity());
			savedFile.setFileGroup(fileGroup);
			return savedFile;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			deleteFiles(fileGroup);
			if(isFolderCreated == true) {
				deleteFolder(fileDTO.getPath());
			}
			throw new FileException(FileErrorResult.UPLOAD_FAIL);
		}
	}

	
	private FileDTO setFileInfo(MultipartFile file, FileGroup fileGroup, String contentName) {
		String realName = file.getOriginalFilename();
		String extension = getFileExtension(realName);
		String path = makeuploadFilePath(contentName, fileGroup.getFilegroupId());
		String name = makeFileName(extension);
		String type = getFileType(name);

		FileDTO fileDTO = new FileDTO();
		fileDTO.setRealName(realName);
		fileDTO.setExtension(extension);
		fileDTO.setPath(path);
		fileDTO.setName(name);
		fileDTO.setType(type);
	
		return fileDTO;
	} 


	
	
	private String getFileExtension(String realName) {
		int lastDotIndex = realName.lastIndexOf(".");
		return realName.substring(lastDotIndex + 1);
	}

	
	private String makeuploadFilePath(String contentName, Long fileGroupId) {
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

	
	private void uploadFilesInServer(FileDTO fileDTO, MultipartFile file) throws IllegalStateException, IOException {
		String path = fileDTO.getPath();
		createDirIfNotExists(path);

		String name = fileDTO.getName();
		java.io.File uploadFile = new java.io.File(path, name);
		file.transferTo(uploadFile);
	}

	
	private void createDirIfNotExists(String path) {
		String[] paths = path.split("/");
		StringBuilder currentPath = new StringBuilder();

		for(int i = 0; i < paths.length; i++) {
			currentPath.append(paths[i]).append("/");
			java.io.File currentDir = new java.io.File(currentPath.toString());
			if (!currentDir.exists()) {
				currentDir.mkdir();
			}
		}
		

	}

	
	public void deleteFiles(List<File> fileList) {
		if (fileList == null || fileList.isEmpty()) {
			return;
		}
		for (File file : fileList) {
			deleteFile(file);
		}
	}
	
	
	public void deleteFiles(FileGroup fileGroup) {
		if(fileGroup == null) {
			return;
		}
		
		List<File> fileList = fileGroup.getFileList();
		for(File file : fileList) {
			deleteFile(file);
		}
	}

	
	public void deleteFile(File file) {
		String path = file.getPath() + "/" + file.getName();
		java.io.File fileToDelete = new java.io.File(path);

		if (fileToDelete.exists() && fileToDelete.isFile()) {
			fileToDelete.delete();
		}
	}

	
	private void deleteFolder(String folderPath) {
		java.io.File folderToDelete = new java.io.File(folderPath);
		java.io.File[] files = folderToDelete.listFiles();
		if (files == null || files.length == 0) {
			folderToDelete.delete();
		}
	}

	@Override
	public File fileUpdate(MultipartFile file, FileGroup thumbnailFileGroup, String contentName) {
		File fileInfo = thumbnailFileGroup.getFileList().get(0);
		deleteFile(fileInfo);
		File savedFile = fileUpload(file, thumbnailFileGroup, contentName);

		System.out.println("test");
		System.out.println("test");
		System.out.println("test");

		return savedFile;

	}


}
