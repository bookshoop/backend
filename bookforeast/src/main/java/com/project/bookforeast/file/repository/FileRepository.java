package com.project.bookforeast.file.repository;
import org.springframework.data.repository.CrudRepository;

import com.project.bookforeast.file.entity.File;

public interface FileRepository extends CrudRepository<File, Long> {

}
