package com.project.bookforeast.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.bookforeast.entity.LikeGenre;

public interface GenreRepository extends CrudRepository<LikeGenre, Long> {

}
