package com.project.bookforeast.file.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.bookforeast.file.entity.FileGroup;

public interface FileGroupRepository extends CrudRepository<FileGroup, Long> {

	public FileGroup findFirstByOrderByFilegroupIdDesc();
}
