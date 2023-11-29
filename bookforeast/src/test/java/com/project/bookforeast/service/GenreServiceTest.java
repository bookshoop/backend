package com.project.bookforeast.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.repository.GenreRepository;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

	@InjectMocks
	private GenreServiceImpl genreService;
	
	@Mock
	private GenreRepository genreRepository;
	
	
	
	@Test
	@DisplayName("장르 등록 성공")
	public void GenreSaveSuccess() {
		// given
		List<Long> likeGenres = makeMockLikeGenres();
		UserDTO userDTO = userDTOMaker();
		
		// when
		boolean result = genreService.saveLikeGenres(userDTO.toEntity(), likeGenres);
		
		// then
		assertThat(result).isEqualTo(true);
	}



	private List<Long> makeMockLikeGenres() {
		List<Long> likeGenres = new ArrayList<>();
		likeGenres.add(2L);
		likeGenres.add(3L);
		likeGenres.add(4L);
		
		return likeGenres;
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

