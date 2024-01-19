package com.project.bookforeast.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookforeast.library.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    
}
