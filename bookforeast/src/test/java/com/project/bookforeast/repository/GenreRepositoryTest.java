package com.project.bookforeast.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.project.bookforeast.dto.CodeDTO;
import com.project.bookforeast.dto.LikeGenreDTO;
import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.entity.LikeGenre;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class GenreRepositoryTest {

	@Autowired
	private GenreRepository genreRepository;
	
	
	@Test
	@DisplayName("좋아하는 장르 목록 저장")
	public void genreSaveAll() {
		// given
		List<Long> likeGenreCodeIds = makeMockLikeGenres();
		UserDTO userDTO = userDTOMaker();
		final List<LikeGenre> likeGenres = likeGenreMakerList(likeGenreCodeIds, userDTO);
		
		// when
		final List<LikeGenre> result = (List<LikeGenre>) genreRepository.saveAll(likeGenres);
		
		// then
		assertThat(likeGenres.size()).isEqualTo(result.size());
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
	
	
	private List<LikeGenre> likeGenreMakerList(List<Long> likeGenreCodeIds, UserDTO userDTO) {
		List<LikeGenre> likeGenres = new ArrayList<>();
		
		for(Long likeGenreCodeId : likeGenreCodeIds) {
			CodeDTO codeDTO = new CodeDTO();
			codeDTO.setCodeId(likeGenreCodeId);

			LikeGenreDTO likeGenreDTO = new LikeGenreDTO();
			likeGenreDTO.setCodeDTO(codeDTO);
			likeGenreDTO.setUserDTO(userDTO);
			likeGenreDTO.setLikeGenreId(likeGenreCodeId);
			
			likeGenres.add(likeGenreDTO.toEntity());
		}
		
		return likeGenres;
	}
}
