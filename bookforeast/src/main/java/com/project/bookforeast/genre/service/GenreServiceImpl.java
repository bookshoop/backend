package com.project.bookforeast.genre.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.code.dto.CodeDTO;
import com.project.bookforeast.genre.dto.LikeGenreDTO;
import com.project.bookforeast.genre.entity.LikeGenre;
import com.project.bookforeast.genre.repository.GenreRepository;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.entity.User;

@Service
public class GenreServiceImpl implements GenreService {

	GenreRepository genreRepository;
	
	@Autowired
	public GenreServiceImpl(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}
	
	
	@Override
	public boolean saveLikeGenres(User user, List<Long> likeGenreCodeIds) {		
		List<LikeGenre> likeGenres = makeLikeGenreList(likeGenreCodeIds, user);
		genreRepository.saveAll(likeGenres);
		return true;
	}


	private List<LikeGenre> makeLikeGenreList(List<Long> likeGenreCodeIds, User user) {
		List<LikeGenre> likeGenres = new ArrayList<>();
		
		for(Long likeGenreCodeId : likeGenreCodeIds) {
			CodeDTO codeDTO = new CodeDTO();
			codeDTO.setCodeId(likeGenreCodeId);

			LikeGenreDTO likeGenreDTO = new LikeGenreDTO();
			likeGenreDTO.setCodeDTO(codeDTO);
			likeGenreDTO.setLikeGenreId(likeGenreCodeId);
			likeGenreDTO.setUserDTO(user.toDTO());
			
			LikeGenre likeGenre = likeGenreDTO.toEntity();
			likeGenres.add(likeGenre);
		}
		
		return likeGenres;
	}

}
