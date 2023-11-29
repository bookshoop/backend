package com.project.bookforeast.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.project.bookforeast.entity.File;
import com.project.bookforeast.entity.FileGroup;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class FileRepositoryTest {

	@Autowired
	private FileRepository fileRepository;

	
	@Test
	@DisplayName("파일 목록 저장 성공")
	public void fileListSaveSuccess() {
		// given
		List<File> fileInfos = fileInfosMaker();
		
		// when
		final List<File> result = (List<File>) fileRepository.saveAll(fileInfos);
		
		// then
		assertThat(result.size()).isEqualTo(fileInfos.size());
	}


	@DisplayName("파일 목록 삭제 성공")
	public void fileListDeleteSuccess() {
		// given
		File file = new File();
		
		// when
		
		// then
	}
	
	
	private List<File> fileInfosMaker() {
		List<File> fileInfos = new ArrayList<>();
		FileGroup filegroup = mock(FileGroup.class);
		
		for(int i = 0; i < 3; i++) {
			File file = new File();
			file.setRealName("testFileRealName" + i);
			file.setExtension("txt");
			file.setPath("testPath");
			file.setName("testFileName" + i);
			file.takeFileGroup(filegroup);
		}
		
		return fileInfos;
	}
}
