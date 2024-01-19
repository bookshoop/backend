package com.project.bookforeast.library.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookforeast.library.entity.Library;
import com.project.bookforeast.user.entity.User;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    @Query("SELECT l FROM Library l " +
                "LEFT JOIN FETCH l.parentLibrary " +
                "LEFT JOIN FETCH l.childLibrarys " +
                "WHERE l.registUser = :registUser")
    public Library findByRegistUser(User registUser);
    
}
