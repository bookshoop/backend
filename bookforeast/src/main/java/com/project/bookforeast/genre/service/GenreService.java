package com.project.bookforeast.genre.service;

import java.util.List;

import com.project.bookforeast.genre.dto.LikeGenreDTO;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.entity.User;

public interface GenreService {

	boolean saveLikeGenres(User user, List<Long> likeGenreCodeIds);

}
