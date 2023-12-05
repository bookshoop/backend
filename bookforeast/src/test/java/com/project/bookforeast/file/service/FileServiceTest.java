package com.project.bookforeast.file.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.common.domain.service.NetworkService;
import com.project.bookforeast.file.entity.File;
import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.file.error.FileErrorResult;
import com.project.bookforeast.file.error.FileException;
import com.project.bookforeast.file.repository.FileGroupRepository;
import com.project.bookforeast.file.repository.FileRepository;
import com.project.bookforeast.file.service.FileServiceImpl;


@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

	@InjectMocks
	private FileServiceImpl fileService;
	
	@Mock
	private FileRepository fileRepository;
	
	@Mock
	private FileGroupRepository fileGroupRepository;
	
	@Mock
	private NetworkService networkService;
	
	
	@DisplayName("파일 비어있을 경우")
	@Test
	public void emptyFileUpload() {
		// given
		MockMultipartFile[] emptyFile = new MockMultipartFile[0];
		
		// when
		final FileException result = assertThrows(FileException.class, () -> fileService.fileUpload(emptyFile, "testContentName"));
		
		// then
		assertThat(result.getFileErrorResult()).isEqualTo(FileErrorResult.EMPTY_FILE);
	}
	
	

	@DisplayName("파일 업로드 성공 시")
	@Test
	public void fileUploadSuccess() {
		// given
		FileGroup fileGroup = fileGroupBuilder();
		MultipartFile[] mockFileArray = makeMultipartFileArray();
		addFileEntityInFileGroup(fileGroup, mockFileArray);
		doReturn(fileGroup).when(fileGroupRepository).save(fileGroup);
		
		// when
		boolean result = fileService.fileUpload(mockFileArray, "testContentName");
		
		// then
		assertTrue(result);
	}




	private MultipartFile[] makeMultipartFileArray() {
		MultipartFile mockFile1 = makeMockMultipartFile();
		MultipartFile mockFile2 = makeMockMultipartFile();
		MultipartFile mockFile3 = makeMockMultipartFile();
		
		MultipartFile[] mockFileArray = new MultipartFile[]{ mockFile1, mockFile2, mockFile3 };
		return mockFileArray;
	}

	
	private MultipartFile makeMockMultipartFile() {
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "example.txt",
                "text/plain",
                "Hello, World!".getBytes(StandardCharsets.UTF_8)
        );
        
		return multipartFile;
	}
	
	
	private void addFileEntityInFileGroup(FileGroup fileGroup, MultipartFile[] mockFileArray) {
		for(MultipartFile mockFile : mockFileArray) {
			fileGroup.addFile(makeFileEntityByMockFile(mockFile));
		}
	}
	
	
	private FileGroup fileGroupBuilder() {
		FileGroup fileGroup = FileGroup.builder()
										.filegroupId(9999999L)
										.fileList(new ArrayList<>())
										.build();
		return fileGroup;
	}
	
	
	

	private File makeFileEntityByMockFile(MultipartFile mockFile) {
		File file = new File();
		file.setRealName(mockFile.getOriginalFilename());
		file.setExtension("txt");
		file.setPath("testPath");
		file.setName(mockFile.getName());
		
		return file;
	}


	
	
	//	@DisplayName("파일 삭제 성공")
//	@Test
//	public void fileDeleteSuccess() {
//		// given
//		FileGroupDTO fileGroupDTO = mock(FileGroupDTO.class);
//		
//		FileDTO file1 = new FileDTO(1, fileGroupDTO, "/test", "file1.txt", "realFile1.txt", null, "text", "txt");
//		FileDTO file2 = new FileDTO(2, fileGroupDTO, "/test2", "file2.txt", "realFile2.txt", null, "Text", "txt");
//		
//		
//		List<FileDTO> fileInfoList = new ArrayList<>();
//		fileInfoList.add(file1);
//		fileInfoList.add(file2);
//		
//		
//		// when
//		
//		
//		// then
//	}
//	
//	
//	@DisplayName("fileInfo가 null")
//	@Test
//	public void fileDeleteFail_fileInfoIsNull() {
//		// given
//		List<FileDTO> fileInfoList = null;
//		
//		// when
//		fileService.deleteFiles(fileInfoList);
//		
//		// then
//	}


	

}
