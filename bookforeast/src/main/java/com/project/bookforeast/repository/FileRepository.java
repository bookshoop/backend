package com.project.bookforeast.repository;
import org.springframework.data.repository.CrudRepository;

import com.project.bookforeast.entity.File;

public interface FileRepository extends CrudRepository<File, Integer> {

}
