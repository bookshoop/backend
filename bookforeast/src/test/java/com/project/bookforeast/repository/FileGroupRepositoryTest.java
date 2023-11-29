package com.project.bookforeast.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.project.bookforeast.dto.FileGroupDTO;
import com.project.bookforeast.entity.FileGroup;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class FileGroupRepositoryTest {
	
	@Autowired
	private FileGroupRepository fileGroupRepository;
	

	@Test
	@DisplayName("파일 그룹 저장 성공")
	public void fileGroupSaveSuccess() {
		// given
		FileGroupDTO fileGroupDTO = new FileGroupDTO();
		
		// when
		FileGroup fileGroup = fileGroupRepository.save(fileGroupDTO.toEntity());
		
		// then
		assertNotEquals(fileGroup, null);
	}
}
