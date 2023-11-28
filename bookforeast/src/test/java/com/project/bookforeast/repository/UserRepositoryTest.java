package com.project.bookforeast.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserRepositoryTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	@Test
	@DisplayName("유저 저장 성공")
	public void saveUserSuccess() {
		// given
		UserDTO userDTO = userDTOMaker();
		
		// when
		User result = userRepository.save(userDTO.toEntity());

		// then
		assertThat(userDTO.getNickname()).isEqualTo(result.getNickname());
	}
	
	@Test
	@DisplayName("유저 닉네임으로 찾기 성공")
	public void findUserByNicknameSucess() {
		// given
		UserDTO userDTO = userDTOMaker();
		userRepository.save(userDTO.toEntity());
		
		// when
		User result = userRepository.findByNickname(userDTO.getNickname());
		
		// then
		assertThat(userDTO.getNickname()).isEqualTo(result.getNickname());
		
	}
	
	@Test
	@DisplayName("등록된 유저 삭제 성공")
	public void deleteUser() {
		// given
		long beforeDelUserCount = userRepository.count();
		UserDTO userDTO = userDTOMaker();
		User savedUser = userRepository.save(userDTO.toEntity());
		
		// when
		userRepository.deleteById(savedUser.getUserId());
		
		// then
		assertThat(userRepository.count()).isEqualTo(beforeDelUserCount);
	}	

	
	private UserDTO userDTOMaker() {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setNickname("testNickName");
		userDTO.setMobile("01012345678");
		userDTO.setSocialId("testSocialId");
		userDTO.setSocialProvider("KAKAO");
		
		return userDTO;
	}
}
