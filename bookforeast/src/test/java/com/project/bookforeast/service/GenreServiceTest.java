package com.project.bookforeast.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.repository.GenreRepository;

@SpringBootTest
public class GenreServiceTest {

	@InjectMocks
	private GenreServiceImpl genreService;
	
	@Mock
	private GenreRepository genreRepository;
	
	
	
	@Test
	@DisplayName("장르 등록 성공")
	public void GenreSaveSuccess() {
		// given
		List<Integer> likeGenres = makeMockLikeGenres();
		UserDTO userDTO = userDTOMaker();
		
		// when
		boolean result = genreService.saveLikeGenres(userDTO.toEntity(), likeGenres);
		
		// then
		assertThat(result).isEqualTo(true);
	}



	private List<Integer> makeMockLikeGenres() {
		List<Integer> likeGenres = new ArrayList<>();
		likeGenres.add(2);
		likeGenres.add(3);
		likeGenres.add(4);
		
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

