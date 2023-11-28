package com.project.bookforeast.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.bookforeast.entity.FileGroup;

public interface FileGroupRepository extends CrudRepository<FileGroup, Integer> {

	public FileGroup findFirstByOrderByFilegroupIdDesc();
}
