package com.project.bookforeast.file.service;



import org.springframework.web.multipart.MultipartFile;


public interface FileService {


	public boolean fileUpload(MultipartFile[] files, String contentName);

}
