package com.project.bookforeast.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.dto.CodeDTO;
import com.project.bookforeast.dto.LikeGenreDTO;
import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.entity.LikeGenre;
import com.project.bookforeast.entity.User;
import com.project.bookforeast.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {

	GenreRepository genreRepository;
	
	@Autowired
	public GenreServiceImpl(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}
	
	
	@Override
	public boolean saveLikeGenres(User user, List<Integer> likeGenreCodeIds) {		
		List<LikeGenre> likeGenres = makeLikeGenreList(likeGenreCodeIds, user);
		genreRepository.saveAll(likeGenres);
		return true;
	}


	private List<LikeGenre> makeLikeGenreList(List<Integer> likeGenreCodeIds, User user) {
		List<LikeGenre> likeGenres = new ArrayList<>();
		
		for(int likeGenreCodeId : likeGenreCodeIds) {
			CodeDTO codeDTO = new CodeDTO();
			codeDTO.setCodeId(likeGenreCodeId);

			LikeGenreDTO likeGenreDTO = new LikeGenreDTO();
			likeGenreDTO.setCodeDTO(codeDTO);
			likeGenreDTO.setLikeGenreId(likeGenreCodeId);
			
			LikeGenre likeGenre = likeGenreDTO.toEntity();
			likeGenre.setUser(user);
			likeGenres.add(likeGenre);
			
		}
		
		return likeGenres;
	}

}
