package com.project.bookforeast.service;

import java.util.List;

import com.project.bookforeast.dto.LikeGenreDTO;
import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.entity.User;

public interface GenreService {

	boolean saveLikeGenres(User user, List<Long> likeGenreCodeIds);

}
