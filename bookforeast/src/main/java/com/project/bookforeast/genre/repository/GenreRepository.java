package com.project.bookforeast.genre.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.bookforeast.genre.entity.LikeGenre;

public interface GenreRepository extends CrudRepository<LikeGenre, Long> {

}
